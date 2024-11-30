package io.github.xinfra.lab.telemetry.plugin.spring.mvc.v5.interceptor;

import io.github.xinfra.lab.telemetry.plugin.interceptor.InstanceMethodAroundInterceptor;

import java.lang.reflect.Method;

public class RequestMappingMethodInterceptor implements InstanceMethodAroundInterceptor {
    @Override
    public void beforeMethod(Object obj, Method method, Object[] allArguments) throws Throwable {
        // todo
    }

    @Override
    public Object afterMethod(Object obj, Method method, Object[] allArguments, Object result) {
        // todo
        return null;
    }

    @Override
    public void methodException(Object obj, Method method, Object[] allArguments, Throwable throwable) {
        // todo
    }
}
