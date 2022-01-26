package com.aviobrief.springserver.services.servicesImpl;

import com.aviobrief.springserver.models.auth.AuthMetadata;
import com.aviobrief.springserver.models.entities.UserEntity;
import com.aviobrief.springserver.repositories.UserRepository;
import com.aviobrief.springserver.services.AuthMetadataService;
import com.aviobrief.springserver.services.AuthService;
import com.aviobrief.springserver.utils.logger.ServerLogger;
import com.google.common.base.Strings;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;

import static com.aviobrief.springserver.config.constants.ApplicationConstants.HTTP_REQ_AUTH_HEADER;
import static com.aviobrief.springserver.config.constants.ApplicationConstants.HTTP_REQ_AUTH_TOKEN_PREFIX;
import static java.util.Objects.nonNull;

@Service
public class AuthServiceImpl implements AuthService {

    private final ServerLogger serverLogger;
    private final String jwtSecretKey;
    private final int jwtExpirationInMs;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final AuthMetadataService authMetadataService;
    private final Parser parser;
    private final DatabaseReader databaseReader;


    public AuthServiceImpl(ServerLogger serverLogger,
                           @Value("${app.jwt-secret}") String jwtSecretKey,
                           @Value("${app.jwt-expiration-mills}") int jwtExpirationInMs,
                           UserRepository userRepository,
                           UserDetailsService userDetailsService,
                           AuthMetadataService authMetadataService,
                           Parser parser,
                           DatabaseReader databaseReader) {
        this.serverLogger = serverLogger;
        this.jwtSecretKey = jwtSecretKey;
        this.jwtExpirationInMs = jwtExpirationInMs;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.authMetadataService = authMetadataService;
        this.parser = parser;
        this.databaseReader = databaseReader;
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
    public String getUserEmailFromJWT(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(convertToBites(jwtSecretKey))
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    @Override
    public boolean validateJWT(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(convertToBites(jwtSecretKey))
                    .parseClaimsJws(authToken);

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
    public String getJwtFromRequest(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(HTTP_REQ_AUTH_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HTTP_REQ_AUTH_TOKEN_PREFIX)) {
            String jwt = bearerToken.substring(7, bearerToken.length());
            return jwt.equals("null") ? null : jwt;
        }
        return null;
    }


    @Override
    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthToken(String userEmail) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
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

        UserEntity currentlyAuthenticatedUser =
                getCurrentlyAuthenticatedUser()
                        .orElseThrow(() -> new UsernameNotFoundException("User not found by email!"));

        if (nonNull(currentlyAuthenticatedUser)) {

            String deviceDetails = getDeviceDetails(request.getHeader("user-agent"));
            String location = getIpLocation(extractIp(request));

            AuthMetadata authMetadata =
                    new AuthMetadata()
                            .setDeviceDetails(deviceDetails)
                            .setLocation(location)
                            .setLogin(ZonedDateTime.now())
                            .setJwt(jwt);

//            this.logoutUserFromAllSessions();
            currentlyAuthenticatedUser.getAuthMetadata().add(authMetadata);
            authMetadata.setUserEntity(currentlyAuthenticatedUser);
            this.userRepository.saveAndFlush(currentlyAuthenticatedUser);
        }
    }

    @Override
    public void logoutAllUserAuthMetadata() {

        /* logout previous active sessions */
        List<AuthMetadata> activeSessions = authMetadataService.getAllActiveSessionsForCurrentUser();
        activeSessions.forEach(System.out::println);

        activeSessions.forEach(as ->
                as.setLogout(ZonedDateTime.now())
                        .setSessionDuration(as.getLogout().toEpochSecond() - as.getLogin().toEpochSecond()));

        this.authMetadataService.saveAll(activeSessions);
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

    private Optional<UserEntity> getCurrentlyAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = "";
        if (principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            userEmail = principal.toString();
        }

        return this.userRepository.findByEmail(userEmail);
    }

    private byte[] convertToBites(String key) {
        return DatatypeConverter.parseBase64Binary(key);
    }
}
