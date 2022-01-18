package com.aviobrief.springserver.utils.response_builder.responses;

import com.google.gson.annotations.Expose;

public class SingleError {

    @Expose
    private String message;
    @Expose
    private String target;
    @Expose
    private Object rejectedValue;
    @Expose
    private String reason;

    public SingleError() {
    }

    public SingleError setMessage(String message) {
        this.message = message;
        return this;
    }

    public SingleError setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
