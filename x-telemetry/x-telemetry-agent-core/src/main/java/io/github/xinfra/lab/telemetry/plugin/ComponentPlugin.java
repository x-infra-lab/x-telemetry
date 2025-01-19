package io.github.xinfra.lab.telemetry.plugin;

public interface ComponentPlugin {

	String pluginName();

	ClassEnhancement[] classEnhancements();

}
