package com.ubuuy.springserver.web;

import com.ubuuy.springserver.config.constants.ResponseMessages;
import com.ubuuy.springserver.models.requests.LoginRequest;
import com.ubuuy.springserver.models.requests.RegisterOwnerRequest;
import com.ubuuy.springserver.models.responses.api_responses.LoginResponse;
import com.ubuuy.springserver.models.responses.api_responses.RegisterOrganizationOwnerResponse;
import com.ubuuy.springserver.models.service_models.UserServiceModel;
import com.ubuuy.springserver.services.AuthService;
import com.ubuuy.springserver.utils.json.JsonUtil;
import com.ubuuy.springserver.utils.response_builder.ResponseBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.ubuuy.springserver.utils.response_builder.ResponseBuilder.Type;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final ResponseBuilder responseBuilder;
    private final JsonUtil jsonUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          AuthService authService,
                          ResponseBuilder responseBuilder,
                          JsonUtil jsonUtil
    ) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.responseBuilder = responseBuilder;
        this.jsonUtil = jsonUtil;
    }

    @PostMapping(path = "/api/auth/register-organization-owner")
    public ResponseEntity<?> registerOwner(@RequestBody RegisterOwnerRequest registerOwnerRequest) {

        try {
            UserServiceModel registeredOwner = authService.registerOrganizationOwner(registerOwnerRequest);
            return ResponseEntity
                    .ok()
                    .body(new RegisterOrganizationOwnerResponse(
                            registeredOwner.getOrganization().getId(),
                            registeredOwner.getId()));

        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .body(responseBuilder
                            .buildErrorObject(true)
                            .setType(Type.REGISTER)
                            .setStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                            .setMessage("Server could not process the request")
                            .setErrors(new ArrayList<>()))
                    ;
        }
    }


    @PostMapping(path = "/api/auth/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {

        try {
            /* AUTHENTICATE IN SPRING */
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());

            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            /* GENERATE LOGIN RESPONSE ( JWT AND CUSTOM CLAIMS ) */
            LoginResponse loginResponse = authService.generateLoginResponse(loginRequest.email());

            /* GENERATE DOUBLE SUBMIT COOKIE (WITH CSRF TOKEN) HEADER */
            HttpHeaders responseHeaders = authService.generateDoubleSubmitCookieHeader();

            /* ADD LOGIN TO USER HISTORY */
            authService.addLoginToUserHistory(loginRequest.email(), request, loginResponse.getAccessToken());

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(loginResponse);

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .body(responseBuilder
                            .buildErrorObject(true)
                            .setType(Type.AUTH)
                            .setStatus(HttpStatus.BAD_REQUEST)
                            .setMessage(ResponseMessages.BAD_CREDENTIALS)
                            .setErrors(List.of(
                                    responseBuilder
                                            .buildSingleError()
                                            .setTarget("credentials")
                                            .setMessage(ResponseMessages.BAD_CREDENTIALS)
                                            .setRejectedValue(jsonUtil.toJson(
                                                    jsonUtil.pair("email", loginRequest.email()),
                                                    jsonUtil.pair("password", "hidden")
                                            ))
                                            .setReason(ResponseMessages.BAD_CREDENTIALS)
                            )));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .build();
        }


    }

    @GetMapping(path = "/api/auth/logout", produces = "application/json")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        try {
            authService.logoutAllUserAuthMetadata();
            HttpHeaders responseHeaders = authService.invalidateCsrfTokenCookie();
            SecurityContextHolder.getContext().setAuthentication(null);

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(responseBuilder.ok(true));

        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .body(responseBuilder
                            .buildErrorObject(true)
                            .setType(Type.AUTH)
                            .setStatus(HttpStatus.BAD_REQUEST)
                            .setMessage("No active session found for provided jwt!")
                            .setErrors(List.of(
                                    responseBuilder
                                            .buildSingleError()
                                            .setTarget("credentials")
                                            .setMessage(ResponseMessages.BAD_CREDENTIALS)
                                            .setRejectedValue(jsonUtil.toJson(
                                                    jsonUtil.pair("jwt", authService.getJwtFromRequest(request))
                                            ))
                                            .setReason(ResponseMessages.BAD_CREDENTIALS)
                            )));
        }
    }

    @GetMapping(path = "/.well-known/first-party-set")
    public ResponseEntity<?> serveWellKnownSamePartyJson() {
        return ResponseEntity.ok().body(new SamePartyWellKnownOwnerMembers());

    }

    @GetMapping(path = "/first-party-cookie")
    public ResponseEntity<?> getFirstPartyCookie() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(
                "Set-Cookie",
                "x-fpt=first-party-test; Max-Age=604800; Path=/dev; Secure; SameSite=Lax; SameParty "
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(new SamePartyWellKnownOwnerMembers());

    }

    private class SamePartyWellKnownOwnerMembers {

        private final String owner = "localhost:8000";
        private int version = 1;
        private List<String> members = List.of("localhost:3000");

        public SamePartyWellKnownOwnerMembers() {
        }

        public String getOwner() {
            return owner;
        }

        public int getVersion() {
            return version;
        }

        public List<String> getMembers() {
            return members;
        }
    }
}
