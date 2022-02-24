package com.ubuuy.springserver.models.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "stores")
@Access(AccessType.PROPERTY)
public class StoreEntity extends BaseEntity {

    private String name;

    public StoreEntity() {
    }

    public StoreEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public StoreEntity setName(String name) {
        this.name = name;
        return this;
    }
}
