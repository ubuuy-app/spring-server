package com.ubuuy.springserver.config.security.jwt;

import com.google.gson.Gson;
import com.ubuuy.springserver.config.constants.LoggerMessages;
import com.ubuuy.springserver.config.constants.ResponseMessages;
import com.ubuuy.springserver.services.AuthService;
import com.ubuuy.springserver.utils.json.JsonUtil;
import com.ubuuy.springserver.utils.response_builder.ResponseBuilder;
import com.ubuuy.springserver.utils.response_builder.responses.ErrorResponseObject;
import com.ubuuy.springserver.utils.response_builder.responses.SingleError;
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

import static com.ubuuy.springserver.utils.response_builder.ResponseBuilder.Type;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    private final AuthService authService;
    private final Gson gson;
    private final ResponseBuilder responseBuilder;
    private final JsonUtil jsonUtil;

    public JwtAuthenticationEntryPoint(AuthService authService,
                                       Gson gson,
                                       ResponseBuilder responseBuilder,
                                       JsonUtil jsonUtil) {
        this.authService = authService;
        this.gson = gson;
        this.responseBuilder = responseBuilder;
        this.jsonUtil = jsonUtil;
    }


    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException authException) throws IOException, ServletException {

        logger.error(String.format("%s - '%s'", LoggerMessages.JWT_UNAUTHORIZED_HANDLER_LOG_MESSAGE, authException.getMessage()));

        /* Build error */
        String requestPath = responseBuilder.getRequestPath(httpServletRequest);

        SingleError singleError =
                responseBuilder
                        .buildSingleError()
                        .setTarget("jwt")
                        .setMessage(ResponseMessages.UNAUTHORIZED_HANDLER_RES_MESSAGE)
                        .setRejectedValue(authService.getJwtFromRequest(httpServletRequest) == null
                                ? jsonUtil.toJson(jsonUtil.pair("jwt", "(empty string)"))
                                : jsonUtil.toJson(jsonUtil.pair("jwt", authService.getJwtFromRequest(httpServletRequest))))
                        .setReason(authException.getMessage());


        ErrorResponseObject errorResponseObject =
                responseBuilder
                        .buildErrorObject()
                        .setType(Type.AUTH)
                        .setStatus(HttpStatus.UNAUTHORIZED)
                        .setMessage(ResponseMessages.UNAUTHORIZED_HANDLER_RES_MESSAGE)
                        .setErrors(List.of(singleError))
                        .setPath(requestPath);

        /* Send error */
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.getWriter().println(gson.toJson(errorResponseObject));

    }
}

