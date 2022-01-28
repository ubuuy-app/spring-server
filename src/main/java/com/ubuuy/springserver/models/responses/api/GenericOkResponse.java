package com.ubuuy.springserver.models.responses.api;

public class GenericOkResponse {

    private boolean ok;

    public GenericOkResponse() {
    }

    public boolean isOk() {
        return ok;
    }

    public GenericOkResponse setOk(boolean ok) {
        this.ok = ok;
        return this;
    }
}
