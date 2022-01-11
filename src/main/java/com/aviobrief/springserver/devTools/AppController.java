package com.aviobrief.springserver.devTools;

import com.aviobrief.springserver.db.DatabaseInit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.aviobrief.springserver.config.messages.LoggerMessages.SERVER_RUNNING_OK_TEMPLATE;

@Component
public class AppController implements CommandLineRunner {

    private final Logger logger = Logger.getLogger("AppController");
    private final Environment environment;
    private final DatabaseInit databaseInit;

    public AppController(Environment environment, DatabaseInit databaseInit) {
        this.environment = environment;
        this.databaseInit = databaseInit;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.log(Level.INFO, String.format(SERVER_RUNNING_OK_TEMPLATE,environment.getProperty("local.server.port")));
        databaseInit.initDatabase();
    }
}

/*
    LIBRARY:
      local.server.port reading:  https://m.editcode.net/forum.php?mod=viewthread&tid=236028&extra=page%3D1&mobile=1

 */

