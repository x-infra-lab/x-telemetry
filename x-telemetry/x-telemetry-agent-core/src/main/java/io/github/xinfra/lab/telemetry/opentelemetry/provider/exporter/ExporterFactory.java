package io.github.xinfra.lab.telemetry.opentelemetry.provider.exporter;

import io.github.xinfra.lab.telemetry.config.LoggerProviderConfig;
import io.github.xinfra.lab.telemetry.config.MeterProviderConfig;
import io.github.xinfra.lab.telemetry.config.TracerProviderConfig;
import io.github.xinfra.lab.telemetry.opentelemetry.provider.MeterProviderType;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.trace.export.SpanExporter;

public class ExporterFactory {

	public static MetricExporter createMetricExporter(MeterProviderConfig meterProviderConfig) {
		MeterProviderType meterProviderType = meterProviderConfig.getMeterProviderType();
		switch (meterProviderType) {
		}
		throw new IllegalArgumentException("unsupported metric exporter:" + meterProviderType);
	}

	public static SpanExporter createSpanExporter(TracerProviderConfig tracerProviderConfig) {
		// todo
		return null;
	}

	public static LogRecordExporter createLogRecordExporter(LoggerProviderConfig loggerProviderConfig) {
		// todo
		return null;
	}

}
