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

	private AgentLogConfig agentLogConfig = new AgentLogConfig();

	private PluginConfig pluginConfig = new PluginConfig();

	private MeterProviderConfig meterProviderConfig = new MeterProviderConfig();

	private TracerProviderConfig tracerProviderConfig = new TracerProviderConfig();

	private LoggerProviderConfig loggerProviderConfig = new LoggerProviderConfig();

}
