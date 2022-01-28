package com.ubuuy.springserver.models.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizations")
@Access(AccessType.PROPERTY)
public class OrganizationEntity extends BaseEntity {

    private String name;
    private List<UserEntity> members = new ArrayList<>();

    public OrganizationEntity() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public OrganizationEntity setName(String name) {
        this.name = name;
        return this;
    }

    @OneToMany(
            mappedBy = "organization",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public List<UserEntity> getMembers() {
        return members;
    }

    public OrganizationEntity setMembers(List<UserEntity> members) {
        this.members = members;
        return this;
    }
}
