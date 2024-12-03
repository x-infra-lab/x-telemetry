package io.github.xinfra.lab.telemetry.service;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

public class OpenTelemetrys implements AgentService {

    @Getter
    private static OpenTelemetry openTelemetry = OpenTelemetry.noop();

    @Override
    public void startup() {
        // todo
        SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder().build();
        // todo
        SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder().build();
        // todo
        SdkLoggerProvider sdkLoggerProvider = SdkLoggerProvider.builder().build();
        openTelemetry = OpenTelemetrySdk.builder()
                .setMeterProvider(sdkMeterProvider)
                .setLoggerProvider(sdkLoggerProvider)
                .setTracerProvider(sdkTracerProvider)
                .build();
    }

    @Override
    public void shutdown() {
        if (openTelemetry instanceof OpenTelemetrySdk) {
            OpenTelemetrySdk openTelemetrySdk = (OpenTelemetrySdk) openTelemetry;
            openTelemetrySdk.shutdown().join(3, TimeUnit.SECONDS);
        }
    }


}
