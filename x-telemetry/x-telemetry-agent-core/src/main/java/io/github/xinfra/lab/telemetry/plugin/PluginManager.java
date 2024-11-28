package io.github.xinfra.lab.telemetry.plugin;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.List;

public class PluginManager {

    public static void loadPlugins() {
        // todo
    }

    public static ElementMatcher<TypeDescription> typeMatcher() {
        // todo
        return null;
    }

    public static List<AbstractClassEnhancePlugin> getMatchPlugin(TypeDescription typeDescription) {
        // todo

        return null;
    }
}
