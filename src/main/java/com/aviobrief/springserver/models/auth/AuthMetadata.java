package com.aviobrief.springserver.models.auth;

import com.aviobrief.springserver.models.entities.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auth_metadata")
@Access(AccessType.PROPERTY)
public class AuthMetadata extends BaseEntity {

    private List<AuthSession> authSessions = new ArrayList<>();
    private String deviceDetails;
    private String location;

    public AuthMetadata() {
    }

    public AuthMetadata addAuthSession(AuthSession authSession) {
        this.authSessions.add(authSession);
        return this;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "authMetadata", targetEntity = AuthSession.class)
    public List<AuthSession> getAuthSessions() {
        return authSessions;
    }

    public AuthMetadata setAuthSessions(List<AuthSession> authSessions) {
        this.authSessions = authSessions;
        return this;
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


}
