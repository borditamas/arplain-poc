package ai.aitia.netplain;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.aitia.netplain.config.Configuration;
import ai.aitia.netplain.config.ConfigurationManager;
import ai.aitia.netplain.core.ServerListenerThread;

public class NetPlainServer {
	
	//=================================================================================================
	// members
	
	private final static Logger logger = LoggerFactory.getLogger(NetPlainServer.class);

	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public static void main(final String[] args) {
		logger.info("Server strating...");
		
		ConfigurationManager.getInstance().loadConfigFile("src/main/resources/http.json");
		final Configuration config = ConfigurationManager.getInstance().getCurrentConfig();
		
		logger.info("Using port: " + config.getPort());
		logger.info("Using webroot: " + config.getWebroot());
		
		try {
			final ServerListenerThread serverListener = new ServerListenerThread(config.getPort(), config.getWebroot());
			serverListener.start();
			
		} catch (final IOException ex) {
			logger.error("Problem with setting server socket", ex);
		}
	}
}
