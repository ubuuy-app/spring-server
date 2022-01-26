package com.aviobrief.springserver.services.servicesImpl;

import com.aviobrief.springserver.models.auth.AuthMetadata;
import com.aviobrief.springserver.models.entities.UserEntity;
import com.aviobrief.springserver.repositories.UserRepository;
import com.aviobrief.springserver.services.AuthMetadataService;
import com.aviobrief.springserver.services.AuthService;
import com.google.common.base.Strings;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserDetailsSpringService userDetailsSpringService;
    private final AuthMetadataService authMetadataService;
    private final Parser parser;
    private final DatabaseReader databaseReader;


    public AuthServiceImpl(UserRepository userRepository, UserDetailsSpringService userDetailsSpringService,
                           AuthMetadataService authMetadataService,
                           Parser parser,
                           DatabaseReader databaseReader) {
        this.userRepository = userRepository;
        this.userDetailsSpringService = userDetailsSpringService;
        this.authMetadataService = authMetadataService;
        this.parser = parser;
        this.databaseReader = databaseReader;
    }


    @Override
    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthToken(String userEmail) {
        UserDetails userDetails = userDetailsSpringService.loadUserByUsername(userEmail);
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

            this.logoutUserFromAllSessions();
            currentlyAuthenticatedUser.getAuthMetadata().add(authMetadata);
            authMetadata.setUserEntity(currentlyAuthenticatedUser);
            this.userRepository.saveAndFlush(currentlyAuthenticatedUser);
        }
    }

    @Override
    public void logoutUserFromAllSessions() {

        /* logout previous active sessions */
        List<AuthMetadata> activeSessions = authMetadataService.getAllActiveSessionsForCurrentUser();

        if (nonNull(activeSessions)) {
            activeSessions.forEach(as ->
                    as.setLogout(ZonedDateTime.now())
                            .setSessionDuration(as.getLogout().toEpochSecond() - as.getLogin().toEpochSecond())
                            .setJwt(null));
        }
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
}
