package com.aviobrief.springserver.config.security.filters.jwt;

import com.aviobrief.springserver.utils.response_builder.ResponseBuilder;
import com.aviobrief.springserver.utils.response_builder.responses.ErrorResponseObject;
import com.aviobrief.springserver.utils.response_builder.responses.SingleError;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.aviobrief.springserver.config.constants.LoggerMessages.JWT_UNAUTHORIZED_HANDLER_LOG_MESSAGE;
import static com.aviobrief.springserver.config.constants.ResponseMessages.JWT_UNAUTHORIZED_HANDLER_RES_MESSAGE;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    private final Gson gson;
    private final ResponseBuilder responseBuilder;

    public JwtAuthenticationEntryPoint(Gson gson, ResponseBuilder responseBuilder) {
        this.gson = gson;
        this.responseBuilder = responseBuilder;
    }


    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException authException) throws IOException, ServletException {

        logger.error(String.format("%s - '%s'", JWT_UNAUTHORIZED_HANDLER_LOG_MESSAGE, authException.getMessage()));

        /* Build error */
        String requestPath = responseBuilder.getRequestPath(httpServletRequest);

        SingleError singleError =
                responseBuilder
                        .buildSingleError(JWT_UNAUTHORIZED_HANDLER_RES_MESSAGE)
                        .setReason(authException.getMessage());

        ErrorResponseObject errorResponseObject =
                responseBuilder
                        .buildErrorObject()
                        .setStatus(HttpStatus.UNAUTHORIZED)
                        .setMessage(JWT_UNAUTHORIZED_HANDLER_RES_MESSAGE)
                        .setErrors(List.of(singleError))
                        .setPath(requestPath);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.getWriter().println(gson.toJson(errorResponseObject));

    }
}
