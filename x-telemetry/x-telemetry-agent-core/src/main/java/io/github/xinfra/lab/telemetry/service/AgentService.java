package io.github.xinfra.lab.telemetry.service;

public interface AgentService extends Comparable<AgentService> {

	void startup();

	void shutdown();

	default int priority() {
		return Integer.MAX_VALUE;
	}

	default int compareTo(AgentService o) {
		return Integer.compare(priority(), o.priority());
	}

}
