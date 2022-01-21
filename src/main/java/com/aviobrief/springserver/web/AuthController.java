package com.aviobrief.springserver.web;


import com.aviobrief.springserver.config.security.jwt.JwtTokenProvider;
import com.aviobrief.springserver.models.requests.LoginRequest;
import com.aviobrief.springserver.services.AuthService;
import com.aviobrief.springserver.services.UserService;
import com.aviobrief.springserver.utils.json.JsonUtil;
import com.aviobrief.springserver.utils.response_builder.ResponseBuilder;
import com.aviobrief.springserver.utils.response_builder.responses.JwtResponse;
import com.aviobrief.springserver.utils.response_builder.responses.OkResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

import static com.aviobrief.springserver.config.constants.ResponseMessages.BAD_CREDENTIALS;
import static com.aviobrief.springserver.utils.response_builder.ResponseBuilder.Type;

@RestController
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider tokenProvider;
    private final ResponseBuilder responseBuilder;
    private final JsonUtil jsonUtil;


    public AuthController(AuthService authService, UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider,
                          ResponseBuilder responseBuilder, JsonUtil jsonUtil) {
        this.authService = authService;
        this.tokenProvider = tokenProvider;
        this.responseBuilder = responseBuilder;
        this.jsonUtil = jsonUtil;
    }


    @PostMapping(path = "/api/auth")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest,
                                              HttpServletResponse httpServletResponse) {


        try {

            SecurityContextHolder.getContext()
                    .setAuthentication(authService.getUsernamePasswordAuthToken(loginRequest.username()));

            String jwt = tokenProvider.generateToken(loginRequest.username());


            HttpHeaders responseHeaders = new HttpHeaders();
            String csrfToken = UUID.randomUUID().toString();
            responseHeaders.set("X-CSRF-TOKEN", csrfToken);

            Cookie cookie = new Cookie("CSRF-TOKEN", csrfToken);
            cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
            cookie.setSecure(true);//TODO - TO BE TRUE in production
            cookie.setHttpOnly(true);

            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(new JwtResponse(jwt));

        } catch (UsernameNotFoundException e) {
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .body(responseBuilder
                            .buildErrorObject(true)
                            .setType(Type.AUTH)
                            .setStatus(HttpStatus.BAD_REQUEST)
                            .setMessage(BAD_CREDENTIALS)
                            .setErrors(List.of(
                                    responseBuilder
                                            .buildSingleError()
                                            .setTarget("credentials")
                                            .setMessage(BAD_CREDENTIALS)
                                            .setRejectedValue(jsonUtil.toJson(
                                                    jsonUtil.pair("email", loginRequest.username()),
                                                    jsonUtil.pair("password", "hidden")
                                            ))
                                            .setReason(BAD_CREDENTIALS)
                            )));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .build();
        }


    }

    @GetMapping(path = "/auth-logout", produces = "application/json")
    public ResponseEntity<OkResponse> logout() {
        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
        return ResponseEntity.ok().body(responseBuilder.ok(true));
    }
}
