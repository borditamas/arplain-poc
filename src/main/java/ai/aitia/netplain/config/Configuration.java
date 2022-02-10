package ai.aitia.netplain.config;

public class Configuration {

	//=================================================================================================
	// members
	
	private int port;
	private String webroot;
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public int getPort() { return port; }
	public void setPort(final int port) { this.port = port; }
	
	//-------------------------------------------------------------------------------------------------
	public String getWebroot() { return webroot; }
	public void setWebroot(final String webroot) { this.webroot = webroot; }
}
