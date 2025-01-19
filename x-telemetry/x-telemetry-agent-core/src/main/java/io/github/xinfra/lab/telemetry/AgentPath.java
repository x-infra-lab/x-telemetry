package io.github.xinfra.lab.telemetry;

import io.github.xinfra.lab.telemetry.exception.AgentPathLocateException;
import io.github.xinfra.lab.telemetry.logger.AgentLogManager;
import io.github.xinfra.lab.telemetry.logger.AgentLogger;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AgentPath {

	private static final AgentLogger LOGGER = AgentLogManager.getLogger(AgentPath.class);

	public static volatile Path agentDirPath;

	public static Path getAgentDirPath() {
		if (agentDirPath != null) {
			return agentDirPath;
		}

		synchronized (AgentPath.class) {
			if (agentDirPath == null) {
				agentDirPath = locateAgentDirPath();
			}
		}
		return agentDirPath;
	}

	private static Path locateAgentDirPath() throws AgentPathLocateException {
		try {
			String resourceName = AgentPath.class.getName().replaceAll("\\.", "/") + ".class";
			URL resource = AgentPath.class.getClassLoader().getResource(resourceName);
			Path agentDirPath = null;
			if (resource != null) {
				String urlString = resource.toString();
				int insidePathIndex = urlString.indexOf('!');
				boolean isInJar = insidePathIndex > -1;
				if (isInJar) {
					urlString = urlString.substring(urlString.indexOf("file:"), insidePathIndex);
					Path agentJarPath = Paths.get(new URL(urlString).toURI());
					agentDirPath = agentJarPath.getParent();
				}
				else {
					// maybe in ide environment
					urlString = urlString.substring(urlString.indexOf("file:"),
							urlString.length() - resourceName.length());
					agentDirPath = Paths.get(new URL(urlString).toURI());
				}
			}
			else {
				throw new AgentPathLocateException(
						"locate agent dir fail. resource not found. resourceName:" + resourceName);
			}
			if (Files.exists(agentDirPath)) {
				return agentDirPath;
			}
			else {
				throw new AgentPathLocateException("locate agent dir fail. dir not exist. dirPath:" + agentDirPath);
			}
		}
		catch (Exception e) {
			LOGGER.error("locateAgentDirPath fail.", e);
			if (e instanceof AgentPathLocateException) {
				throw (AgentPathLocateException) e;
			}
			else {
				throw new AgentPathLocateException("locateAgentDirPath fail.", e);
			}
		}
	}

}
