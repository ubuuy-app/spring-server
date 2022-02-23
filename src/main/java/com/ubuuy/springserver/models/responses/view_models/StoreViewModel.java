package com.ubuuy.springserver.models.responses.view_models;

public class StoreViewModel {

    private String name;

    public StoreViewModel() {
    }

    public String getName() {
        return name;
    }

    public StoreViewModel setName(String name) {
        this.name = name;
        return this;
    }
}
