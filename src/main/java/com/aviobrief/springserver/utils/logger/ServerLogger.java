package com.aviobrief.springserver.utils.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerLogger {

    public ServerLogger() {
    }

    public void error(String loggerName, String message){
        Logger logger = LoggerFactory.getLogger(loggerName);
        logger.error(message);

    }
}
