package io.github.xinfra.lab.telemetry.plugin.spring.mvc.v5.enhancement;

import io.github.xinfra.lab.telemetry.plugin.ClassEnhancement;
import io.github.xinfra.lab.telemetry.plugin.MethodInterceptorPoint;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class ControllerClassEnhancement implements ClassEnhancement {

	@Override
	public ElementMatcher<TypeDescription> enhanceClass() {
		return isAnnotatedWith(named("org.springframework.stereotype.Controller"));
	}

	@Override
	public MethodInterceptorPoint[] methodInterceptorPoints() {
		return new MethodInterceptorPoint[] { new MethodInterceptorPoint() {
			@Override
			public ElementMatcher<MethodDescription> getMethodMatcher() {
				return isAnnotatedWith(named("org.springframework.web.bind.annotation.RequestMapping"));
			}

			@Override
			public String getMethodInterceptor() {
				return "io.github.xinfra.lab.telemetry.plugin.spring.mvc.v5.interceptor.RequestMappingMethodInterceptor";
			}
		} };
	}

}
