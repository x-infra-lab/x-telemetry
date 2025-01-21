package io.github.xinfra.lab.telemetry.service.opentelemetry.provider.config;

import lombok.Data;

@Data
public class OtlpConfig {

	private String traceEndpoint;

	private String metricEndpoint;

	private String logEndpoint;

}
