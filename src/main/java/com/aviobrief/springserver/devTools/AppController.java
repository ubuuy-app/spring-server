package com.aviobrief.springserver.devTools;

import com.aviobrief.springserver.utils.log.LoggerMessages;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.aviobrief.springserver.utils.log.LoggerMessages.*;

@Component
public class AppController implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {

        System.out.printf("%s",new Date());
        System.out.printf(SERVER_RUNNING_OK);

    }
}
