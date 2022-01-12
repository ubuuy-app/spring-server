package com.aviobrief.springserver.utils.api_response_builder.response_models;

public class ApiJwtResponse {

    private String accessToken;
    private String type = "Bearer";

    public ApiJwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String type) {
        this.type = type;
    }
}
