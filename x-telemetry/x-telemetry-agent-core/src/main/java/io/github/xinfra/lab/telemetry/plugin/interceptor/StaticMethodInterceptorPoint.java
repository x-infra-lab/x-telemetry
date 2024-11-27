package io.github.xinfra.lab.telemetry.plugin.interceptor;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

public interface StaticMethodInterceptorPoint {

    ElementMatcher<MethodDescription> getMethodMatcher();

    String getStaticMethodInterceptor();

    boolean isOverrideArgs();

}
