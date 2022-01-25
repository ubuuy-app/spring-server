package com.aviobrief.springserver.models.auth;

import com.aviobrief.springserver.models.entities.BaseEntity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auth_metadata")
@Access(AccessType.PROPERTY)
public class AuthMetadata extends BaseEntity {

    private List<AuthSession> authSessions = new ArrayList<>();
    private String deviceDetails;
    private String location;
    private String jwt;
    public  static AuthMetadata newAuthMetadata(){return new AuthMetadata();}

    public AuthMetadata() {
    }

    public AuthMetadata addUserSession(ZonedDateTime login) {
        authSessions.add(new AuthSession(login));
        return this;
    }

    @OneToMany
    @JoinColumn(name="id")
    public List<AuthSession> getUserSessions() {
        return authSessions;
    }

    public AuthMetadata setUserSessions(List<AuthSession> authSessions) {
        this.authSessions = authSessions;
        return this;
    }

    public String getDeviceDetails() {
        return deviceDetails;
    }

    public AuthMetadata setDeviceDetails(String deviceDetails) {
        this.deviceDetails = deviceDetails;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public AuthMetadata setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getJwt() {
        return jwt;
    }

    public AuthMetadata setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }
}
