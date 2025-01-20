package io.github.xinfra.lab.telemetry.service.opentelemetry.provider.exporter;

import io.github.xinfra.lab.telemetry.config.LoggerProviderConfig;
import io.github.xinfra.lab.telemetry.config.MeterProviderConfig;
import io.github.xinfra.lab.telemetry.config.TracerProviderConfig;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.MeterProviderType;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.TracerProviderType;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.config.ZipkinConfig;
import io.opentelemetry.exporter.zipkin.ZipkinSpanExporter;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.trace.export.SpanExporter;

public class ExporterFactory {

	public static MetricExporter createMetricExporter(MeterProviderConfig meterProviderConfig) {
		MeterProviderType meterProviderType = meterProviderConfig.getMeterProviderType();
		switch (meterProviderType) {
			// todo
		}
		throw new IllegalArgumentException("unsupported metric exporter:" + meterProviderType);
	}

	public static SpanExporter createSpanExporter(TracerProviderConfig tracerProviderConfig) {
		TracerProviderType tracerProviderType = tracerProviderConfig.getTracerProviderType();
		switch (tracerProviderType) {
			case Zipkin:
				ZipkinConfig zipkinConfig = tracerProviderConfig.getZipkinConfig();
				return ZipkinSpanExporter.builder().setEndpoint(zipkinConfig.getEndpoint()).build();
		}
		throw new IllegalArgumentException("unsupported trace exporter:" + tracerProviderType);
	}

	public static LogRecordExporter createLogRecordExporter(LoggerProviderConfig loggerProviderConfig) {
		// todo
		return null;
	}

}
