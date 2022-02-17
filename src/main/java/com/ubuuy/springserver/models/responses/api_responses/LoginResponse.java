package com.ubuuy.springserver.models.responses.api_responses;

import com.ubuuy.springserver.models.service_models.CustomClaimsServiceModel;

public class LoginResponse extends JwtResponse{

    private CustomClaimsServiceModel customClaims;


    public LoginResponse(String accessToken) {
        super(accessToken);
    }

    public CustomClaimsServiceModel getCustomClaims() {
        return customClaims;
    }

    public LoginResponse setCustomClaims(CustomClaimsServiceModel customClaims) {
        this.customClaims = customClaims;
        return this;
    }
}
