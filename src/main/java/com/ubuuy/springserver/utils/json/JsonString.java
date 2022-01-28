package com.ubuuy.springserver.utils.json;

import com.fasterxml.jackson.annotation.JsonValue;

public enum JsonString {
    JSON_STRING;

    private String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    public JsonString setValue(String value) {
        this.value = value;
        return JSON_STRING;
    }

}
