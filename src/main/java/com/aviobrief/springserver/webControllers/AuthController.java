package com.aviobrief.springserver.webControllers;


import com.aviobrief.springserver.responseBeans.ResponseOkTrueOrFalse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class AuthController {


    @GetMapping(path = "/basic-auth", produces = "application/json")
    public ResponseEntity<ResponseOkTrueOrFalse> authenticate() {
        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
        return ResponseEntity.ok().body(new ResponseOkTrueOrFalse(true));
    }

    @GetMapping(path = "/auth-logout", produces = "application/json")
    public ResponseEntity<ResponseOkTrueOrFalse> logout() {
        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
        return ResponseEntity.ok().body(new ResponseOkTrueOrFalse(true));
    }


}
