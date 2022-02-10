package ai.aitia.netplain;

import ai.aitia.netplain.config.Configuration;
import ai.aitia.netplain.config.ConfigurationManager;

public class HttpServer {

	public static void main(final String[] args) {
		System.out.println("Server strating...");
		
		ConfigurationManager.getInstance().loadConfigFile("src/main/resources/http.json");
		Configuration config = ConfigurationManager.getInstance().getCurrentConfig();
		
		System.out.println(config.getPort());
		System.out.println(config.getWebroot());
	}
}
