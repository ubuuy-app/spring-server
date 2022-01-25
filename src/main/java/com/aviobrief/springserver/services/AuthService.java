package com.aviobrief.springserver.services;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    UsernamePasswordAuthenticationToken getUsernamePasswordAuthToken(String userEmail);

    HttpHeaders generateDoubleSubmitCookieHeader();

    void addLoginToUserHistory(String userEmail, HttpServletRequest request);
}
