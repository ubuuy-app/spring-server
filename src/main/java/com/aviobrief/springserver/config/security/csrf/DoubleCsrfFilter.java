package com.aviobrief.springserver.config.security.csrf;

import com.aviobrief.springserver.utils.json.JsonUtil;
import com.aviobrief.springserver.utils.response_builder.ResponseBuilder;
import com.aviobrief.springserver.utils.response_builder.responses.ErrorResponseObject;
import com.aviobrief.springserver.utils.response_builder.responses.SingleError;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.aviobrief.springserver.config.constants.ApplicationConstants.CSRF_DISABLED_PATH;
import static com.aviobrief.springserver.config.constants.ApplicationConstants.CSRF_TOKEN_SAFE_METHODS;
import static com.aviobrief.springserver.config.constants.ResponseMessages.UNAUTHORIZED_HANDLER_RES_MESSAGE;

@Component
public class DoubleCsrfFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger("DoubleCsrfFilter");
    private final Gson gson;
    private final ResponseBuilder responseBuilder;
    private final JsonUtil jsonUtil;

    public DoubleCsrfFilter(Gson gson, ResponseBuilder responseBuilder, JsonUtil jsonUtil) {
        this.gson = gson;
        this.responseBuilder = responseBuilder;
        this.jsonUtil = jsonUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest httpServletRequest) throws ServletException {
        String path = httpServletRequest.getRequestURI();
        return CSRF_DISABLED_PATH.equals(path);
    }

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

            if (csrfCookieToken == null || !csrfCookieToken.equals(csrfHeaderToken)) {
                logger.error("error");
                sendCsrfErrorResponse(httpServletRequest,httpServletResponse, csrfCookieToken, csrfHeaderToken);
                return;
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean csrfTokenIsRequired(HttpServletRequest httpServletRequest) {
        return !CSRF_TOKEN_SAFE_METHODS.contains(httpServletRequest.getMethod());
    }

    private void sendCsrfErrorResponse(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse,
                                       String csrfCookieToken, String csrfHeaderToken) throws IOException {

        boolean anyCsrfIsNull = !StringUtils.hasText(csrfCookieToken) || !StringUtils.hasText(csrfHeaderToken);

        /* Build error */
        String requestPath = responseBuilder.getRequestPath(httpServletRequest);

        SingleError singleError =
                responseBuilder
                        .buildSingleError()
                        .setTarget("csrf")
                        .setMessage(UNAUTHORIZED_HANDLER_RES_MESSAGE)
                        .setRejectedValue(anyCsrfIsNull
                                ? jsonUtil.toJson(jsonUtil.pair("csrf", "null or (empty string)"))
                                : jsonUtil.toJson(jsonUtil.pair("csrf", "csrf token mismatch")))
                        .setReason("wrong or missing csrf token data!");


        ErrorResponseObject errorResponseObject =
                responseBuilder
                        .buildErrorObject()
                        .setStatus(HttpStatus.UNAUTHORIZED)
                        .setMessage(UNAUTHORIZED_HANDLER_RES_MESSAGE)
                        .setErrors(List.of(singleError))
                        .setPath(requestPath);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.getWriter().println(gson.toJson(errorResponseObject));
    }

}
