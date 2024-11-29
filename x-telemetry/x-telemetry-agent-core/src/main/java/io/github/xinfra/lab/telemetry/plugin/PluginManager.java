package io.github.xinfra.lab.telemetry.plugin;

import lombok.Getter;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.ArrayList;
import java.util.List;

public class PluginManager {

    @Getter
    private static List<ClassEnhancePlugin> classEnhancePlugins = new ArrayList<>();

    public static void loadPlugins() {
        // todo
    }

    public static ElementMatcher<TypeDescription> typeMatcher() {
        ElementMatcher.Junction<TypeDescription> matcher  = ElementMatchers.none();
        for (ClassEnhancePlugin classEnhancePlugin : classEnhancePlugins) {
                matcher = matcher.or(classEnhancePlugin.enhanceClass());
        }
        return matcher;
    }

    public static List<ClassEnhancePlugin> getMatchPlugin(TypeDescription typeDescription) {
        List<ClassEnhancePlugin> matchPlugins = new ArrayList<>();
        for (ClassEnhancePlugin classEnhancePlugin : classEnhancePlugins) {
            if (classEnhancePlugin.enhanceClass().matches(typeDescription)) {
                matchPlugins.add(classEnhancePlugin);
            }
        }
        return matchPlugins;
    }
}
