package ai.aitia.arplain;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.aitia.arplain.config.Configuration;
import ai.aitia.arplain.config.ConfigurationManager;
import ai.aitia.arplain.http.ArPlainHttpServer;

public class ArPlain {

	private final static Logger logger = LoggerFactory.getLogger(ArPlain.class);
	
	public static void main(final String[] args) {
		logger.info("Taking off...");
		
		ConfigurationManager.getInstance().loadConfigFile("src/main/resources/arplain.json");
		Configuration config = ConfigurationManager.getInstance().getCurrentConfig();
		
		if (config.isHttpEnabled()) {
			logger.info("HTTP is enabled");
			try {
				final ArPlainHttpServer httpServer = new ArPlainHttpServer(config.getHttpPort(), config.getHttpBacklog());
				httpServer.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
