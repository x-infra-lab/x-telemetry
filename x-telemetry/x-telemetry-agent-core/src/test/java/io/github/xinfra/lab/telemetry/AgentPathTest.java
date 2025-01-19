package io.github.xinfra.lab.telemetry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AgentPathTest {

	@Test
	public void testGetAgentDirPath() {
		Assertions.assertNotNull(AgentPath.getAgentDirPath());
	}

}
