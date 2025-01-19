package io.github.xinfra.lab.telemetry.plugin;

import io.github.xinfra.lab.telemetry.plugin.delegation.ConstructorDelegation;
import io.github.xinfra.lab.telemetry.plugin.delegation.InstanceMethodDelegation;
import io.github.xinfra.lab.telemetry.plugin.delegation.StaticMethodDelegation;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;

public class Enhances {

	public static DynamicType.Builder<?> enhance(MethodInterceptorPoint methodInterceptorPoint,
			DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader) {
		if (methodInterceptorPoint.isConstructor()) {
			return enhanceConstructor(methodInterceptorPoint, builder, typeDescription, classLoader);
		}
		if (methodInterceptorPoint.isStatic()) {
			return enhanceStaticMethod(methodInterceptorPoint, builder, typeDescription, classLoader);
		}

		return enhanceInstanceMethod(methodInterceptorPoint, builder, typeDescription, classLoader);
	}

	private static DynamicType.Builder<?> enhanceInstanceMethod(MethodInterceptorPoint methodInterceptorPoint,
			DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader) {
		builder = builder.method(methodInterceptorPoint.getMethodMatcher())
			.intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.withDefaultConfiguration()
				.to(new InstanceMethodDelegation(methodInterceptorPoint.getMethodInterceptor(), classLoader))));
		return builder;
	}

	private static DynamicType.Builder<?> enhanceStaticMethod(MethodInterceptorPoint methodInterceptorPoint,
			DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader) {
		builder = builder.method(ElementMatchers.isStatic().and(methodInterceptorPoint.getMethodMatcher()))
			.intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.withDefaultConfiguration()
				.to(new StaticMethodDelegation(methodInterceptorPoint.getMethodInterceptor(), classLoader))));
		return builder;
	}

	private static DynamicType.Builder<?> enhanceConstructor(MethodInterceptorPoint methodInterceptorPoint,
			DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader) {

		builder = builder.constructor(methodInterceptorPoint.getMethodMatcher())
			.intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.withDefaultConfiguration()
				.to(new ConstructorDelegation(methodInterceptorPoint.getMethodInterceptor(), classLoader))));
		return builder;
	}

}
