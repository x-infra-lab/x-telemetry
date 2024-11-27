package io.github.xinfra.lab.telemetry.plugin;

import io.github.xinfra.lab.telemetry.plugin.interceptor.ConstructorInterceptPoint;
import io.github.xinfra.lab.telemetry.plugin.interceptor.InstanceMethodInterceptorPoint;
import io.github.xinfra.lab.telemetry.plugin.interceptor.StaticMethodInterceptorPoint;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

public abstract class AbstractClassEnhancePlugin {


    protected String[] witnessClasses() {
        // todo
        return null;
    }

    protected WitnessMethod[] witnessMethods() {
        // todo
        return null;
    }

    public boolean isBootstrapInstrumentation() {
        // todo
        return false;
    }

    public abstract ElementMatcher<TypeDescription> enhanceClass();

    public abstract ConstructorInterceptPoint[] getConstructorsInterceptPoints();

    public abstract InstanceMethodInterceptorPoint[] getInstanceMethodInterceptPoints();

    public abstract StaticMethodInterceptorPoint[] getStaticMethodInterceptPoints();

}
