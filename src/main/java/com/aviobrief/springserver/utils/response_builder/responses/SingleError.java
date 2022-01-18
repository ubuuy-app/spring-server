package com.aviobrief.springserver.utils.response_builder.responses;

import com.google.gson.annotations.Expose;

public class SingleError {

    @Expose
    private String target;
    @Expose
    private String message;
    @Expose
    private Object rejectedValue;
    @Expose
    private String reason;

    public SingleError() {
    }

    public String getTarget() {
        return target;
    }

    public SingleError setTarget(String target) {
        this.target = target;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SingleError setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public SingleError setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public SingleError setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
