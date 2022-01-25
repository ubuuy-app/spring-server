package com.aviobrief.springserver.models.auth;

import com.aviobrief.springserver.models.entities.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auth_metadata")
@Access(AccessType.PROPERTY)
public class AuthMetadata extends BaseEntity {

    private String deviceDetails;
    private String location;
    private List<AuthSession> authSessions = new ArrayList<>();

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

    @OneToMany(
            mappedBy = "authMetadata", targetEntity = AuthSession.class,
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<AuthSession> getAuthSessions() {
        return authSessions;
    }

    public AuthMetadata setAuthSessions(List<AuthSession> authSessions) {
        this.authSessions = authSessions;
        return this;
    }

    public AuthMetadata addAuthSession(AuthSession authSession) {
        this.authSessions.add(authSession);
        return this;
    }


}
