package com.ubuuy.springserver.services;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.ubuuy.springserver.models.service_models.OrganizationServiceModel;
import com.ubuuy.springserver.models.service_models.UserServiceModel;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface AuthService {
    String generateJWT(String userEmail);

    String getUserEmailFromJWT(String jwt);

    boolean validateJWT(String jwt);

    boolean jwtIsLoggedOut(String jwt, String userEmail);

    String getJwtFromRequest(HttpServletRequest request);

    UsernamePasswordAuthenticationToken getUsernamePasswordAuthToken(String userEmail);

    HttpHeaders generateDoubleSubmitCookieHeader();

    HttpHeaders invalidateCsrfTokenCookie();

    void addLoginToUserHistory(String userEmail, HttpServletRequest request, String jwt) throws IOException, GeoIp2Exception;

    void logoutAllUserAuthMetadata();

    void registerOrganizationOwner(UserServiceModel userServiceModel, OrganizationServiceModel organizationServiceModel);

}
