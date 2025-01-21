package io.github.xinfra.lab.telemetry.config;

import io.github.xinfra.lab.telemetry.AgentPath;
import io.github.xinfra.lab.telemetry.common.Beans;
import io.github.xinfra.lab.telemetry.logger.AgentLogManager;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigManager {

	/**
	 * represent the global configuration of agent
	 */
	public static AgentConfig AGENT_CONFIG = new AgentConfig();

	public static final String AGENT_CONFIG_PREFIX = "x.agent.";

	public static final String AGENT_CONFIG_FILE_PATH = "x.agent.config.file";

	public static void loadConfig(String agentArgs)
			throws InvocationTargetException, IllegalAccessException, IOException {
		Map<String, Object> properties = new HashMap<>();

		// load config file
		loadConfigFile(properties);

		// load system env
		loadSystemEnv(properties);

		// load system properties
		loadSystemProperties(properties);

		Beans.populate(AGENT_CONFIG, properties);
	}

	private static void loadSystemEnv(Map<String, Object> properties) {
		Map<String, String> envs = System.getenv();
		envs.forEach((k, v) -> {
			if (k.startsWith(AGENT_CONFIG_PREFIX)) {
				properties.put(k.substring(AGENT_CONFIG_PREFIX.length()), v);
			}
		});
	}

	private static void loadSystemProperties(Map<String, Object> properties) {
		Properties sysProps = System.getProperties();
		sysProps.forEach((ko, v) -> {
			String k = ko.toString();
			if (k.startsWith(AGENT_CONFIG_PREFIX)) {
				properties.put(k.substring(AGENT_CONFIG_PREFIX.length()), v);
			}
		});
	}

	private static void loadConfigFile(Map<String, Object> properties) throws IOException {
		String envConfigFilePath = System.getenv(AGENT_CONFIG_FILE_PATH);
		String propConfigFilePath = System.getProperty(AGENT_CONFIG_FILE_PATH);

		// priority: prop > env
		String configFilePath = StringUtils.isNotBlank(propConfigFilePath) ? propConfigFilePath : envConfigFilePath;

		File configFile;
		if (StringUtils.isNotBlank(configFilePath)) {
			configFile = new File(configFilePath);
		}
		else {
			// default config file
			configFile = AgentPath.getAgentDirPath().resolve("config").resolve("x-telemetry-agent.config").toFile();
		}
		if (!configFile.exists()) {
			return;
		}

		Properties configProps = new Properties();
		try (FileInputStream fis = new FileInputStream(configFile)) {
			configProps.load(fis);
			configProps.forEach((k, v) -> {
				properties.put(k.toString(), v);
			});
		}
	}

	public static void refreshConfig() {
		AgentLogManager.refreshConfig(AGENT_CONFIG.getAgentLogConfig());
	}

}
