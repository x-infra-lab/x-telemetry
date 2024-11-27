package io.github.xinfra.lab.telemetry.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class LogManager {

    static {
        // todo default config it
        ConsoleAppender consoleAppender = ConsoleAppender.newBuilder()
                .setName("ConsoleAppender")
                .build();

        LoggerContext loggerContext = LoggerContext.getContext();
        Configuration configuration = loggerContext.getConfiguration();
        LoggerConfig loggerConfig = configuration.getRootLogger();
        loggerConfig.addAppender(consoleAppender, Level.INFO, null);
    }

    public static Logger getLogger(Class<?> clazz) {
        return org.apache.logging.log4j.LogManager.getLogger(clazz);
    }

    public static Logger getLogger(String name) {
        return org.apache.logging.log4j.LogManager.getLogger(name);
    }

}
