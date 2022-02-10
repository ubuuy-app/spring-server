package com.ubuuy.springserver.models.service_models;

import com.ubuuy.springserver.models.entities.MetaEntity;
import com.ubuuy.springserver.models.entities.ProductEntity;
import com.ubuuy.springserver.models.entities.PurchaseEntity;

import java.util.ArrayList;
import java.util.List;

public class OrganizationServiceModel {

    private String name;
    private List<UserServiceModel> members = new ArrayList<>();
    private List<PurchaseEntity> purchases = new ArrayList<>();
    private List<ProductEntity> products = new ArrayList<>();
    private MetaEntity metaEntity;

    public OrganizationServiceModel() {
    }

    public String getName() {
        return name;
    }

    public OrganizationServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<UserServiceModel> getMembers() {
        return members;
    }

    public OrganizationServiceModel setMembers(List<UserServiceModel> members) {
        this.members = members;
        return this;
    }

    public List<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public OrganizationServiceModel setPurchases(List<PurchaseEntity> purchases) {
        this.purchases = purchases;
        return this;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public OrganizationServiceModel setProducts(List<ProductEntity> products) {
        this.products = products;
        return this;
    }

    public MetaEntity getMetaEntity() {
        return metaEntity;
    }

    public OrganizationServiceModel setMetaEntity(MetaEntity metaEntity) {
        this.metaEntity = metaEntity;
        return this;
    }
}
