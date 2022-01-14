package com.aviobrief.springserver.utils.api_response_builder.response_models.error_models;

import com.google.gson.annotations.Expose;

public class ApiSingleError {

    @Expose
    private String message;
    @Expose
    private String target;
    @Expose
    private Object rejectedValue;
    @Expose
    private String reason;

    public ApiSingleError(String message) {
        this.message = message;
    }

    public ApiSingleError(String message, String target) {
        this.message = message;
        this.target = target;
    }


    public ApiSingleError(String message, String target, Object rejectedValue) {
        this.message = message;
        this.target = target;
        this.rejectedValue = rejectedValue;
    }

    public ApiSingleError(String message, String target, Object rejectedValue, String reason) {
        this.message = message;
        this.target = target;
        this.rejectedValue = rejectedValue;
        this.reason = reason;
    }

      public ApiSingleError setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
