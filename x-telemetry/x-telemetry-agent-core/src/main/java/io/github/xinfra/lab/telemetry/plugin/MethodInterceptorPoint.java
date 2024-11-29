package io.github.xinfra.lab.telemetry.plugin;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

public interface MethodInterceptorPoint {

    ElementMatcher<MethodDescription> getMethodMatcher();

    String getMethodInterceptor();

    boolean isOverrideArgs();

    boolean isStatic();

    boolean isConstructor();

}
