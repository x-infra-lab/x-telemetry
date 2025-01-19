package io.github.xinfra.lab.telemetry.opentelemetry;

import io.github.xinfra.lab.telemetry.config.LoggerProviderConfig;
import io.github.xinfra.lab.telemetry.config.MeterProviderConfig;
import io.github.xinfra.lab.telemetry.config.TracerProviderConfig;
import io.github.xinfra.lab.telemetry.opentelemetry.provider.exporter.ExporterFactory;
import io.github.xinfra.lab.telemetry.opentelemetry.provider.log.processor.LogRecordProcessorFactory;
import io.github.xinfra.lab.telemetry.opentelemetry.provider.meter.reader.MeterReaderFactory;
import io.github.xinfra.lab.telemetry.opentelemetry.provider.trace.processor.SpanProcessorFactory;
import io.github.xinfra.lab.telemetry.service.AgentService;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.LogRecordProcessor;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.MetricReader;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;

import java.util.concurrent.TimeUnit;

import static io.github.xinfra.lab.telemetry.config.ConfigManager.AGENT_CONFIG;

public class OpenTelemetrys implements AgentService {

	private static OpenTelemetry openTelemetry = OpenTelemetry.noop();

	@Override
	public void startup() {
		// set metric
		MeterProviderConfig meterProviderConfig = AGENT_CONFIG.getMeterProviderConfig();
		MetricReader metricReader = MeterReaderFactory.create(meterProviderConfig);
		SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder().registerMetricReader(metricReader).build();
		// set trace
		TracerProviderConfig tracerProviderConfig = AGENT_CONFIG.getTracerProviderConfig();
		SpanProcessor spanProcessor = SpanProcessorFactory.create(tracerProviderConfig, sdkMeterProvider);
		SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder().addSpanProcessor(spanProcessor).build();

		// set log
		LoggerProviderConfig loggerProviderConfig = AGENT_CONFIG.getLoggerProviderConfig();
		LogRecordProcessor logRecordProcessor = LogRecordProcessorFactory.create(loggerProviderConfig,
				sdkMeterProvider);
		SdkLoggerProvider sdkLoggerProvider = SdkLoggerProvider.builder()
			.addLogRecordProcessor(logRecordProcessor)
			.build();

		// set contextPropagators
		ContextPropagators contextPropagators = ContextPropagators
			.create(TextMapPropagator.composite(W3CTraceContextPropagator.getInstance()));

		// set opentelemetry
		openTelemetry = OpenTelemetrySdk.builder()
			.setMeterProvider(sdkMeterProvider)
			.setLoggerProvider(sdkLoggerProvider)
			.setTracerProvider(sdkTracerProvider)
			.setPropagators(contextPropagators)
			.build();
	}

	@Override
	public void shutdown() {
		if (openTelemetry instanceof OpenTelemetrySdk) {
			OpenTelemetrySdk openTelemetrySdk = (OpenTelemetrySdk) openTelemetry;
			openTelemetrySdk.shutdown().join(3, TimeUnit.SECONDS);
		}
	}

	public static OpenTelemetry get() {
		return openTelemetry;
	}

	public static Tracer getTracer(String scopeName) {
		// todo cache it
		return openTelemetry.getTracer(scopeName);
	}

	public static ContextPropagators getPropagators() {
		return openTelemetry.getPropagators();
	}

}
