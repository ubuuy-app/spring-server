package com.aviobrief.springserver.models.responses;

public class JwtAuthToken {

    private String accessToken;
    private String type = "Bearer";

    public JwtAuthToken(String accessToken) {
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
