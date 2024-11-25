package io.github.xinfra.lab.telemetry.config;


import org.xinfra.lab.telemetry.shade.org.apache.commons.beanutils.BeanUtilsBean2;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    public static final AgentConfig CONFIG = new AgentConfig();

    public void load(String agentArgs) throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> properties = new HashMap<>();

        // load config file
        loadConfigFile(properties);

        // load system properties
        loadSystemProperties(properties);

        // load agent Args
        loadAgentArgs(properties);

        BeanUtilsBean2.getInstance().populate(CONFIG, properties);
    }

    private void loadAgentArgs(Map<String, Object> properties) {
        // todo
    }

    private void loadSystemProperties(Map<String, Object> properties) {
        // todo
    }

    private void loadConfigFile(Map<String, Object> properties) {
        // todo
    }
}
