package io.github.xinfra.lab.telemetry.config;

import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.TracerProviderType;
import lombok.Data;

@Data
public class TracerProviderConfig {

	private TracerProviderType tracerProviderType;

}
