package io.github.xinfra.lab.telemetry.plugin.interceptor;

public interface ConstructorInterceptor {

	void onConstruct(Object obj, Object[] allArguments) throws Throwable;

}
