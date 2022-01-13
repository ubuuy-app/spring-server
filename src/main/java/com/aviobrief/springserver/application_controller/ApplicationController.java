package com.aviobrief.springserver.application_controller;

import com.aviobrief.springserver.config.date_time.ApplicationDateTimeConfiguration;
import com.aviobrief.springserver.db.DatabaseInit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.aviobrief.springserver.config.constants.LoggerMessages.SERVER_RUNNING_OK_TEMPLATE;

@Component
public class ApplicationController implements CommandLineRunner {

    private final Logger logger = Logger.getLogger("AppController");
    private final Environment environment;
    private final DatabaseInit databaseInit;

    public ApplicationController(Environment environment,
                                 DatabaseInit databaseInit) {
        this.environment = environment;
        this.databaseInit = databaseInit;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.log(Level.INFO, String.format(SERVER_RUNNING_OK_TEMPLATE, environment.getProperty("local.server.port")));
        databaseInit.initDatabase();
        System.out.println(ApplicationDateTimeConfiguration.getApplicationTimeZone());
        Arrays.stream(TimeZone.getAvailableIDs()).forEach(System.out::println);
    }
}

/*
    LIBRARY:
      local.server.port reading:  https://m.editcode.net/forum.php?mod=viewthread&tid=236028&extra=page%3D1&mobile=1

 */

