package io.github.xinfra.lab.telemetry.log;

import io.github.xinfra.lab.telemetry.config.LogConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.filter.LevelMatchFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class LogManager {
    public static Appender defaultAppender = null;

    static {
        // default console appender
        defaultAppender = ConsoleAppender.newBuilder()
                .setName("ConsoleAppender")
                .setFilter(LevelMatchFilter.newBuilder().setLevel(Level.INFO).build())
                .build();
        defaultAppender.start();
        LoggerContext loggerContext = LoggerContext.getContext();
        loggerContext.getRootLogger().addAppender(defaultAppender);
        loggerContext.updateLoggers();
    }

    public static void refreshConfig(LogConfig logConfig) {
        Layout layout;
        if (StringUtils.isBlank(logConfig.getLayoutPattern())) {
            layout = PatternLayout.createDefaultLayout();
        } else {
            layout = PatternLayout.newBuilder().withPattern(logConfig.getLayoutPattern()).build();
        }

        TimeBasedTriggeringPolicy timeBasedTriggeringPolicy = TimeBasedTriggeringPolicy
                .createPolicy(logConfig.getTimeBasedRollInterval(), Boolean.FALSE.toString());

        DefaultRolloverStrategy rolloverStrategy = DefaultRolloverStrategy.newBuilder()
                .withMax(logConfig.getRolloverMax())
                .build();

        LevelMatchFilter filter = LevelMatchFilter
                .newBuilder()
                .setLevel(Level.getLevel(logConfig.getLevel()))
                .build();
        // build RollingFileAppender
        RollingFileAppender appender = RollingFileAppender.newBuilder()
                .setName("RollingFileAppender")
                .setFilter(filter)
                .withFileName(logConfig.getFilename())
                .withFilePattern(logConfig.getFileNamePattern())
                .withLayout(layout)
                .withPolicy(timeBasedTriggeringPolicy)
                .withStrategy(rolloverStrategy)
                .build();
        appender.start();

        LoggerContext loggerContext = LoggerContext.getContext();
        loggerContext.getRootLogger().addAppender(appender);
        if (defaultAppender != null) {
            loggerContext.getRootLogger().removeAppender(defaultAppender);
        }
        loggerContext.updateLoggers();
    }

    public static Logger getLogger(Class<?> clazz) {
        return org.apache.logging.log4j.LogManager.getLogger(clazz);
    }

    public static Logger getLogger(String name) {
        return org.apache.logging.log4j.LogManager.getLogger(name);
    }

}
