package com.ubuuy.springserver.models.meta_data;

import com.ubuuy.springserver.models.entities.BaseEntity;
import com.ubuuy.springserver.models.entities.UserEntity;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "auth_metadata")
@Access(AccessType.PROPERTY)
public class AuthMetadata extends BaseEntity {

    private String deviceDetails;
    private String location;
    private ZonedDateTime login;
    private ZonedDateTime logout;
    private long sessionDuration;
    private String jwt;
    private UserEntity userEntity;

    public AuthMetadata() {
    }

    @Column(name = "device_details", unique = false, nullable = false)
    public String getDeviceDetails() {
        return deviceDetails;
    }

    public AuthMetadata setDeviceDetails(String deviceDetails) {
        this.deviceDetails = deviceDetails;
        return this;
    }

    @Column(name = "location", unique = false, nullable = false)
    public String getLocation() {
        return location;
    }

    public AuthMetadata setLocation(String location) {
        this.location = location;
        return this;
    }

    @Column(name = "login", nullable = false)
    public ZonedDateTime getLogin() {
        return login;
    }

    public AuthMetadata setLogin(ZonedDateTime login) {
        this.login = login;
        return this;
    }

    @Column(name = "logout")
    public ZonedDateTime getLogout() {
        return logout;
    }

    public AuthMetadata setLogout(ZonedDateTime logout) {
        this.logout = logout;
        return this;
    }

    @Column(name = "session_duration")
    public long getSessionDuration() {
        return sessionDuration;
    }

    public AuthMetadata setSessionDuration(long sessionDuration) {
        this.sessionDuration = sessionDuration;
        return this;
    }

    @Column(name = "jwt")
    public String getJwt() {
        return jwt;
    }

    public AuthMetadata setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }

    @ManyToOne
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public AuthMetadata setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }
}
