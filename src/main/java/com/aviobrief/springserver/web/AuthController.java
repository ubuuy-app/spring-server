package com.aviobrief.springserver.web;


import com.aviobrief.springserver.models.requests.LoginRequest;
import com.aviobrief.springserver.services.AuthService;
import com.aviobrief.springserver.services.UserService;
import com.aviobrief.springserver.utils.json.JsonUtil;
import com.aviobrief.springserver.utils.response_builder.ResponseBuilder;
import com.aviobrief.springserver.utils.response_builder.responses.JwtResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.aviobrief.springserver.config.constants.ResponseMessages.BAD_CREDENTIALS;
import static com.aviobrief.springserver.utils.response_builder.ResponseBuilder.Type;

@RestController
public class AuthController {

    private final AuthService authService;

    private final ResponseBuilder responseBuilder;
    private final JsonUtil jsonUtil;


    public AuthController(AuthService authService, UserService userService,
                          AuthenticationManager authenticationManager,
                          ResponseBuilder responseBuilder, JsonUtil jsonUtil) {
        this.authService = authService;

        this.responseBuilder = responseBuilder;
        this.jsonUtil = jsonUtil;
    }


    @PostMapping(path = "/api/auth")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {

        try {
            /* AUTHENTICATE IN SPRING */
            UsernamePasswordAuthenticationToken token = authService.getUsernamePasswordAuthToken(loginRequest.username());
            SecurityContextHolder.getContext().setAuthentication(token);

            /* GENERATE JWT RESPONSE */
            String jwt = authService.generateJWT(loginRequest.username());
            JwtResponse jwtResponse = new JwtResponse(jwt);

            /* GENERATE DOUBLE SUBMIT COOKIE (WITH CSRF TOKEN) HEADER */
            HttpHeaders responseHeaders = authService.generateDoubleSubmitCookieHeader();

            /* ADD LOGIN TO USER HISTORY */
            authService.addLoginToUserHistory(loginRequest.username(), request, jwt);

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(jwtResponse);

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
            System.out.println(e);
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .build();
        }


    }

    @PostMapping(path = "/api/auth/logout", produces = "application/json")
    public ResponseEntity<?> logout() {

        authService.logoutAllUserAuthMetadata();
        HttpHeaders responseHeaders = authService.invalidateCsrfTokenCookie();

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(responseBuilder.ok(true));
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
