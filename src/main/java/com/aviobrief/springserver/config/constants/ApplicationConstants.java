package com.aviobrief.springserver.config.constants;

import org.springframework.beans.factory.annotation.Value;

public record ApplicationConstants() {

 /* auth */
 public static final String HTTP_REQ_AUTH_HEADER = "Authorization";
 public static final String HTTP_REQ_AUTH_TOKEN_PREFIX = "Bearer ";

 @Value("${app.completable-await-time-seconds}")
 public static long COMPLETABLE_AWAIT_TIME_SEC;

}
