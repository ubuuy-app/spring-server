package com.aviobrief.springserver.services.servicesImpl;

import com.aviobrief.springserver.services.AuthService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsSpringService userDetailsSpringService;

    public AuthServiceImpl(UserDetailsSpringService userDetailsSpringService) {
        this.userDetailsSpringService = userDetailsSpringService;
    }

    @Override
    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthToken(String userEmail) {

        UserDetails userDetails = userDetailsSpringService.loadUserByUsername(userEmail);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }
}
