package com.ubuuy.springserver.models.entities;

import com.ubuuy.springserver.models.meta_data.MetaData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizations")
@Access(AccessType.PROPERTY)
public class OrganizationEntity extends BaseEntity {

    private String name;
    private List<UserEntity> members = new ArrayList<>();
    private List<PurchaseEntity> purchases = new ArrayList<>();
    private List<ProductEntity> products = new ArrayList<>();
    private MetaData metaData;

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
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE
    )
    public List<UserEntity> getMembers() {
        return members;
    }

    public OrganizationEntity setMembers(List<UserEntity> members) {
        this.members = members;
        return this;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public OrganizationEntity setPurchases(List<PurchaseEntity> purchases) {
        this.purchases = purchases;
        return this;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<ProductEntity> getProducts() {
        return products;
    }

    public OrganizationEntity setProducts(List<ProductEntity> products) {
        this.products = products;
        return this;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public MetaData getMetaData() {
        return metaData;
    }

    public OrganizationEntity setMetaData(MetaData metaData) {
        this.metaData = metaData;
        return this;
    }
}
