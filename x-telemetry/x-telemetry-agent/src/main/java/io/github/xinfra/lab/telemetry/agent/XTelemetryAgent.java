package io.github.xinfra.lab.telemetry.agent;

import io.github.xinfra.lab.telemetry.config.ConfigManager;
import io.github.xinfra.lab.telemetry.log.LogManager;
import io.github.xinfra.lab.telemetry.plugin.PluginManager;
import io.github.xinfra.lab.telemetry.service.ServiceManager;
import org.apache.logging.log4j.Logger;

import java.lang.instrument.Instrumentation;

/**
 * x-telemetry java-agent
 */
public class XTelemetryAgent {

    private static Logger LOGGER =  LogManager.getLogger(XTelemetryAgent.class);

    public static void premain(String agentArgs, Instrumentation inst) {
        try {
            ConfigManager.loadConfig(agentArgs);
        } catch (Exception e) {
            LOGGER.error("XTelemetryAgent load config failed.", e);
            return;
        }

        try {
            ConfigManager.refreshConfig();
        }catch (Exception e){
            LOGGER.error("XTelemetryAgent refresh config failed.", e);
            return;
        }

        if (!ConfigManager.CONFIG.isEnable()) {
            LOGGER.info("XTelemetryAgent is disabled.");
            return;
        }


        try {
            PluginManager.loadPlugins();
        } catch (Exception e) {
            // todo
        }

        try {
            installTransformer(inst);
        } catch (Exception e) {
            // todo
        }
        try {
            ServiceManager.startup();
        } catch (Exception e) {
            // todo
        }

        Runtime.getRuntime().addShutdownHook(new Thread(ServiceManager::shutdown,
                "XTelemetryAgent-ShutdownHook-Thread"));
    }

    private static void installTransformer(Instrumentation inst) {
        // todo
    }
}
