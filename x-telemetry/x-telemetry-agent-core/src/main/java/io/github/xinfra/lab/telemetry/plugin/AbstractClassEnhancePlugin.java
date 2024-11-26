package io.github.xinfra.lab.telemetry.plugin;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;


public abstract class AbstractClassEnhancePlugin {

    abstract ElementMatcher<TypeDescription> enhanceClass();

    protected ElementMatcher<TypeDescription> witnessClasses() {
        // todo
        return null;
    }

    protected ElementMatcher<MethodDescription> witnessMethods() {
        // todo
        return null;
    }

    public boolean isBootstrapInstrumentation() {
        // todo
        return false;
    }
}
