package com.ubuuy.springserver.models.responses;

public class UserResponseModel {

    private String email;
    private String fullName;

    public UserResponseModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
