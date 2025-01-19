package io.github.xinfra.lab.telemetry.agent;

import io.github.xinfra.lab.telemetry.config.ConfigManager;
import io.github.xinfra.lab.telemetry.logger.AgentLogManager;
import io.github.xinfra.lab.telemetry.logger.AgentLogger;
import io.github.xinfra.lab.telemetry.plugin.ClassEnhancement;
import io.github.xinfra.lab.telemetry.plugin.PluginManager;
import io.github.xinfra.lab.telemetry.service.ServiceManager;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

/**
 * x-telemetry java-agent
 */
public class XTelemetryAgent {

	private static final AgentLogger LOGGER = AgentLogManager.getLogger(XTelemetryAgent.class);

	public static void premain(String agentArgs, Instrumentation inst) {
		try {
			ConfigManager.loadConfig(agentArgs);
		}
		catch (Exception e) {
			LOGGER.error("XTelemetryAgent load config failed. Shutting down.", e);
			return;
		}

		try {
			ConfigManager.refreshConfig();
		}
		catch (Exception e) {
			LOGGER.error("XTelemetryAgent refresh config failed. Shutting down.", e);
			return;
		}

		if (!ConfigManager.AGENT_CONFIG.isEnable()) {
			LOGGER.info("XTelemetryAgent is disabled.");
			return;
		}

		try {
			PluginManager.loadPlugins();
		}
		catch (Exception e) {
			LOGGER.error("XTelemetryAgent loadPlugins failed. Shutting down.", e);
			return;
		}

		try {
			installTransformer(inst);
		}
		catch (Exception e) {
			LOGGER.error("XTelemetryAgent installTransformer failed.", e);
		}
		try {
			ServiceManager.startup();
		}
		catch (Exception e) {
			LOGGER.error("XTelemetryAgent ServiceManager startup failed.", e);
		}

		Runtime.getRuntime()
			.addShutdownHook(new Thread(ServiceManager::shutdown, "XTelemetryAgent-ShutdownHook-Thread"));
	}

	private static void installTransformer(Instrumentation inst) {
		new AgentBuilder.Default().ignore(ElementMatchers.isSynthetic().or(nameStartsWith("net.bytebuddy.")) // ignore
																												// bytebuddy
		).type(PluginManager.typeMatcher()).transform(new AgentTransformer()).installOn(inst);
	}

	public static class AgentTransformer implements AgentBuilder.Transformer {

		@Override
		public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription,
				ClassLoader classLoader, JavaModule module, ProtectionDomain protectionDomain) {

			List<ClassEnhancement> classEnhancementList = PluginManager.getMatchClassEnhancements(typeDescription);
			if (classEnhancementList.isEmpty()) {
				LOGGER.info("type:{}. no match classEnhancement", typeDescription);
				return builder;
			}

			for (ClassEnhancement classEnhancement : classEnhancementList) {
				builder = classEnhancement.enhance(builder, typeDescription, classLoader);
			}

			return builder;
		}

	}

}
