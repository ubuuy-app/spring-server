package com.aviobrief.springserver.devTools;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.aviobrief.springserver.utils.log.LoggerMessages.*;

@Component
public class AppController implements CommandLineRunner {

    private Logger logger = Logger.getLogger("AppController");
    private Environment environment;

    public AppController(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.log(Level.INFO, String.format(SERVER_RUNNING_OK_TEMPLATE,environment.getProperty("local.server.port")));
    }
}
