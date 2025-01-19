package io.github.xinfra.lab.telemetry.config;

import io.github.xinfra.lab.telemetry.opentelemetry.provider.TracerProviderType;
import lombok.Data;

@Data
public class TracerProviderConfig {

	private TracerProviderType tracerProviderType;

}
