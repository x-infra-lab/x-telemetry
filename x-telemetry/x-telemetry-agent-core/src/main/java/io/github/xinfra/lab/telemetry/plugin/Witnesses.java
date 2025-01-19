package io.github.xinfra.lab.telemetry.plugin;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.pool.TypePool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Witnesses {

	private static final Map<ClassLoader, TypePool> poolMap = new ConcurrentHashMap<>();

	private static final ClassLoader NULL_CLASSLOADER = new ClassLoader() {
	};

	public static TypePool.Resolution describeType(String className, ClassLoader classLoader) {
		if (classLoader == null) {
			classLoader = NULL_CLASSLOADER;
		}
		TypePool typePool = poolMap.computeIfAbsent(classLoader, (loader) -> TypePool.Default.of(loader));
		return typePool.describe(className);
	}

	public static boolean resolveClass(String className, ClassLoader classLoader) {
		TypePool.Resolution resolution = describeType(className, classLoader);
		return resolution.isResolved();
	}

	public static boolean resolveMethod(WitnessMethod witnessMethod, ClassLoader classLoader) {
		TypePool.Resolution resolution = describeType(witnessMethod.getDeclaringClassName(), classLoader);
		if (!resolution.isResolved()) {
			return false;
		}
		TypeDescription typeDescription = resolution.resolve();

		return !typeDescription.getDeclaredMethods().filter(witnessMethod.getElementMatcher()).isEmpty();
	}

}
