package io.github.xinfra.lab.telemetry.service.opentelemetry.provider.log.processor;

import io.github.xinfra.lab.telemetry.config.LoggerProviderConfig;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.exporter.ExporterFactory;
import io.opentelemetry.sdk.logs.LogRecordProcessor;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;

public class LogRecordProcessorFactory {

	public static LogRecordProcessor create(LoggerProviderConfig loggerProviderConfig) {
		LogRecordExporter logRecordExporter = ExporterFactory.createLogRecordExporter(loggerProviderConfig);
		return BatchLogRecordProcessor.builder(logRecordExporter).build();
	}

}
