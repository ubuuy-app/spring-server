package com.aviobrief.springserver.services;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface AuthService {

    UsernamePasswordAuthenticationToken getUsernamePasswordAuthToken(String userEmail);
}
