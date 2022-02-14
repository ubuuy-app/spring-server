package com.ubuuy.springserver.models.service_models;

import com.ubuuy.springserver.models.meta_data.MetaData;

import java.util.ArrayList;
import java.util.List;

public class OrganizationServiceModel {

    private String name;
    private List<UserServiceModel> members = new ArrayList<>();
    private List<PurchaseServiceModel> purchases = new ArrayList<>();
    private List<ProductServiceModel> products = new ArrayList<>();
    private MetaData metaData;

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

    public List<PurchaseServiceModel> getPurchases() {
        return purchases;
    }

    public OrganizationServiceModel setPurchases(List<PurchaseServiceModel> purchases) {
        this.purchases = purchases;
        return this;
    }

    public List<ProductServiceModel> getProducts() {
        return products;
    }

    public OrganizationServiceModel setProducts(List<ProductServiceModel> products) {
        this.products = products;
        return this;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public OrganizationServiceModel setMetaData(MetaData metaData) {
        this.metaData = metaData;
        return this;
    }
}
