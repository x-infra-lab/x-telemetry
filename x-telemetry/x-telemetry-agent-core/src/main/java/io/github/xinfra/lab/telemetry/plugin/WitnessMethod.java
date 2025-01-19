package io.github.xinfra.lab.telemetry.plugin;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.ToString;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.isSynthetic;
import static net.bytebuddy.matcher.ElementMatchers.not;

@ToString
public class WitnessMethod {

	@Getter
	private final String declaringClassName;

	@Getter
	private final ElementMatcher<? super MethodDescription.InDefinedShape> elementMatcher;

	public WitnessMethod(String declaringClassName,
			ElementMatcher.Junction<? super MethodDescription.InDefinedShape> elementMatcher) {
		this.declaringClassName = declaringClassName;
		this.elementMatcher = Preconditions.checkNotNull(elementMatcher).and(not(isSynthetic()));
	}

}
