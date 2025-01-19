package io.github.xinfra.lab.telemetry.logger;

import org.apache.logging.log4j.Logger;

public class AgentLogger {

	private final Logger logger;

	public AgentLogger(Logger logger) {
		this.logger = logger;
	}

	public void info(String message) {
		logger.info(message);
	}

	public void info(String message, Object... params) {
		logger.info(message, params);
	}

	public void error(String message, Throwable throwable) {
		logger.error(message, throwable);
	}

	public void error(String message, Object... params) {
		logger.error(message, params);
	}

	public void warn(String message, Object... params) {
		logger.warn(message, params);
	}

}
