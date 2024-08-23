package io.github.xinfra.lab.telemetry.agent;

import io.github.xinfra.lab.telemetry.config.ConfigManager;

/**
 * x-telemetry java-agent
 */
public class XTelemetryAgent {
    public static void premain(String agentArgs, Instrumentation inst) {

        ConfigManager configManager = new ConfigManager();
        // configManager.load();
    }
}
