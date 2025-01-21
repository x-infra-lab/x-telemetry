package io.github.xinfra.lab.telemetry.service.opentelemetry.provider.exporter;

import io.github.xinfra.lab.telemetry.config.LoggerProviderConfig;
import io.github.xinfra.lab.telemetry.config.MeterProviderConfig;
import io.github.xinfra.lab.telemetry.config.TracerProviderConfig;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.LoggerProviderType;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.MeterProviderType;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.TracerProviderType;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.config.OtlpConfig;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.config.ZipkinConfig;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.exporter.zipkin.ZipkinSpanExporter;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.trace.export.SpanExporter;

public class ExporterFactory {

	public static MetricExporter createMetricExporter(MeterProviderConfig meterProviderConfig) {
		MeterProviderType meterProviderType = meterProviderConfig.getMeterProviderType();
		switch (meterProviderType) {
			case prometheus:
				return NoopMetricExporter.NOOP;
		}
		throw new IllegalArgumentException("unsupported metric exporter:" + meterProviderType);
	}

	public static SpanExporter createSpanExporter(TracerProviderConfig tracerProviderConfig) {
		TracerProviderType tracerProviderType = tracerProviderConfig.getTracerProviderType();
		switch (tracerProviderType) {
			case zipkin:
				ZipkinConfig zipkinConfig = tracerProviderConfig.getZipkinConfig();
				return ZipkinSpanExporter.builder().setEndpoint(zipkinConfig.getEndpoint()).build();
		}
		throw new IllegalArgumentException("unsupported trace exporter:" + tracerProviderType);
	}

	public static LogRecordExporter createLogRecordExporter(LoggerProviderConfig loggerProviderConfig) {
		LoggerProviderType loggerProviderType = loggerProviderConfig.getLoggerProviderType();
		switch (loggerProviderType) {
			case otlp:
				OtlpConfig otlpConfig = loggerProviderConfig.getOtlpConfig();
				return OtlpGrpcLogRecordExporter.builder().setEndpoint(otlpConfig.getLogEndpoint()).build();
		}
		throw new IllegalArgumentException("unsupported log exporter:" + loggerProviderType);
	}

}
