package io.github.xinfra.lab.telemetry.service.opentelemetry.provider.meter.reader;

import io.github.xinfra.lab.telemetry.config.MeterProviderConfig;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.config.PrometheusConfig;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.exporter.ExporterFactory;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.MeterProviderType;
import io.opentelemetry.exporter.prometheus.PrometheusHttpServer;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.metrics.export.MetricReader;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;

import java.util.concurrent.TimeUnit;

public class MeterReaderFactory {

	public static MetricReader create(MeterProviderConfig meterProviderConfig) {
		MetricExporter metricExporter = ExporterFactory.createMetricExporter(meterProviderConfig);

		MeterProviderType meterProviderType = meterProviderConfig.getMeterProviderType();
		switch (meterProviderType) {
			case prometheus:
				PrometheusConfig prometheusConfig = meterProviderConfig.getPrometheusConfig();
				return PrometheusHttpServer.builder().setPort(prometheusConfig.getPort()).build();
			default:
				return PeriodicMetricReader.builder(metricExporter)
					.setInterval(meterProviderConfig.getMeterIntervalMills(), TimeUnit.MILLISECONDS)
					.build();
		}

	}

}
