package com.aviobrief.springserver.models.requests;

public record LogoutRequest(String userEmail) {

    @Override
    public String userEmail() {
        return userEmail;
    }

}
