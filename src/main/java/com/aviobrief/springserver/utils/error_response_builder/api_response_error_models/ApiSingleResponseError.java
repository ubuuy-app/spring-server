package com.aviobrief.springserver.utils.error_response_builder.api_response_error_models;

import com.google.gson.annotations.Expose;

public class ApiSingleResponseError {

    @Expose
    private String message;
    @Expose
    private String target;
    @Expose
    private Object rejectedValue;
    @Expose
    private String reason;

    public ApiSingleResponseError(String message) {
        this.message = message;
    }

    public ApiSingleResponseError(String message, String target) {
        this.message = message;
        this.target = target;
    }


    public ApiSingleResponseError(String message, String target, Object rejectedValue) {
        this.message = message;
        this.target = target;
        this.rejectedValue = rejectedValue;
    }

    public ApiSingleResponseError(String message, String target, Object rejectedValue, String reason) {
        this.message = message;
        this.target = target;
        this.rejectedValue = rejectedValue;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public ApiSingleResponseError setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
