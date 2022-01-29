package com.ubuuy.springserver.config.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record ApplicationConstants() {
    /* app */
    public static final String APPLICATION_TIMEZONE_DEFAULT = "Etc/UTC";


    /* auth */
    public static final List<String> CSRF_FILTER_DISABLED_PATHS =
            List.of(
                    "/api/auth/register-organization-owner",
                    "/api/auth/login",
                    "/api/auth/logout",
                    "/.well-known/first-party-set",
                    "/first-party-cookie");

    public static final List<String> JWT_FILTER_DISABLED_PATHS =
            List.of(
                    "/api/auth/register-organization-owner",
                    "/api/auth/login",
                    "/.well-known/first-party-set",
                    "/first-party-cookie");

    public static final Set<String> CSRF_TOKEN_SAFE_METHODS = new HashSet<>(Arrays.asList("HEAD", "TRACE", "OPTIONS"));
    public static final String HTTP_REQ_AUTH_HEADER = "Authorization";
    public static final String HTTP_REQ_AUTH_TOKEN_PREFIX = "Bearer ";

    /* service */
    public static long COMPLETABLE_AWAIT_TIME_SEC = 30;

}
