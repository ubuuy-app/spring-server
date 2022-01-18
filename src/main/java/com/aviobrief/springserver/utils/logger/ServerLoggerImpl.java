package com.aviobrief.springserver.utils.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServerLoggerImpl implements ServerLogger{

    public ServerLoggerImpl() {
    }

    public void error(String loggerName, String message){
        Logger logger = LoggerFactory.getLogger(loggerName);
        logger.error(message);

    }
}
