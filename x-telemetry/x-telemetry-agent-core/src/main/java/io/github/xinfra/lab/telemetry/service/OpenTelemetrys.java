package io.github.xinfra.lab.telemetry.service;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.trace.SdkTracerProvider;

import java.util.concurrent.TimeUnit;

public class OpenTelemetrys implements AgentService {


    private static OpenTelemetry openTelemetry = OpenTelemetry.noop();


    @Override
    public void startup() {
        // todo
        SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder().build();
        // todo
        SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder().build();
        // todo
        SdkLoggerProvider sdkLoggerProvider = SdkLoggerProvider.builder().build();

        ContextPropagators contextPropagators = ContextPropagators
                .create(TextMapPropagator
                        .composite(W3CTraceContextPropagator
                                .getInstance()));

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
