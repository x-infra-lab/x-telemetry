package io.github.xinfra.lab.telemetry.plugin.delegation;

import io.github.xinfra.lab.telemetry.exception.AgentClassLoadException;
import io.github.xinfra.lab.telemetry.logger.AgentLogManager;
import io.github.xinfra.lab.telemetry.logger.AgentLogger;
import io.github.xinfra.lab.telemetry.plugin.InterceptContext;
import io.github.xinfra.lab.telemetry.plugin.Interceptors;
import io.github.xinfra.lab.telemetry.plugin.interceptor.StaticMethodAroundInterceptor;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class StaticMethodDelegation {

	private static final AgentLogger LOGGER = AgentLogManager.getLogger(StaticMethodDelegation.class);

	private StaticMethodAroundInterceptor interceptor;

	public StaticMethodDelegation(String methodInterceptorClassName, ClassLoader classLoader) {
		try {
			interceptor = Interceptors.load(methodInterceptorClassName, classLoader);
		}
		catch (Throwable t) {
			LOGGER.error("load InstanceMethodAroundInterceptor class:{} failed.", methodInterceptorClassName, t);
			throw new AgentClassLoadException("load class failed. className:" + methodInterceptorClassName, t);
		}
	}

	@RuntimeType
	public Object intercept(@Origin Class<?> clazz, @Origin Method method, @AllArguments Object[] allArguments,
			@SuperCall Callable<?> zuper) throws Throwable {

		InterceptContext interceptContext = new InterceptContext();
		try {
			interceptor.beforeMethod(interceptContext, clazz, method, allArguments);
		}
		catch (Throwable t) {
			LOGGER.error("StaticMethodAroundInterceptor beforeMethod failed. class:{} method:{}", clazz,
					method.getName());
		}
		Object result = null;
		try {
			result = zuper.call();
		}
		catch (Throwable throwable) {
			try {
				interceptor.methodException(interceptContext, clazz, method, allArguments, throwable);
			}
			catch (Throwable t) {
				LOGGER.error("StaticMethodAroundInterceptor methodException failed. class:{} method:{}", clazz,
						method.getName());
			}
			throw throwable;
		}
		finally {
			try {
				result = interceptor.afterMethod(interceptContext, clazz, method, allArguments, result);
			}
			catch (Throwable t) {
				LOGGER.error("StaticMethodAroundInterceptor afterMethod failed. class:{} method:{}", clazz,
						method.getName());
			}
		}

		return result;
	}

}
