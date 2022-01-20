package com.aviobrief.springserver.config.security.csrf;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static com.aviobrief.springserver.config.constants.ApplicationConstants.CSRF_TOKEN_SAFE_METHODS;

public class CsrfFilter extends OncePerRequestFilter {

    private final AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (csrfTokenIsRequired(httpServletRequest)) {
            String csrfHeaderToken = httpServletRequest.getHeader("X-XSRF-TOKEN");

            String csrfCookieToken = null;
            Cookie[] cookies = httpServletRequest.getCookies();
            if (cookies != null) {
                Optional<Cookie> csrfCookie = Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equals("XSRF-TOKEN"))
                        .findFirst();
                if (csrfCookie.isPresent()) {
                    csrfCookieToken = csrfCookie.get().getValue();
                }
            }

            if (csrfHeaderToken == null || csrfCookieToken == null || !csrfCookieToken.equals(csrfHeaderToken)) {
                accessDeniedHandler
                        .handle(httpServletRequest,
                                httpServletResponse,
                                new AccessDeniedException("XSRF tokens missing or not matching!"));
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean csrfTokenIsRequired(HttpServletRequest httpServletRequest) {
        return !CSRF_TOKEN_SAFE_METHODS.contains(httpServletRequest.getMethod());
    }

}
