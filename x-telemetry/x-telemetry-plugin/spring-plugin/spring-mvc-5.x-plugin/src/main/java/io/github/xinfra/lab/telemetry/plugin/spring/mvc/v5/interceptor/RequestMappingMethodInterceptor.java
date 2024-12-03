package io.github.xinfra.lab.telemetry.plugin.spring.mvc.v5.interceptor;

import io.github.xinfra.lab.telemetry.plugin.InterceptContext;
import io.github.xinfra.lab.telemetry.plugin.interceptor.InstanceMethodAroundInterceptor;
import io.github.xinfra.lab.telemetry.service.OpenTelemetrys;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.context.Context;

import java.lang.reflect.Method;

public class RequestMappingMethodInterceptor implements InstanceMethodAroundInterceptor {

    @Override
    public void beforeMethod(InterceptContext interceptContext, Object obj, Method method, Object[] allArguments) throws Throwable {
        // todo
        Context parentContext = Context.current();
        OpenTelemetry openTelemetry = OpenTelemetrys.getOpenTelemetry();


    }

    @Override
    public Object afterMethod(InterceptContext interceptContext, Object obj, Method method, Object[] allArguments, Object result) {
        // todo
        return null;
    }

    @Override
    public void methodException(InterceptContext interceptContext, Object obj, Method method, Object[] allArguments, Throwable throwable) {
        // todo
    }
}
