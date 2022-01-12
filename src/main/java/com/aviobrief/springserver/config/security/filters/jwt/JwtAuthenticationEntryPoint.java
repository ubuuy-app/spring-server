package com.aviobrief.springserver.config.security.filters.jwt;

import com.aviobrief.springserver.utils.api_response_builder.ApiResponseBuilder;
import com.aviobrief.springserver.utils.api_response_builder.response_models.error_models.ApiErrorObjectResponse;
import com.aviobrief.springserver.utils.api_response_builder.response_models.error_models.ApiSingleError;
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
    private final ApiResponseBuilder apiResponseBuilder;

    public JwtAuthenticationEntryPoint(Gson gson, ApiResponseBuilder apiResponseBuilder) {
        this.gson = gson;
        this.apiResponseBuilder = apiResponseBuilder;
    }


    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException authException) throws IOException, ServletException {

        logger.error(String.format("%s - '%s'", JWT_UNAUTHORIZED_HANDLER_LOG_MESSAGE, authException.getMessage()));

        /* Build error */
        String requestPath = apiResponseBuilder.getRequestPath(httpServletRequest);

        ApiSingleError apiSingleError =
                apiResponseBuilder
                        .buildSingleError(JWT_UNAUTHORIZED_HANDLER_RES_MESSAGE)
                        .setReason(authException.getMessage());

        ApiErrorObjectResponse apiErrorObjectResponse =
                apiResponseBuilder
                        .buildErrorObject()
                        .setStatus(HttpStatus.UNAUTHORIZED)
                        .setMessage(JWT_UNAUTHORIZED_HANDLER_RES_MESSAGE)
                        .setErrors(List.of(apiSingleError))
                        .setPath(requestPath);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.getWriter().println(gson.toJson(apiErrorObjectResponse));

    }
}
