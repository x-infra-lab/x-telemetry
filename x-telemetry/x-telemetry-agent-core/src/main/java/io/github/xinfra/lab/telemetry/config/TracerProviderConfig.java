package io.github.xinfra.lab.telemetry.config;

import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.TracerProviderType;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.config.ZipkinConfig;
import lombok.Data;

@Data
public class TracerProviderConfig {

	private TracerProviderType tracerProviderType;

	private ZipkinConfig zipkinConfig = new ZipkinConfig();

}
