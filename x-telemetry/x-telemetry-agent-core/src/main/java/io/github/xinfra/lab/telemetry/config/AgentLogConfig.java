package io.github.xinfra.lab.telemetry.config;

import io.github.xinfra.lab.telemetry.AgentPath;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgentLogConfig {

	private String level = "info";

	private String filename = "x-telemetry.log";

	private String logDir = AgentPath.getAgentDirPath() + "/logs";

	private String layoutPattern; // default null use default layout

	private String fileNamePattern = "x-telemetry-%d{yyyy-MM-dd-HH}.log";

	private String timeBasedRollInterval = "1"; // 1 hour

	private String rolloverMax = "168"; // 24 hours * 7 = 7 days

}
