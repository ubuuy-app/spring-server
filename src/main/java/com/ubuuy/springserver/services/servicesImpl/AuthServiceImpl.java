package com.ubuuy.springserver.services.servicesImpl;

import com.google.common.base.Strings;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.ubuuy.springserver.config.constants.ApplicationConstants;
import com.ubuuy.springserver.config.constants.ExceptionMessages;
import com.ubuuy.springserver.models.auth.AuthMetadata;
import com.ubuuy.springserver.models.entities.MetaEntity;
import com.ubuuy.springserver.models.entities.OrganizationEntity;
import com.ubuuy.springserver.models.entities.RoleEntity;
import com.ubuuy.springserver.models.entities.UserEntity;
import com.ubuuy.springserver.models.enums.MetaActionEnum;
import com.ubuuy.springserver.models.enums.UserRole;
import com.ubuuy.springserver.models.requests.RegisterOwnerRequest;
import com.ubuuy.springserver.models.responses.api.LoginResponse;
import com.ubuuy.springserver.models.service_models.CustomClaimsServiceModel;
import com.ubuuy.springserver.models.service_models.OrganizationServiceModel;
import com.ubuuy.springserver.models.service_models.UserServiceModel;
import com.ubuuy.springserver.repositories.UserRepository;
import com.ubuuy.springserver.services.*;
import com.ubuuy.springserver.utils.logger.ServerLogger;
import com.ubuuy.springserver.utils.mapper.Mapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
public class AuthServiceImpl implements AuthService {

    private final ServerLogger serverLogger;
    private final Mapper mapper;
    private final String jwtSecretKey;
    private final int jwtExpirationInMs;

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final OrganizationService organizationService;
    private final AuthMetadataService authMetadataService;
    private final RoleService roleService;
    private final Parser parser;
    private final DatabaseReader databaseReader;
    private final PasswordEncoder passwordEncoder;


    public AuthServiceImpl(ServerLogger serverLogger,
                           Mapper mapper,
                           @Value("${app.jwt-secret}") String jwtSecretKey,
                           @Value("${app.jwt-expiration-mills}") int jwtExpirationInMs,
                           UserRepository userRepository,
                           UserService userService, UserDetailsService userDetailsService,
                           OrganizationService organizationService, AuthMetadataService authMetadataService,
                           RoleService roleService,
                           Parser parser,
                           DatabaseReader databaseReader,
                           PasswordEncoder passwordEncoder) {
        this.serverLogger = serverLogger;
        this.mapper = mapper;
        this.jwtSecretKey = jwtSecretKey;
        this.jwtExpirationInMs = jwtExpirationInMs;
        this.userRepository = userRepository;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.organizationService = organizationService;
        this.authMetadataService = authMetadataService;
        this.roleService = roleService;
        this.parser = parser;
        this.databaseReader = databaseReader;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String generateJWT(String email) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        Instant now = Instant.now();
        Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, convertToBites(jwtSecretKey))
                .compact();
    }

    @Override
    public LoginResponse generateLoginResponse(String userEmail) throws UnsupportedOperationException {

        try {
            String jwt = this.generateJWT(userEmail);
            UserServiceModel userServiceModel = userService.getByEmail(userEmail);

            CustomClaimsServiceModel customClaimsServiceModel
                    = new CustomClaimsServiceModel()
                    .setEmail(userServiceModel.getEmail())
                    .setFullName(userServiceModel.getFullName())
                    .setOrganizationId(userServiceModel.getOrganization().getId())
                    .setOrganizationName(userServiceModel.getOrganization().getName());

            return new LoginResponse(jwt).setCustomClaims(customClaimsServiceModel);
        } catch (Exception ex){
            throw new UnsupportedOperationException("Could not generate login response!");
        }
    }

    @Override
    public String getUserEmailFromJWT(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(convertToBites(jwtSecretKey))
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    @Override
    public boolean validateJWT(String jwt) {
        try {
            Jwts.parser()
                    .setSigningKey(convertToBites(jwtSecretKey))
                    .parseClaimsJws(jwt);

            return true;

        } catch (SignatureException ex) {
            serverLogger.error("JwtTokenProvider", "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            serverLogger.error("JwtTokenProvider", "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            serverLogger.error("JwtTokenProvider", "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            serverLogger.error("JwtTokenProvider", "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            serverLogger.error("JwtTokenProvider", "JWT claims string is empty");
        }

        return false;
    }

    @Override
    public boolean jwtIsLoggedOut(String jwt, String userEmail) {
        try {
            /* search for active sessions */
            List<AuthMetadata> activeSessions = authMetadataService.getAllActiveSessionsUser(userEmail);

            if (nonNull(activeSessions)) {
                /* try to find one that matches the provided jwt */
                AuthMetadata activeJwtAuthMetadata = activeSessions
                        .stream()
                        .filter(as -> as.getJwt().equals(jwt))
                        .findFirst()
                        .orElse(null);

                return activeJwtAuthMetadata == null;
            } else {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public String getJwtFromRequest(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(ApplicationConstants.HTTP_REQ_AUTH_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(ApplicationConstants.HTTP_REQ_AUTH_TOKEN_PREFIX)) {
            String jwt = bearerToken.substring(7, bearerToken.length());
            return jwt.equals("null") ? null : jwt;
        }
        return null;
    }


    @Override
    public HttpHeaders generateDoubleSubmitCookieHeader() {
        /* GENERATE CSRF TOKEN */
        String csrfToken = UUID.randomUUID().toString();

        /* ADD CSRF TOKEN IN COOKIE, IT WILL TRAVEL BACK WITH THE OTHER REQUESTS FROM CLIENT */
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(
                "Set-Cookie",
                "CSRF-TOKEN=" + csrfToken + "; Max-Age=604800; Path=/; Secure; SameSite=None; SameParty; HttpOnly"
        );
        /* ADD CSRF TOKEN IN HEADER, IT WILL BE STORED IN SESSION STORAGE AND ATTACHED TO FURTHER REQUESTS*/
        responseHeaders.set("X-CSRF-TOKEN", csrfToken);

        return responseHeaders;
    }

    @Override
    public HttpHeaders invalidateCsrfTokenCookie() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(
                "Set-Cookie",
                "CSRF-TOKEN=" + "" + "; Max-Age=0; Path=/; Secure; SameSite=None; SameParty; HttpOnly"
        );
        /* ADD CSRF TOKEN IN HEADER, IT WILL BE STORED IN SESSION STORAGE AND ATTACHED TO FURTHER REQUESTS*/
        responseHeaders.set("X-CSRF-TOKEN", "");

        return responseHeaders;
    }

    @Override
    @Transactional
    public void addLoginToUserHistory(String userEmail, HttpServletRequest request, String jwt)
            throws IOException, GeoIp2Exception, UsernameNotFoundException {

        UserEntity currentlyAuthenticatedUser = getCurrentlyAuthenticatedUser();

        if (nonNull(currentlyAuthenticatedUser)) {

            String deviceDetails = getDeviceDetails(request.getHeader("user-agent"));
            String location = getIpLocation(extractIp(request));

            AuthMetadata authMetadata =
                    new AuthMetadata()
                            .setDeviceDetails(deviceDetails)
                            .setLocation(location)
                            .setLogin(ZonedDateTime.now())
                            .setJwt(jwt);

            this.logoutAllUserAuthMetadata();

            currentlyAuthenticatedUser.getAuthMetadata().add(authMetadata);
            authMetadata.setUserEntity(currentlyAuthenticatedUser);
            this.userRepository.saveAndFlush(currentlyAuthenticatedUser);
        }
    }

    @Override
    public void logoutAllUserAuthMetadata() {

        /* logout previous active sessions */
        List<AuthMetadata> activeSessions = authMetadataService.getAllActiveSessionsForCurrentUser();

        activeSessions.forEach(as ->
                as.setLogout(ZonedDateTime.now())
                        .setSessionDuration(as.getLogout().toEpochSecond() - as.getLogin().toEpochSecond()));

        this.authMetadataService.saveAll(activeSessions);
    }

    @Override
    @Transactional
    public UserServiceModel registerOrganizationOwner(RegisterOwnerRequest registerOwnerRequest) throws SQLException {

        OrganizationServiceModel organizationServiceModel =
                new OrganizationServiceModel().setName(registerOwnerRequest.getOrganization());

        RoleEntity roleEntity = this.roleService.findExistingOrSaveNew(UserRole.OWNER);

        UserEntity userEntity = new UserEntity()
                .setEmail(registerOwnerRequest.getEmail())
                .setFullName(registerOwnerRequest.getFullName())
                .setPassword(passwordEncoder.encode(registerOwnerRequest.getPassword()))
                .setRoles(List.of(roleEntity))
                .setMeta(new MetaEntity()
                        .setSystemUser(registerOwnerRequest.getEmail())
                        .setAction(MetaActionEnum.CREATE))
                .setOrganization(mapper.toModel(organizationServiceModel, OrganizationEntity.class));


        return mapper.toModel(userRepository.saveAndFlush(userEntity), UserServiceModel.class);
    }

    @Override
    public String getPrincipalUsername() {
        return this.getCurrentlyAuthenticatedUser().getEmail();
    }

    private String getDeviceDetails(String userAgent) {
        String deviceDetails = "UNKNOWN";

        Client client = parser.parse(userAgent);
        if (nonNull(client)) {
            deviceDetails =
                    client.userAgent.family
                            + " " + client.userAgent.major + "."
                            + client.userAgent.minor + " - "
                            + client.os.family + " " + client.os.major
                            + "." + client.os.minor;
        }
        return deviceDetails;
    }

    private String extractIp(HttpServletRequest request) {
        String clientIp;
        String clientXForwardedForIp = request.getHeader("x-forwarded-for");
        if (nonNull(clientXForwardedForIp)) {
            clientIp = parseXForwardedHeader(clientXForwardedForIp);
        } else {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;
    }

    private String parseXForwardedHeader(String header) {
        return header.split(" *, *")[0];
    }

    private String getIpLocation(String ip) throws IOException, GeoIp2Exception {

        String location = "UNKNOWN";

        InetAddress ipAddress = InetAddress.getByName(ip);

        CityResponse cityResponse = databaseReader.city(ipAddress);
        if (Objects.nonNull(cityResponse) &&
                Objects.nonNull(cityResponse.getCity()) &&
                !Strings.isNullOrEmpty(cityResponse.getCity().getName())) {

            location = cityResponse.getCity().getName();
        }

        return location;
    }

    private UserEntity getCurrentlyAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = "";
        if (principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            userEmail = principal.toString();
        }

        return this.userRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessages.USER_FIND_BY_EMAIL_FAIL));
    }

    private byte[] convertToBites(String key) {
        return DatatypeConverter.parseBase64Binary(key);
    }
}
