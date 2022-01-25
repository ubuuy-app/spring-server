package com.aviobrief.springserver.models.auth;

import com.aviobrief.springserver.models.entities.BaseEntity;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "auth_sessions")
@Access(AccessType.PROPERTY)
public class AuthSession extends BaseEntity {

    private ZonedDateTime login;
    private ZonedDateTime logout;
    private long sessionDuration;
    private String jwt;
    private AuthMetadata authMetadata;


    public AuthSession() {
    }

    public AuthSession(ZonedDateTime login) {
        this.login = login;
    }

    public ZonedDateTime getLogin() {
        return login;
    }

    public AuthSession setLogin(ZonedDateTime login) {
        this.login = login;
        return this;
    }

    public ZonedDateTime getLogout() {
        return logout;
    }

    public AuthSession setLogout(ZonedDateTime logout) {
        this.logout = logout;
        return this;
    }

    public long getSessionDuration() {
        return sessionDuration;
    }

    public AuthSession setSessionDuration(long sessionDuration) {
        this.sessionDuration = sessionDuration;
        return this;
    }

    public String getJwt() {
        return jwt;
    }

    public AuthSession setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "auth_session_id")
    public AuthMetadata getAuthMetadata() {
        return authMetadata;
    }

    public AuthSession setAuthMetadata(AuthMetadata authMetadata) {
        this.authMetadata = authMetadata;
        return this;
    }
}
