package com.ubuuy.springserver.config.constants;

public record LoggerMessages() {

    /* dev */
    public static final String SERVER_RUNNING_OK_TEMPLATE = "Server listening on port %s [Timezone:%s]...";

    /* auth */
    public static final String JWT_VERIFICATION_FAIL = "JWT invalid!";
    public static final String SECURITY_CONTEXT_SET_AUTH_TOKEN_FAIL = "Could not set user authentication in security context!";
    public static final String JWT_UNAUTHORIZED_HANDLER_LOG_MESSAGE = "Unauthorised RES (401) error send";


    /* user controller */
    public static final String USERS_GET_ALL_OK = "USERS_GET_ALL_OK";
    public static final String USERS_GET_ALL_FAIL = "USERS_GET_ALL_FAIL";
    public static final String USER_NOT_FOUND_IN_DATABASE_BY_EMAIL = "USER_NOT_FOUND_IN_DATABASE_BY_EMAIL";



}
