package io.github.xinfra.lab.telemetry.service.opentelemetry.provider.exporter;

import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.metrics.InstrumentType;
import io.opentelemetry.sdk.metrics.data.AggregationTemporality;
import io.opentelemetry.sdk.metrics.data.MetricData;
import io.opentelemetry.sdk.metrics.export.MetricExporter;

import java.util.Collection;

public class NoopMetricExporter implements MetricExporter {

	public static final MetricExporter NOOP = new NoopMetricExporter();

	@Override
	public CompletableResultCode export(Collection<MetricData> metrics) {
		return null;
	}

	@Override
	public CompletableResultCode flush() {
		return null;
	}

	@Override
	public CompletableResultCode shutdown() {
		return null;
	}

	@Override
	public AggregationTemporality getAggregationTemporality(InstrumentType instrumentType) {
		return null;
	}

}