package com.aviobrief.springserver.services.servicesImpl;

import com.aviobrief.springserver.services.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
}
