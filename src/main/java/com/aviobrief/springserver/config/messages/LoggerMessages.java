package com.aviobrief.springserver.config.messages;

public record LoggerMessages() {

    /* dev */
    public static final String SERVER_RUNNING_OK_TEMPLATE = "Server listening on port %s...";

    /* user controller */
    public static final String USERS_GET_ALL_OK = "USERS_GET_ALL_OK";
    public static final String USERS_GET_ALL_FAIL = "USERS_GET_ALL_FAIL";
    public static final String USER_NOT_FOUND_IN_DATABASE_BY_EMAIL = "USER_NOT_FOUND_IN_DATABASE_BY_EMAIL";



}
