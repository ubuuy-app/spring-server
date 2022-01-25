package com.aviobrief.springserver.models.auth;

import com.aviobrief.springserver.models.entities.BaseEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "auth_sessions")
@Access(AccessType.PROPERTY)
public class AuthSession extends BaseEntity {
    private ZonedDateTime login;
    private ZonedDateTime logout;
    private long sessionDuration;

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

    public void setLogout(ZonedDateTime logout) {
        this.logout = logout;
        this.sessionDuration = logout.toEpochSecond() - login.toEpochSecond();
    }

    public long getSessionDuration() {
        return sessionDuration;
    }

    public AuthSession setSessionDuration(long sessionDuration) {
        this.sessionDuration = sessionDuration;
        return this;
    }
}
