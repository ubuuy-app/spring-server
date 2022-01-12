package com.aviobrief.springserver.utils.api_response_builder.response_models;


public class ApiOkBooleanResponse {

    private boolean ok;

    public ApiOkBooleanResponse(boolean ok) {
        this.ok = ok;
    }

    public boolean getOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
