package io.github.xinfra.lab.telemetry.exception;

public class AgentClassLoadException extends AgentException {

	public AgentClassLoadException() {
	}

	public AgentClassLoadException(String message) {
		super(message);
	}

	public AgentClassLoadException(String message, Throwable cause) {
		super(message, cause);
	}

	public AgentClassLoadException(Throwable cause) {
		super(cause);
	}

	public AgentClassLoadException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
