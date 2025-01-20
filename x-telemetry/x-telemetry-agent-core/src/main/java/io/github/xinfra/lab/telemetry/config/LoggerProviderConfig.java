package io.github.xinfra.lab.telemetry.config;

import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.LoggerProviderType;
import lombok.Data;

@Data
public class LoggerProviderConfig {

	private LoggerProviderType loggerProviderType;

}
