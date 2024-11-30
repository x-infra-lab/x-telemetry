package io.github.xinfra.lab.telemetry.plugin.interceptor;

import java.lang.reflect.Method;

public interface StaticMethodAroundInterceptor {

    void beforeMethod(Class<?> clazz, Method method, Object[] allArguments);

    void methodException(Class<?> clazz, Method method, Object[] allArguments, Throwable throwable);

    Object afterMethod(Class<?> clazz, Method method, Object[] allArguments, Object result);
}
