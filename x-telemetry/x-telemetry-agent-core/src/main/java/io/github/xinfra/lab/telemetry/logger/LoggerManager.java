package io.github.xinfra.lab.telemetry.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class LoggerManager {

    static {
        // todo default config it
        RollingFileAppender rootAppender = RollingFileAppender.newBuilder()
                .setName("rootRollingFileAppender").build();

        LoggerContext loggerContext = LoggerContext.getContext();
        Configuration configuration = loggerContext.getConfiguration();
        LoggerConfig loggerConfig = configuration.getRootLogger();
        loggerConfig.addAppender(rootAppender, Level.INFO, null);
    }

    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

    public static Logger getLogger(String name) {
        return LogManager.getLogger(name);
    }

}
