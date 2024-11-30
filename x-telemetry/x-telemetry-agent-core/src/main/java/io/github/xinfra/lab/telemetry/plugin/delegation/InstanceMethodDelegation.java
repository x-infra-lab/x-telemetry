package io.github.xinfra.lab.telemetry.plugin.delegation;

import io.github.xinfra.lab.telemetry.exception.AgentClassLoadException;
import io.github.xinfra.lab.telemetry.log.LogManager;
import io.github.xinfra.lab.telemetry.plugin.Interceptors;
import io.github.xinfra.lab.telemetry.plugin.interceptor.InstanceMethodAroundInterceptor;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class InstanceMethodDelegation {
    private static final Logger LOGGER = LogManager.getLogger(InstanceMethodDelegation.class);

    private InstanceMethodAroundInterceptor interceptor;

    public InstanceMethodDelegation(String methodInterceptorClassName, ClassLoader classLoader) {
        try {
            interceptor = Interceptors.load(methodInterceptorClassName, classLoader);
        } catch (Throwable t) {
            LOGGER.error("load InstanceMethodAroundInterceptor class:{} failed.", methodInterceptorClassName, t);
            throw new AgentClassLoadException("load class failed. className:" + methodInterceptorClassName, t);
        }
    }

    @RuntimeType
    public Object intercept(@This Object obj, @Origin Method method,
                            @AllArguments Object[] allArguments,
                            @SuperCall Callable<?> zuper) throws Throwable {
        try {
            interceptor.beforeMethod(obj, method, allArguments);
        } catch (Throwable t) {
            LOGGER.error("InstanceMethodAroundInterceptor beforeMethod failed. class:{} method:{}", obj.getClass(), method.getName());
        }

        Object result = null;
        try {
            result = zuper.call();
        } catch (Throwable throwable) {
            try {
                interceptor.methodException(obj, method, allArguments, throwable);
            } catch (Throwable t) {
                LOGGER.error("InstanceMethodAroundInterceptor methodException failed. class:{} method:{}", obj.getClass(), method.getName());
            }

            throw throwable;
        } finally {
            try {
                result = interceptor.afterMethod(obj, method, allArguments, result);
            } catch (Throwable t) {
                LOGGER.error("InstanceMethodAroundInterceptor afterMethod failed. class:{} method:{}", obj.getClass(), method.getName());
            }
        }
        
        return result;
    }
}
