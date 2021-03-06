package com.ubuuy.springserver.application_controller;

import com.ubuuy.springserver.config.date_time.ApplicationDateTimeConfiguration;
import com.ubuuy.springserver.db.DatabaseInit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.ubuuy.springserver.config.constants.LoggerMessages.SERVER_RUNNING_OK_TEMPLATE;

@Component
public class ApplicationController implements CommandLineRunner {

    private final Logger logger = Logger.getLogger("ApplicationController");
    private final Environment environment;
    private final DatabaseInit databaseInit;

    public ApplicationController(Environment environment,
                                 DatabaseInit databaseInit) {
        this.environment = environment;
        this.databaseInit = databaseInit;
    }

    @Override
    public void run(String... args) throws Exception {
        ApplicationDateTimeConfiguration.setApplicationTimeZoneDefault();
        logger.log(Level.INFO,
                String.format(
                        SERVER_RUNNING_OK_TEMPLATE,
                        environment.getProperty("local.server.port"),
                        ApplicationDateTimeConfiguration.getApplicationTimeZone())
        );

        databaseInit.initDatabase();
    }
}

/*
    LIBRARY:
      local.server.port reading:  https://m.editcode.net/forum.php?mod=viewthread&tid=236028&extra=page%3D1&mobile=1
 */

