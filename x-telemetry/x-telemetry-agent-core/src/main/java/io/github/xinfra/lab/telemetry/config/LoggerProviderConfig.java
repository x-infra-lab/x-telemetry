package io.github.xinfra.lab.telemetry.config;

import io.github.xinfra.lab.telemetry.opentelemetry.provider.LoggerProviderType;
import lombok.Data;

@Data
public class LoggerProviderConfig {

	private LoggerProviderType tracerProviderType;

}
