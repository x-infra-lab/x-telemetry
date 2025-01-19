package io.github.xinfra.lab.telemetry.plugin;

import lombok.Getter;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.ArrayList;
import java.util.List;

public class PluginManager {

	@Getter
	private static List<ComponentPlugin> plugins = new ArrayList<>();

	public static void loadPlugins() {
		// todo
	}

	public static ElementMatcher<TypeDescription> typeMatcher() {
		ElementMatcher.Junction<TypeDescription> matcher = ElementMatchers.none();
		for (ComponentPlugin plugin : plugins) {
			for (ClassEnhancement classEnhancement : plugin.classEnhancements()) {
				matcher = matcher.or(classEnhancement.enhanceClass());
			}
		}
		return matcher;
	}

	public static List<ClassEnhancement> getMatchClassEnhancements(TypeDescription typeDescription) {
		List<ClassEnhancement> matchClassEnhancements = new ArrayList<>();
		for (ComponentPlugin plugin : plugins) {
			for (ClassEnhancement classEnhancement : plugin.classEnhancements()) {
				if (classEnhancement.enhanceClass().matches(typeDescription)) {
					matchClassEnhancements.add(classEnhancement);
				}
			}
		}
		return matchClassEnhancements;
	}

}
