package com.aviobrief.springserver.models.requests;

public class RegisterOwnerRequest {

    private String email;
    private String fullName;
    private String organization;
    private String password;

    public String getEmail() {
        return email;
    }

    public RegisterOwnerRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public RegisterOwnerRequest setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getOrganization() {
        return organization;
    }

    public RegisterOwnerRequest setOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterOwnerRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
