package com.aviobrief.springserver.utils.error_response_builder.api_response_error_models;

import com.google.gson.annotations.Expose;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ApiResponseErrorObject {

    @Expose
    private HttpStatus status;
    @Expose
    private String timestamp;
    @Expose
    private String message;
    @Expose
    private List<ApiSingleResponseError> errors;
    @Expose
    private String path;

    public ApiResponseErrorObject() {
    }

    public ApiResponseErrorObject(HttpStatus status, String message, List<ApiSingleResponseError> errors) {
        this.status = status;
        this.timestamp = LocalDateTime.now().toString();
        this.message = message;
        this.errors = errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ApiResponseErrorObject setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public ApiResponseErrorObject setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiResponseErrorObject setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<ApiSingleResponseError> getErrors() {
        return errors;
    }

    public ApiResponseErrorObject setErrors(List<ApiSingleResponseError> errors) {
        this.errors = errors;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ApiResponseErrorObject setPath(String path) {
        this.path = path;
        return this;
    }
}
