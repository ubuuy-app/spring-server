package com.aviobrief.springserver.utils.api_response_builder.response_models.error_models;

import com.google.gson.annotations.Expose;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApiErrorObjectResponse {

    @Expose
    private HttpStatus status;
    @Expose
    private String timestamp;
    @Expose
    private String message;
    @Expose
    private List<ApiSingleError> errors = new ArrayList<>();
    @Expose
    private String path;


    public ApiErrorObjectResponse() {
        this.timestamp = ZonedDateTime.now().toString();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ApiErrorObjectResponse setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public ApiErrorObjectResponse setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiErrorObjectResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<ApiSingleError> getErrors() {
        return errors;
    }

    public ApiErrorObjectResponse setErrors(List<ApiSingleError> errors) {
        this.errors = errors;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ApiErrorObjectResponse setPath(String path) {
        this.path = path;
        return this;
    }
}
