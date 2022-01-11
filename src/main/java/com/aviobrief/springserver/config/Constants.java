package com.aviobrief.springserver.config;

import org.springframework.beans.factory.annotation.Value;

public record Constants() {

 @Value("${app.completable-await-time-seconds}")
 public static long COMPLETABLE_AWAIT_TIME_SEC;

}
