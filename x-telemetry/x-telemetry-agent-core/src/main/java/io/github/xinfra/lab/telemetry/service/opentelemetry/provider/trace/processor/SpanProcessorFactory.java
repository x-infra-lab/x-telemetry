package io.github.xinfra.lab.telemetry.service.opentelemetry.provider.trace.processor;

import io.github.xinfra.lab.telemetry.config.TracerProviderConfig;
import io.github.xinfra.lab.telemetry.service.opentelemetry.provider.exporter.ExporterFactory;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;

public class SpanProcessorFactory {

	public static SpanProcessor create(TracerProviderConfig tracerProviderConfig) {
		SpanExporter spanExporter = ExporterFactory.createSpanExporter(tracerProviderConfig);
		return BatchSpanProcessor.builder(spanExporter).build();
	}

}
