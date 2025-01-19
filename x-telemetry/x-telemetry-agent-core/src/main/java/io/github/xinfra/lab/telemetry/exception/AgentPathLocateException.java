package io.github.xinfra.lab.telemetry.exception;

public class AgentPathLocateException extends AgentException {

	public AgentPathLocateException() {
	}

	public AgentPathLocateException(String message) {
		super(message);
	}

	public AgentPathLocateException(String message, Throwable cause) {
		super(message, cause);
	}

	public AgentPathLocateException(Throwable cause) {
		super(cause);
	}

	public AgentPathLocateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
