package io.github.xinfra.lab.telemetry.plugin.interceptor;

import io.github.xinfra.lab.telemetry.plugin.InterceptContext;

import java.lang.reflect.Method;

public interface InstanceMethodAroundInterceptor {

	void beforeMethod(InterceptContext interceptContext, Object obj, Method method, Object[] allArguments)
			throws Throwable;

	Object afterMethod(InterceptContext interceptContext, Object obj, Method method, Object[] allArguments,
			Object result);

	void methodException(InterceptContext interceptContext, Object obj, Method method, Object[] allArguments,
			Throwable throwable);

}
