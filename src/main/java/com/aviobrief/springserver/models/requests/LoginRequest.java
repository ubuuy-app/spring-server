package com.aviobrief.springserver.models.requests;

public record LoginRequest (String username, String password) {

    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }
}
