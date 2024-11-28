package io.github.xinfra.lab.telemetry.config;


import io.github.xinfra.lab.telemetry.log.LogManager;
import org.apache.commons.beanutils.BeanUtilsBean2;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    public static final AgentConfig CONFIG = new AgentConfig();

    public static void loadConfig(String agentArgs) throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> properties = new HashMap<>();

        // load config file
        loadConfigFile(properties);

        // load system env
        loadSystemEnv(properties);

        // load system properties
        loadSystemProperties(properties);


        BeanUtilsBean2.getInstance().populate(CONFIG, properties);
    }

    private static void loadSystemEnv(Map<String, Object> properties) {
    }


    private static void loadSystemProperties(Map<String, Object> properties) {
        // todo
    }

    private static void loadConfigFile(Map<String, Object> properties) {
        // todo
    }

    public static void refreshConfig() {
        LogManager.refreshConfig(CONFIG.getLog());
    }
}
