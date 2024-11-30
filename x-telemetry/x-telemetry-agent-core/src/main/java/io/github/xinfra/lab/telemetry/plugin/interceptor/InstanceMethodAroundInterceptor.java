package io.github.xinfra.lab.telemetry.plugin.interceptor;

import java.lang.reflect.Method;

public interface InstanceMethodAroundInterceptor {

    void beforeMethod(Object obj, Method method, Object[] allArguments) throws Throwable;

    Object afterMethod(Object obj, Method method, Object[] allArguments, Object result);

    void methodException(Object obj, Method method, Object[] allArguments, Throwable throwable);
}
