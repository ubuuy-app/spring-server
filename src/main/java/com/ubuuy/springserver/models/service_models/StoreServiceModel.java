package com.ubuuy.springserver.models.service_models;

import com.ubuuy.springserver.models.entities.BaseEntity;


public class StoreServiceModel extends BaseEntity {

    private String name;

    public StoreServiceModel() {
    }

    public String getName() {
        return name;
    }

    public StoreServiceModel setName(String name) {
        this.name = name;
        return this;
    }
}
