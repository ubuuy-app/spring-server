package com.aviobrief.springserver.config.date_time;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationDateTimeConfiguration {

    private static String applicationTimeZoneDefault;

    /* ApplicationDateTimeConfiguration is not abstract and this method is not static, so Spring can initialize the class and inject
       the default time-zone value.
    */
    @Value("${app.time-zone:Etc/UTC}")
    public void setApplicationTimeZoneDefault(String timeZoneDefault){
        ApplicationDateTimeConfiguration.applicationTimeZoneDefault = timeZoneDefault;
    }
    public static String getApplicationTimeZone(){
        return applicationTimeZoneDefault;
    }
}
