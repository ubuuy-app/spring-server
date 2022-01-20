package com.aviobrief.springserver.config.security.jwt;

import com.aviobrief.springserver.services.servicesImpl.UserDetailsSpringService;
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

import static com.aviobrief.springserver.config.constants.LoggerMessages.JWT_VERIFICATION_FAIL;
import static com.aviobrief.springserver.config.constants.LoggerMessages.SECURITY_CONTEXT_SET_AUTH_TOKEN_FAIL;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsSpringService userDetailsSpringService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider,
                                   UserDetailsSpringService userDetailsSpringService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsSpringService = userDetailsSpringService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = tokenProvider.getJwtFromRequest(httpServletRequest);

            /* Check Jwt has some text in it and verify validity */
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

                /* extract email from jwt */
                String userEmail = tokenProvider.getUserEmailFromJWT(jwt);

                /* Load Spring user details */
                UserDetails userDetails = userDetailsSpringService.loadUserByUsername(userEmail);

                /* The next two steps do the magic of signing in into Spring Security */
                /* (1) generate internal username and password auth token */
                UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                /* manually authenticate (set the authentication token in Security Context) */
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);

            } else {
                logger.error(JWT_VERIFICATION_FAIL);
            }

        } catch (Exception ex) {
            logger.error(SECURITY_CONTEXT_SET_AUTH_TOKEN_FAIL, ex);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
