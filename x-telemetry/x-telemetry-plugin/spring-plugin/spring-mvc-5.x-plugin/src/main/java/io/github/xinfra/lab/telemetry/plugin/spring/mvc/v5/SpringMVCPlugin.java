package io.github.xinfra.lab.telemetry.plugin.spring.mvc.v5;

import io.github.xinfra.lab.telemetry.plugin.ClassEnhancement;
import io.github.xinfra.lab.telemetry.plugin.ComponentPlugin;
import io.github.xinfra.lab.telemetry.plugin.spring.mvc.v5.enhancement.ControllerClassEnhancement;

public class SpringMVCPlugin implements ComponentPlugin {

	public static final String NAME = "SpringMVC-5.x";

	private final ClassEnhancement controllerEnhancement = new ControllerClassEnhancement();

	@Override
	public String pluginName() {
		return NAME;
	}

	@Override
	public ClassEnhancement[] classEnhancements() {
		return new ClassEnhancement[] { controllerEnhancement };
	}

}
