package io.github.xinfra.lab.telemetry.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgentConfig {

	private String serviceName;

	private String group;

	private String version;

	private boolean enable = true;

	private MeterProviderConfig meterProviderConfig;

	private TracerProviderConfig tracerProviderConfig;

	private LoggerProviderConfig loggerProviderConfig;

	private AgentLogConfig agentLogConfig;

	private PluginConfig pluginConfig;

}
