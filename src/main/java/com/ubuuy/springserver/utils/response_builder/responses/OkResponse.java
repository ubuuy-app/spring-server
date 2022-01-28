package com.ubuuy.springserver.utils.response_builder.responses;


public class OkResponse {

    private boolean ok;

    public OkResponse() {
    }

    public boolean isOk() {
        return ok;
    }

    public OkResponse setOk(boolean ok) {
        this.ok = ok;
        return this;
    }
}
