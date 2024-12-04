package io.github.xinfra.lab.telemetry.plugin.spring.mvc.v5.interceptor;

import io.github.xinfra.lab.telemetry.plugin.InterceptContext;
import io.github.xinfra.lab.telemetry.plugin.interceptor.InstanceMethodAroundInterceptor;
import io.github.xinfra.lab.telemetry.plugin.spring.mvc.v5.SpringMVCPlugin;
import io.github.xinfra.lab.telemetry.service.OpenTelemetrys;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Controller
@RequestMapping(path = "")
public class RequestMappingMethodInterceptor implements InstanceMethodAroundInterceptor {

    @Override
    public void beforeMethod(InterceptContext interceptContext, Object obj, Method method, Object[] allArguments) throws Throwable {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Context parentContext = Context.current();
        if (parentContext == null) {
            // get parent context from request
            // todo
        }
        Tracer tracer = OpenTelemetrys.getTracer(SpringMVCPlugin.NAME);
        Span span = tracer.spanBuilder(operationName(method))
                .setParent(parentContext)
                .startSpan();
        Scope scope = span.makeCurrent();
        interceptContext.setScope(scope);
    }


    @Override
    public Object afterMethod(InterceptContext interceptContext, Object obj, Method method, Object[] allArguments, Object result) {
        Span span = Span.current();
        span.end();
        Scope scope = interceptContext.getScope();
        scope.close();
        return result;
    }

    @Override
    public void methodException(InterceptContext interceptContext, Object obj, Method method, Object[] allArguments, Throwable throwable) {
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
