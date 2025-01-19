package io.github.xinfra.lab.telemetry.plugin;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

public interface MethodInterceptorPoint {

	ElementMatcher<MethodDescription> getMethodMatcher();

	String getMethodInterceptor();

	default boolean isStatic() {
		return false;
	}

	default boolean isConstructor() {
		return false;
	}

}
