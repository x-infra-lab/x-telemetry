package io.github.xinfra.lab.telemetry.plugin.spring.mvc.v5;

import io.github.xinfra.lab.telemetry.plugin.ClassEnhancement;
import io.github.xinfra.lab.telemetry.plugin.ComponentPlugin;
import io.github.xinfra.lab.telemetry.plugin.spring.mvc.v5.enhancement.ControllerClassEnhancement;

public class SpringMVCPlugin implements ComponentPlugin {
    private final ClassEnhancement controllerEnhancement = new ControllerClassEnhancement();

    @Override
    public String pluginName() {
        return "spring-mvc-5.x";
    }

    @Override
    public ClassEnhancement[] classEnhancements() {
        return new ClassEnhancement[]{
                controllerEnhancement
        };
    }
}
