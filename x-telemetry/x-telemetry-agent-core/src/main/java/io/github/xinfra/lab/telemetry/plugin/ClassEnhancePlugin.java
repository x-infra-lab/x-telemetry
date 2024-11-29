package io.github.xinfra.lab.telemetry.plugin;

import io.github.xinfra.lab.telemetry.log.LogManager;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatcher;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Logger;

public interface ClassEnhancePlugin {

    Logger LOGGER = LogManager.getLogger(ClassEnhancePlugin.class);

    default String[] witnessClasses() {
        return null;
    }

    default WitnessMethod[] witnessMethods() {
        return null;
    }

    String pluginName();

    ElementMatcher<TypeDescription> enhanceClass();

    MethodInterceptorPoint[] methodInterceptorPoints();


    default DynamicType.Builder<?> enhance(DynamicType.Builder<?> builder,
                                           TypeDescription typeDescription,
                                           ClassLoader classLoader) {
        if (ArrayUtils.isNotEmpty(witnessClasses())) {
            for (String className : witnessClasses()) {
                if (!Witnesses.resolveClass(className, classLoader)) {
                    LOGGER.warn("plugin:{} can not resolve class:{}", pluginName(), className);
                    return builder;
                }
            }
        }

        if (ArrayUtils.isNotEmpty(witnessMethods())) {
            for (WitnessMethod witnessMethod : witnessMethods()) {
                if (!Witnesses.resolveMethod(witnessMethod, classLoader)) {
                    LOGGER.warn("plugin:{} can not resolve method:{}", pluginName(), witnessMethod);
                    return builder;
                }
            }
        }

        if (ArrayUtils.isEmpty(methodInterceptorPoints())) {
            LOGGER.warn("plugin:{} can not found any interceptor", pluginName());
            return builder;
        }

        for (MethodInterceptorPoint methodInterceptorPoint : methodInterceptorPoints()) {
            builder = Enhances.enhance(methodInterceptorPoint, builder, typeDescription, classLoader);
        }
        return builder;
    }

}
