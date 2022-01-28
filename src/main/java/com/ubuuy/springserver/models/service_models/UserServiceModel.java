package com.ubuuy.springserver.models.service_models;

import com.ubuuy.springserver.models.entities.Meta;
import com.ubuuy.springserver.models.entities.OrganizationEntity;
import com.ubuuy.springserver.models.entities.RoleEntity;

import java.util.ArrayList;
import java.util.List;

public class UserServiceModel extends BaseServiceModel{

    private String fullName;
    private String email;
    private String password;
    private List<RoleEntity> roles = new ArrayList<>();
    private Meta meta;
    private OrganizationEntity organization;

    public UserServiceModel() {
    }

    /* For initial seed direct UserEntity creation */
    public UserServiceModel(String email, String firstName, String password) {
        this.email = email;
        this.fullName = firstName;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public UserServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public UserServiceModel setRoles(List<RoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public Meta getMeta() {
        return meta;
    }

    public UserServiceModel setMeta(Meta meta) {
        this.meta = meta;
        return this;
    }

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public UserServiceModel setOrganization(OrganizationEntity organization) {
        this.organization = organization;
        return this;
    }
}
