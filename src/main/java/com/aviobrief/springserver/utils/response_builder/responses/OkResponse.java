package com.aviobrief.springserver.utils.response_builder.responses;


public class OkResponse {

    private boolean ok;

    public OkResponse() {
    }

    public OkResponse setOk(boolean ok) {
        this.ok = ok;
        return this;
    }
}
