package com.ubuuy.springserver.models.responses.api_responses;

public class GenericIdResponse {

    private Long id;

    public GenericIdResponse() {
    }

    public Long getId() {
        return id;
    }

    public GenericIdResponse setId(Long id) {
        this.id = id;
        return this;
    }
}
