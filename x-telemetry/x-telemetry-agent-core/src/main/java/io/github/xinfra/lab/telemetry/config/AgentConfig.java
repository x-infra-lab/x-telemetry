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

    private CollectorConfig collector;
    private LoggingConfig logging;
    private PluginConfig plugin;
}
