package io.github.xinfra.lab.telemetry.service;

import com.google.common.collect.Lists;
import io.github.xinfra.lab.telemetry.service.opentelemetry.OpenTelemetrys;

import java.util.List;

public class ServiceManager {

	private static List<AgentService> serviceList = Lists.newArrayList(new OpenTelemetrys());

	public static void startup() {
		serviceList.forEach(AgentService::startup);
	}

	public static void shutdown() {
		serviceList.forEach(AgentService::shutdown);
	}

}
