package io.github.xinfra.lab.telemetry.plugin.spring.mvc.v5.interceptor;

import io.github.xinfra.lab.telemetry.plugin.InterceptContext;
import io.github.xinfra.lab.telemetry.plugin.interceptor.InstanceMethodAroundInterceptor;
import io.github.xinfra.lab.telemetry.plugin.spring.mvc.v5.JavaxHttpServletRequestGetter;
import io.github.xinfra.lab.telemetry.plugin.spring.mvc.v5.SpringMVCPlugin;
import io.github.xinfra.lab.telemetry.service.opentelemetry.OpenTelemetrys;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.sdk.trace.ReadWriteSpan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

import static io.github.xinfra.lab.telemetry.common.AttributeKeys.METHOD_ATTR_KEY;
import static io.opentelemetry.api.trace.SpanKind.SERVER;

public class RequestMappingMethodInterceptor implements InstanceMethodAroundInterceptor {

	private DoubleHistogram springRequest = OpenTelemetrys.get()
		.meterBuilder(SpringMVCPlugin.NAME)
		.build()
		.histogramBuilder("spring.mvc.request")
		.build();

	@Override
	public void beforeMethod(InterceptContext interceptContext, Object obj, Method method, Object[] allArguments)
			throws Throwable {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
			.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		Context parentContext = Context.current();
		// get parent context from request
		ContextPropagators propagators = OpenTelemetrys.getPropagators();
		propagators.getTextMapPropagator().extract(parentContext, request, JavaxHttpServletRequestGetter.INSTANCE);

		Tracer tracer = OpenTelemetrys.getTracer(SpringMVCPlugin.NAME);
		Span span = tracer.spanBuilder(operationName(method)).setParent(parentContext).setSpanKind(SERVER).startSpan();
		Scope scope = span.makeCurrent();
		interceptContext.setScope(scope);
	}

	@Override
	public Object afterMethod(InterceptContext interceptContext, Object obj, Method method, Object[] allArguments,
			Object result) {
		ReadWriteSpan span = (ReadWriteSpan) Span.current();
		span.end();
		Scope scope = interceptContext.getScope();
		scope.close();

		// record metrics
		Attributes attributes = Attributes.of(METHOD_ATTR_KEY, span.getName());
		springRequest.record(span.getLatencyNanos(), attributes);
		return result;
	}

	@Override
	public void methodException(InterceptContext interceptContext, Object obj, Method method, Object[] allArguments,
			Throwable throwable) {
		Span span = Span.current();
		span.recordException(throwable);
	}

	private String operationName(Method method) {
		RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
		if (requestMapping != null) {
			if (requestMapping.value().length > 0) {
				return requestMapping.value()[0];
			}
		}
		return method.getDeclaringClass().getName() + "#" + method.getName();
	}

}
