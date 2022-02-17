package com.ubuuy.springserver.models.service_models;

public abstract class BaseServiceModel {

    private Long id;

    public BaseServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public BaseServiceModel setId(Long id) {
        this.id = id;
        return this;
    }
}
