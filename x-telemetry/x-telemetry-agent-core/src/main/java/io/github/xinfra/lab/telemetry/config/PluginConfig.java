package io.github.xinfra.lab.telemetry.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PluginConfig {

	List<String> disabledPlugins;

}
