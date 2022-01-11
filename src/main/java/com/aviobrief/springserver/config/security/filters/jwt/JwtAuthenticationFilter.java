package com.aviobrief.springserver.config.security.filters.jwt;

import com.aviobrief.springserver.config.security.services.SpringSecurityUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.aviobrief.springserver.config.constants.LoggerMessages.JWT_VERIFY_FAIL;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider tokenProvider;
    private final SpringSecurityUserDetailsService springSecurityUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider,
                                   SpringSecurityUserDetailsService springSecurityUserDetailsService) {
        this.tokenProvider = tokenProvider;
        this.springSecurityUserDetailsService = springSecurityUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = getJwtFromRequest(httpServletRequest);

            /* Check Jwt has some text in it and verify validity */
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

                /* extract email from jwt */
                String userEmail = tokenProvider.getUserEmailFromJWT(jwt);

                /* Load Spring user details */
                UserDetails userDetails = springSecurityUserDetailsService.loadUserByUsername(userEmail);

                /* The next two steps do the magic of signing in into Spring Security, when the token is valid */
                /* (1) generate internal username and password auth token */
                UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                /* (2) manually authenticate (set in Security Context) the authentication token */
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
            } else {
                logger.error(JWT_VERIFY_FAIL);
            }

        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
