package com.aviobrief.springserver.webControllers.responses;


public class ApiOkTrueOrFalse {
    private final boolean ok;

    public ApiOkTrueOrFalse(boolean responseOk) {
        this.ok = responseOk;
    }

    public boolean getOk() {
        return ok;
    }
}
