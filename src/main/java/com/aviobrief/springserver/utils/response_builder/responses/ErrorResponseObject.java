package com.aviobrief.springserver.utils.response_builder.responses;

import com.google.gson.annotations.Expose;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.aviobrief.springserver.utils.response_builder.ResponseBuilder.Type;

@Component
public class ErrorResponseObject {

    @Expose
    private Type type;
    @Expose
    private HttpStatus status;
    @Expose
    private String timestamp;
    @Expose
    private String message;
    @Expose
    private List<SingleError> errors = new ArrayList<>();
    @Expose
    private String path;


    public ErrorResponseObject() {
        this.timestamp = ZonedDateTime.now().toString();
    }

    public Type getType() {
        return type;
    }

    public ErrorResponseObject setType(Type type) {
        this.type = type;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorResponseObject setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public ErrorResponseObject setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponseObject setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<SingleError> getErrors() {
        return errors;
    }

    public ErrorResponseObject setErrors(List<SingleError> errors) {
        this.errors = errors;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ErrorResponseObject setPath(String path) {
        this.path = path;
        return this;
    }
}
