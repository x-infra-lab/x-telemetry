package io.github.xinfra.lab.telemetry.service.opentelemetry.provider.config;

import io.opentelemetry.exporter.zipkin.ZipkinSpanExporter;
import lombok.Data;

@Data
public class ZipkinConfig {

	private String endpoint = ZipkinSpanExporter.DEFAULT_ENDPOINT;

}
