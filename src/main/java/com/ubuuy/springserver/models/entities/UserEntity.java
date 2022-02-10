package com.ubuuy.springserver.models.entities;


import com.ubuuy.springserver.models.auth.AuthMetadata;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Access(AccessType.PROPERTY)
public class UserEntity extends BaseEntity {

    private String email;
    private String fullName;
    private String password;
    private List<RoleEntity> roles = new ArrayList<>();
    private MetaEntity metaEntity;
    private List<AuthMetadata> authMetadata = new ArrayList<>();
    private OrganizationEntity organization;


    public UserEntity() {
    }

    @Column(name = "email", unique = true, nullable = true)
    //todo - email must not be empty, set like this for dev process
    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @Column(name = "full_name", unique = false, nullable = false)
    public String getFullName() {
        return fullName;
    }

    public UserEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Column(name = "password", unique = false, nullable = false)
    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<RoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<RoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    public MetaEntity getMeta() {
        return metaEntity;
    }

    public UserEntity setMeta(MetaEntity metaEntity) {
        this.metaEntity = metaEntity;
        return this;
    }

    @OneToMany(
            mappedBy = "userEntity", targetEntity = AuthMetadata.class,
            cascade = CascadeType.ALL)
    public List<AuthMetadata> getAuthMetadata() {
        return authMetadata;
    }

    public UserEntity setAuthMetadata(List<AuthMetadata> authMetadata) {
        this.authMetadata = authMetadata;
        return this;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public OrganizationEntity getOrganization() {
        return organization;
    }

    public UserEntity setOrganization(OrganizationEntity organization) {
        this.organization = organization;
        return this;
    }
}
