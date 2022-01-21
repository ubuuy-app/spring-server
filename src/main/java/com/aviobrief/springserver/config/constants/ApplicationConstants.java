package com.aviobrief.springserver.config.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record ApplicationConstants() {
    /* app */
    public static final String APPLICATION_TIMEZONE_DEFAULT = "Etc/UTC";


    /* auth */
    public static final List<String> CSRF_DISABLED_PATH = List.of("/api/auth", "/.well-known/first-party-set", "/first-party-cookie");
    public static final Set<String> CSRF_TOKEN_SAFE_METHODS = new HashSet<>(Arrays.asList("HEAD", "TRACE", "OPTIONS"));
    public static final String HTTP_REQ_AUTH_HEADER = "Authorization";
    public static final String HTTP_REQ_AUTH_TOKEN_PREFIX = "Bearer ";

    public static long COMPLETABLE_AWAIT_TIME_SEC = 30;

}
