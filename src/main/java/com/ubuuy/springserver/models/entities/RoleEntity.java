package com.ubuuy.springserver.models.entities;

import com.ubuuy.springserver.models.enums.UserRole;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Access(AccessType.PROPERTY)
public class RoleEntity extends BaseEntity {

    public RoleEntity() {
    }

    public RoleEntity(UserRole role) {
        this.role = role;
    }

    private UserRole role;

    @Enumerated(EnumType.STRING)
    public UserRole getRole() {
        return role;
    }

    public RoleEntity setRole(UserRole role) {
        this.role = role;
        return this;
    }
}
