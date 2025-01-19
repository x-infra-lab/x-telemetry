package io.github.xinfra.lab.telemetry.plugin.spring.mvc.v5;

import io.opentelemetry.context.propagation.TextMapGetter;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

public enum JavaxHttpServletRequestGetter implements TextMapGetter<HttpServletRequest> {

	INSTANCE;

	@Override
	public Iterable<String> keys(HttpServletRequest carrier) {
		return Collections.list(carrier.getHeaderNames());
	}

	@Override
	public String get(HttpServletRequest carrier, String key) {
		return carrier.getHeader(key);
	}

}
