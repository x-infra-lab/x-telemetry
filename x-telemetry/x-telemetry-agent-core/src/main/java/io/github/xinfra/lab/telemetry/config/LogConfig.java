package io.github.xinfra.lab.telemetry.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogConfig {
    public  String filename = "x-telemetry.log";
    public  String dir = "logs/";

    // todo
}
