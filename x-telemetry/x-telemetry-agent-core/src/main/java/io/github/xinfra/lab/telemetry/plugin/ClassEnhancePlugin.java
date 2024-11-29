package io.github.xinfra.lab.telemetry.plugin;

import io.github.xinfra.lab.telemetry.plugin.interceptor.MethodInterceptorPoint;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatcher;
import org.apache.commons.lang3.ArrayUtils;

public interface ClassEnhancePlugin {


    default String[] witnessClasses() {
        return null;
    }

    default WitnessMethod[] witnessMethods() {
        return null;
    }

    default boolean isBootstrapInstrumentation() {
        return false;
    }

    ElementMatcher<TypeDescription> enhanceClass();


    MethodInterceptorPoint[] methodInterceptors();


    default DynamicType.Builder<?> enhance(DynamicType.Builder<?> builder,
                                           TypeDescription typeDescription,
                                           ClassLoader classLoader) {
        if (ArrayUtils.isNotEmpty(witnessClasses())){
            // todo
        }
        if (ArrayUtils.isNotEmpty(witnessMethods())){
            // todo
        }

        if(ArrayUtils.isNotEmpty(methodInterceptors())){
            // todo
        }

        // todo
        return null;
    }

}
