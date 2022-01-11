package com.aviobrief.springserver.config.constants;

import org.springframework.beans.factory.annotation.Value;

public record ApplicationConstants() {

 @Value("${app.completable-await-time-seconds}")
 public static long COMPLETABLE_AWAIT_TIME_SEC;

}
