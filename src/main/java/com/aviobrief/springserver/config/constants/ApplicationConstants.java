package com.aviobrief.springserver.config.constants;

public record ApplicationConstants() {

 /* auth */
 public static final String HTTP_REQ_AUTH_HEADER = "Authorization";
 public static final String HTTP_REQ_AUTH_TOKEN_PREFIX = "Bearer ";

 public static long COMPLETABLE_AWAIT_TIME_SEC = 30;

}
