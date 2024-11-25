package io.github.xinfra.lab.telemetry.agent;

import io.github.xinfra.lab.telemetry.config.ConfigManager;
import io.github.xinfra.lab.telemetry.plugin.PluginManager;

import java.lang.instrument.Instrumentation;

/**
 * x-telemetry java-agent
 */
public class XTelemetryAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        try {
            ConfigManager configManager = new ConfigManager();
            configManager.load(agentArgs);

            PluginManager.loadPlugins();

            installTransformer(inst);


        } catch (Exception e) {
            // todo
            e.printStackTrace();
        }
    }

    private static void installTransformer(Instrumentation inst) {
        // todo
    }
}
