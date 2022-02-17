package com.ubuuy.springserver.models.service_models;

import java.util.List;

public class CustomClaimsServiceModel {

    private String email;
    private String fullName;
    private Long organizationId;
    private String organizationName;
    private List<String> roles;

    public CustomClaimsServiceModel() {
    }

    public String getEmail() {
        return email;
    }

    public CustomClaimsServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public CustomClaimsServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public CustomClaimsServiceModel setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public CustomClaimsServiceModel setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public CustomClaimsServiceModel setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }
}
