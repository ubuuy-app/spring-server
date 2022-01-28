package com.ubuuy.springserver.models.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "meta_data")
@Access(AccessType.PROPERTY)
public class Meta extends BaseEntity{

    private ZonedDateTime addedAt = ZonedDateTime.now();
    private String addedBy;

    public Meta() {
    }

    public Meta(String addedBy) {
        this.addedBy = addedBy;
    }

    public ZonedDateTime getAddedAt() {
        return addedAt;
    }

    public Meta setAddedAt(ZonedDateTime addedAt) {
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
