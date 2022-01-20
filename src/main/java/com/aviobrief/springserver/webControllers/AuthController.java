package com.aviobrief.springserver.webControllers;


import com.aviobrief.springserver.config.security.filters.jwt.JwtTokenProvider;
import com.aviobrief.springserver.models.requests.LoginRequest;
import com.aviobrief.springserver.models.responses.UserViewModel;
import com.aviobrief.springserver.services.UserService;
import com.aviobrief.springserver.utils.json.JsonUtil;
import com.aviobrief.springserver.utils.response_builder.ResponseBuilder;
import com.aviobrief.springserver.utils.response_builder.responses.JwtResponse;
import com.aviobrief.springserver.utils.response_builder.responses.OkResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aviobrief.springserver.config.constants.ResponseMessages.BAD_CREDENTIALS;
import static com.aviobrief.springserver.utils.response_builder.ResponseBuilder.Type;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class AuthController {


    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final ResponseBuilder responseBuilder;
    private final JsonUtil jsonUtil;


    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider,
                          ResponseBuilder responseBuilder, JsonUtil jsonUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.responseBuilder = responseBuilder;
        this.jsonUtil = jsonUtil;
    }


//    @GetMapping(path = "/basic-auth", produces = "application/json")
//    public ResponseEntity<ApiOkTrueOrFalse> authenticate() {
//        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
//        return ResponseEntity.ok().body(new ApiOkTrueOrFalse(true));
//    }

    @PostMapping(path = "/api/auth")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        try {
            UserViewModel userViewModel = userService.getByEmail(loginRequest.username());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(loginRequest.username());

            return ResponseEntity.ok(new JwtResponse(jwt));

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
