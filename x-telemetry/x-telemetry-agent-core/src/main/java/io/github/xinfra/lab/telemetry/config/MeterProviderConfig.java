package io.github.xinfra.lab.telemetry.config;

import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.MeterProviderType;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.config.PrometheusConfig;
import lombok.Data;

@Data
public class MeterProviderConfig {

	private MeterProviderType meterProviderType;

	private int meterIntervalMills = 10_000; // 10s

	private PrometheusConfig prometheusConfig = new PrometheusConfig();

}
