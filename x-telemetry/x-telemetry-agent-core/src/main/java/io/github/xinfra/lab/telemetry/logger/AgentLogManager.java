package io.github.xinfra.lab.telemetry.logger;

import io.github.xinfra.lab.telemetry.config.AgentLogConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.filter.LevelMatchFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.File;

public class AgentLogManager {

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

	public static void refreshConfig(AgentLogConfig agentLogConfig) {
		Layout<?> layout;
		if (StringUtils.isBlank(agentLogConfig.getLayoutPattern())) {
			layout = PatternLayout.createDefaultLayout();
		}
		else {
			layout = PatternLayout.newBuilder().withPattern(agentLogConfig.getLayoutPattern()).build();
		}

		TimeBasedTriggeringPolicy timeBasedTriggeringPolicy = TimeBasedTriggeringPolicy
			.createPolicy(agentLogConfig.getTimeBasedRollInterval(), Boolean.FALSE.toString());

		DefaultRolloverStrategy rolloverStrategy = DefaultRolloverStrategy.newBuilder()
			.withMax(agentLogConfig.getRolloverMax())
			.build();

		LevelMatchFilter filter = LevelMatchFilter.newBuilder()
			.setLevel(Level.getLevel(agentLogConfig.getLevel()))
			.build();
		// build RollingFileAppender
		RollingFileAppender appender = RollingFileAppender.newBuilder()
			.setName("RollingFileAppender")
			.setFilter(filter)
			.setLayout(layout)
			.withFileName(agentLogConfig.getLogDir() + File.separator + agentLogConfig.getFilename())
			.withFilePattern(agentLogConfig.getLogDir() + File.separator + agentLogConfig.getFileNamePattern())
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

	public static AgentLogger getLogger(Class<?> clazz) {
		return new AgentLogger(LogManager.getLogger(clazz));
	}

	public static AgentLogger getLogger(String name) {
		return new AgentLogger(LogManager.getLogger(name));
	}

}
