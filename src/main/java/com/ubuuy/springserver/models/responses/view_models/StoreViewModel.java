package com.ubuuy.springserver.models.responses.view_models;

public class StoreViewModel {

    private Long id;
    private String name;

    public StoreViewModel() {
    }

    public Long getId() {
        return id;
    }

    public StoreViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public StoreViewModel setName(String name) {
        this.name = name;
        return this;
    }
}
