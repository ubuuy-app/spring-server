package com.ubuuy.springserver.config.date_time;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.ubuuy.springserver.config.constants.ApplicationConstants.APPLICATION_TIMEZONE_DEFAULT;

public abstract class ApplicationDateTimeConfiguration {

    private static final Logger logger = Logger.getLogger("ApplicationDateTimeConfiguration");

    public static String getApplicationTimeZone() {
        return TimeZone.getDefault().getDisplayName();
    }

    public static void setApplicationTimeZoneDefault() {
        String timeZoneDefault = APPLICATION_TIMEZONE_DEFAULT;

        /* (1) default timezone is provided */
        if (StringUtils.hasText(timeZoneDefault)) {

            /* (2) check it is valid ? set it as default, otherwise set "Etc/UTC" */
            boolean hasMatchingTimeZone = Arrays.asList(TimeZone.getAvailableIDs()).contains(timeZoneDefault);

            if (hasMatchingTimeZone) {
                TimeZone.setDefault(TimeZone.getTimeZone(timeZoneDefault));
            } else {
                TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
                logger.log(Level.WARNING, "Default timezone invalid. Setting 'Etc/UTC' instead!");
            }

        } else {
            TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
            logger.log(Level.WARNING, "Default timezone not provided. Setting 'Etc/UTC' instead!");
        }
    }
}





















