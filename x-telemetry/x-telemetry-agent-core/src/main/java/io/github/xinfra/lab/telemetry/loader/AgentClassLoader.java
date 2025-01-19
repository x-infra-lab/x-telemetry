package io.github.xinfra.lab.telemetry.loader;

public class AgentClassLoader extends ClassLoader {

	static {
		registerAsParallelCapable();
	}

}
