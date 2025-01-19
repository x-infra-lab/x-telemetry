package io.github.xinfra.lab.telemetry.plugin.interceptor;

import io.github.xinfra.lab.telemetry.plugin.InterceptContext;

import java.lang.reflect.Method;

public interface StaticMethodAroundInterceptor {

	void beforeMethod(InterceptContext interceptContext, Class<?> clazz, Method method, Object[] allArguments);

	void methodException(InterceptContext interceptContext, Class<?> clazz, Method method, Object[] allArguments,
			Throwable throwable);

	Object afterMethod(InterceptContext interceptContext, Class<?> clazz, Method method, Object[] allArguments,
			Object result);

}
