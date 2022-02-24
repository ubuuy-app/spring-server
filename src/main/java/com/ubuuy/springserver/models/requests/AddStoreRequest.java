package com.ubuuy.springserver.models.requests;

public class AddStoreRequest {

    private String name;

    public AddStoreRequest() {
    }

    public String getName() {
        return name;
    }

    public AddStoreRequest setName(String name) {
        this.name = name;
        return this;
    }
}
