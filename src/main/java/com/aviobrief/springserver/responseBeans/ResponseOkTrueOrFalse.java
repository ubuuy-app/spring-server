package com.aviobrief.springserver.responseBeans;


public class ResponseOkTrueOrFalse {
    private final boolean ok;

    public ResponseOkTrueOrFalse(boolean responseOk) {
        this.ok = responseOk;
    }

    public boolean getOk() {
        return ok;
    }
}
