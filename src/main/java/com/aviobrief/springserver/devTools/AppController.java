package com.aviobrief.springserver.devTools;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AppController implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        System.out.printf("[AppController] Server running on port 8080...");
        System.out.printf("%s",new Date());
    }
}
