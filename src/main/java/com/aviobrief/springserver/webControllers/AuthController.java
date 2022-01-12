package com.aviobrief.springserver.webControllers;


import com.aviobrief.springserver.config.security.filters.jwt.JwtTokenProvider;
import com.aviobrief.springserver.models.requests.LoginRequest;
import com.aviobrief.springserver.models.responses.ApiOkTrueOrFalse;
import com.aviobrief.springserver.models.responses.JwtAuthToken;
import com.aviobrief.springserver.models.views.UserViewModel;
import com.aviobrief.springserver.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class AuthController {


    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;


    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
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

            return ResponseEntity.ok(new JwtAuthToken(jwt));

        }catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .body(new ApiOkTrueOrFalse(false));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .build();
        }


    }

    @GetMapping(path = "/auth-logout", produces = "application/json")
    public ResponseEntity<ApiOkTrueOrFalse> logout() {
        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
        return ResponseEntity.ok().body(new ApiOkTrueOrFalse(true));
    }


}
