package com.aviobrief.springserver.utils.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

public record LoggerMessages() {

    /* dev */
    public static final String SERVER_RUNNING_OK_TEMPLATE = "Server listening on port %s...";

    /* user controller */
    public static final String USERS_GET_ALL_OK = "USERS_GET_ALL_OK";
    public static final String USERS_GET_ALL_FAIL = "USERS_GET_ALL_FAIL";



}
