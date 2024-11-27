package io.github.xinfra.lab.telemetry.plugin.interceptor;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

public interface ConstructorInterceptPoint {
    ElementMatcher<MethodDescription> getConstructorMatcher();

    String getConstructorInterceptor();

}
