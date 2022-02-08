package com.ubuuy.springserver.models.service_models;

public class CustomClaimsServiceModel {

    private String email;
    private String fullName;
    private Long organizationId;
    private String organizationName;

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
}
