package io.github.xinfra.lab.telemetry.config;

import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.LoggerProviderType;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.config.OtlpConfig;
import lombok.Data;

@Data
public class LoggerProviderConfig {

	private LoggerProviderType loggerProviderType;

	private OtlpConfig otlpConfig = new OtlpConfig();

}
