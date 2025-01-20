package io.github.xinfra.lab.telemetry.service.jmx;

import io.github.xinfra.lab.telemetry.service.opentelemetry.OpenTelemetrys;
import io.github.xinfra.lab.telemetry.service.AgentService;
import io.opentelemetry.instrumentation.jmx.engine.JmxMetricInsight;
import io.opentelemetry.instrumentation.jmx.engine.MetricConfiguration;

import java.time.Duration;

public class JmxService implements AgentService {

	private JmxMetricInsight jmxMetricInsight;

	@Override
	public void startup() {
		JmxMetricInsight jmxMetricInsight = JmxMetricInsight.createService(OpenTelemetrys.get(),
				Duration.ofMinutes(1).toMillis());
		MetricConfiguration conf = new MetricConfiguration();
		jmxMetricInsight.startLocal(conf);
	}

	@Override
	public void shutdown() {
		// do nothing
	}

}
