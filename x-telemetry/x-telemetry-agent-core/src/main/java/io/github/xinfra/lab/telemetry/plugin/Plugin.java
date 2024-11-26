package io.github.xinfra.lab.telemetry.plugin;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Plugin {
    private String name;
    private String defineClass;
}
