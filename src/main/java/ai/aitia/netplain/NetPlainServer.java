package ai.aitia.netplain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.aitia.netplain.config.Configuration;
import ai.aitia.netplain.config.ConfigurationManager;
import ai.aitia.netplain.http.NetPlainHttpServer;

public class NetPlainServer {

	private final static Logger logger = LoggerFactory.getLogger(NetPlainServer.class);
	
	public static void main(final String[] args) {
		logger.info("NetPlain taking off...");
		
		ConfigurationManager.getInstance().loadConfigFile("src/main/resources/netplain.json");
		Configuration config = ConfigurationManager.getInstance().getCurrentConfig();
		
		if (config.isHttpEnabled()) {
			logger.info("HTTP is enabled");
			NetPlainHttpServer.init(config.getHttpPort(), config.getHttpBacklog());
			NetPlainHttpServer.start();			
		}
	}
}
