package ai.aitia.arplain.config;

public class Configuration {

	private boolean httpEnabled;
	private int httpPort;
	private int httpBacklog;
	
	public int getHttpPort() { return httpPort; }
	public void setHttpPort(final int httpPort) { this.httpPort = httpPort; }
	
	public int getHttpBacklog() { return httpBacklog; }
	public void setHttpBacklog(final int httpBacklog) { this.httpBacklog = httpBacklog; }
	
	public boolean isHttpEnabled() { return httpEnabled; }
	public void setHttpEnabled(final boolean httpEnabled) { this.httpEnabled = httpEnabled; }
}
