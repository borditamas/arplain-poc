package ai.aitia.netplain.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import ai.aitia.netplain.util.JsonUtil;

public class ConfigurationManager {

	//=================================================================================================
	// members
	
	private static ConfigurationManager manager;
	private static Configuration currentConfig;

	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public static ConfigurationManager getInstance() {
		if (manager == null) {
			manager = new ConfigurationManager();
		}
		return manager;
	}
	
	//-------------------------------------------------------------------------------------------------
	public void loadConfigFile(final String filePath) {
		FileReader fileReader;
		try {
			fileReader = new FileReader(filePath);
		} catch (final FileNotFoundException ex) {
			throw new HttpConfiurationException(ex);
		}
		final StringBuffer sb = new StringBuffer();
		int i;
		try {
			while ((i = fileReader.read()) != -1) {
				sb.append((char)i);
			}
		} catch (final IOException ex) {
			throw new HttpConfiurationException(ex);
		}
		JsonNode conf;
		try {
			conf = JsonUtil.parse(sb.toString());
		} catch (final IOException ex) {
			throw new HttpConfiurationException("Error parsing the configuration file.", ex);
		}
		try {
			currentConfig = JsonUtil.fromJson(conf, Configuration.class);
		} catch (final JsonProcessingException ex) {
			throw new HttpConfiurationException("Error parsing the configuration file, internal.", ex);
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	public Configuration getCurrentConfig() {
		if (currentConfig == null) {
			throw new HttpConfiurationException("No current configuration set");
		}
		return currentConfig;
	}
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	private ConfigurationManager() {}
}
