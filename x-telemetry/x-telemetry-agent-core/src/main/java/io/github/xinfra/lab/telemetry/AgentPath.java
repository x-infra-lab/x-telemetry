package io.github.xinfra.lab.telemetry;

import io.github.xinfra.lab.telemetry.exception.AgentPathLocateException;
import io.github.xinfra.lab.telemetry.log.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AgentPath {
    private static final Logger LOGGER = LogManager.getLogger(AgentPath.class);
    public static volatile Path agentDirPath;


    public static Path getAgentDirPath() {
        if (agentDirPath != null) {
            return agentDirPath;
        }

        synchronized (AgentPath.class) {
            if (agentDirPath == null) {
                try {
                    agentDirPath = locateAgentDirPath();
                } catch (Exception e) {
                    LOGGER.error("locateAgentDirPath fail.", e);
                    throw new AgentPathLocateException("locateAgentDirPath fail.", e);
                }
            }
        }
        return agentDirPath;
    }

    private static Path locateAgentDirPath() throws MalformedURLException, URISyntaxException {
        String resourceName = AgentPath.class.getName().replaceAll("\\.", "/") + ".class";
        URL resource = AgentPath.class.getClassLoader()
                .getResource(resourceName);
        if (resource != null) {
            String urlString = resource.toString();

            int insidePathIndex = urlString.indexOf('!');
            boolean isInJar = insidePathIndex > -1;
            if (isInJar) {
                urlString = urlString.substring(urlString.indexOf("file:"), insidePathIndex);
                Path agentJarPath = Paths.get(new URL(urlString).toURI());
                if (Files.exists(agentJarPath)) {
                    return agentJarPath.getParent();
                }
            } else {
                // maybe in ide environment
                urlString = urlString.substring(urlString.indexOf("file:"), urlString.length() - resourceName.length());
                Path agentDirPath = Paths.get(new URL(urlString).toURI());
                if (Files.exists(agentDirPath)) {
                    return agentDirPath;
                }
            }
        }
        throw new AgentPathLocateException("locate agent dir fail.");
    }
}
