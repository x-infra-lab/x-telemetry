package io.github.xinfra.lab.telemetry.plugin;

import io.github.xinfra.lab.telemetry.log.LogManager;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatcher;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Logger;

public interface ClassEnhancement {

    Logger LOGGER = LogManager.getLogger(ClassEnhancement.class);

    default String[] witnessClasses() {
        return null;
    }

    default WitnessMethod[] witnessMethods() {
        return null;
    }

    ElementMatcher<TypeDescription> enhanceClass();

    MethodInterceptorPoint[] methodInterceptorPoints();


    default DynamicType.Builder<?> enhance(DynamicType.Builder<?> builder,
                                           TypeDescription typeDescription,
                                           ClassLoader classLoader) {
        if (ArrayUtils.isNotEmpty(witnessClasses())) {
            for (String className : witnessClasses()) {
                if (!Witnesses.resolveClass(className, classLoader)) {
                    LOGGER.warn("enhancement:{} can not resolve class:{}", this.getClass(), className);
                    return builder;
                }
            }
        }

        if (ArrayUtils.isNotEmpty(witnessMethods())) {
            for (WitnessMethod witnessMethod : witnessMethods()) {
                if (!Witnesses.resolveMethod(witnessMethod, classLoader)) {
                    LOGGER.warn("enhancement:{} can not resolve method:{}", this.getClass(), witnessMethod);
                    return builder;
                }
            }
        }

        if (ArrayUtils.isEmpty(methodInterceptorPoints())) {
            LOGGER.warn("enhancement:{} can not found any interceptor", this.getClass());
            return builder;
        }

        for (MethodInterceptorPoint methodInterceptorPoint : methodInterceptorPoints()) {
            builder = Enhances.enhance(methodInterceptorPoint, builder, typeDescription, classLoader);
        }
        return builder;
    }

}
