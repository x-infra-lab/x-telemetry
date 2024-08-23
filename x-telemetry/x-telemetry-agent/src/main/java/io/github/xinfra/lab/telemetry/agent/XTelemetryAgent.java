package io.github.xinfra.lab.telemetry.agent;

import io.github.xinfra.lab.telemetry.config.ConfigManager;

import java.lang.instrument.Instrumentation;

/**
 * x-telemetry java-agent
 */
public class XTelemetryAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        try {
            ConfigManager configManager = new ConfigManager();
            configManager.load(agentArgs);
        } catch (Exception e) {
            // todo
            e.printStackTrace();
        }
    }
}
