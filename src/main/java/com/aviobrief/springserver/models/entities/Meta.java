package com.aviobrief.springserver.models.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "meta_data")
@Access(AccessType.PROPERTY)
public class Meta extends BaseEntity{

    private String addedAt;
    private String addedBy;

    public String getAddedAt() {
        return addedAt;
    }

    public Meta setAddedAt(String addedAt) {
        this.addedAt = addedAt;
        return this;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public Meta setAddedBy(String addedBy) {
        this.addedBy = addedBy;
        return this;
    }
}
