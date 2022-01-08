package com.aviobrief.springserver.devTools;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.aviobrief.springserver.utils.log.LoggerMessages.*;

@Component
public class AppController implements CommandLineRunner {

    private Logger logger = Logger.getLogger("Test");

    @Override
    public void run(String... args) throws Exception {

        System.out.printf("%s", new Date());
        System.out.printf(SERVER_RUNNING_OK);
        logger.log(Level.INFO, "test");

    }
}
