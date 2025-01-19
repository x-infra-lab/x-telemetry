package io.github.xinfra.lab.telemetry.plugin;

import io.opentelemetry.context.Scope;
import io.opentelemetry.sdk.internal.AttributesMap;
import lombok.Getter;
import lombok.Setter;

public class InterceptContext {

	private static final int DEFAULT_SPAN_MAX_NUM_ATTRIBUTES = 128;

	private static final int DEFAULT_SPAN_MAX_ATTRIBUTE_LENGTH = Integer.MAX_VALUE;

	@Getter
	@Setter
	Scope scope;

	private AttributesMap attributes;

	public AttributesMap getAttributes() {
		if (attributes == null) {
			attributes = AttributesMap.create(DEFAULT_SPAN_MAX_NUM_ATTRIBUTES, DEFAULT_SPAN_MAX_ATTRIBUTE_LENGTH);
		}
		return attributes;
	}

}
