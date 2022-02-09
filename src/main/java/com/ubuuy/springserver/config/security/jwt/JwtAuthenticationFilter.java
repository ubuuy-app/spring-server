package com.ubuuy.springserver.config.security.jwt;

import com.ubuuy.springserver.config.constants.ApplicationConstants;
import com.ubuuy.springserver.config.constants.LoggerMessages;
import com.ubuuy.springserver.services.AuthService;
import com.ubuuy.springserver.services.servicesImpl.UserDetailsService;
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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final UserDetailsService userDetailsService;
    private final AuthService authService;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, AuthService authService) {
        this.userDetailsService = userDetailsService;
        this.authService = authService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest httpServletRequest) throws ServletException {
        String path = httpServletRequest.getRequestURI();
        return ApplicationConstants.JWT_FILTER_DISABLED_PATHS.contains(path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = authService.getJwtFromRequest(httpServletRequest);

            /* Check Jwt has some text in it and verify validity */
            if (StringUtils.hasText(jwt) && authService.validateJWT(jwt)) {

                /* extract email from jwt */
                String userEmail = authService.getUserEmailFromJWT(jwt);

                /* check jwt does not belong to logged out AuthMetadata or is not missing */
//                if (authService.jwtIsLoggedOut(jwt, userEmail)) {
//                    throw new IllegalArgumentException();
//                }

                /* Load Spring user details */
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                /* The next two steps do the magic of signing in into Spring Security */
                /* (1) generate internal username and password auth token */
                UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                /* manually authenticate (set the authentication token in Security Context) */
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);

            } else {
                logger.error(LoggerMessages.JWT_VERIFICATION_FAIL);
            }

        } catch (Exception ex) {
            logger.error(LoggerMessages.SECURITY_CONTEXT_SET_AUTH_TOKEN_FAIL, ex);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
