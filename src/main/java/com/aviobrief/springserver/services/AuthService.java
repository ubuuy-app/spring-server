package com.aviobrief.springserver.services;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface AuthService {

    UsernamePasswordAuthenticationToken getUsernamePasswordAuthToken(String userEmail);

    HttpHeaders generateDoubleSubmitCookieHeader();

    void addLoginToUserHistory(String userEmail, HttpServletRequest request, String jwt) throws IOException, GeoIp2Exception;
}
