package ai.aitia.arplain.http.endpoint;

import java.util.ArrayList;
import java.util.List;

public class HttpEndpointRegister {

	private static final List<HttpEndpoint> endpoints = new ArrayList<HttpEndpoint>();
	
	public static void add(final HttpEndpoint endpoint) {
		endpoints.add(endpoint);
	}
	
	public static void flush() {
		for (HttpEndpoint endpoint : endpoints) {
			HttpEndpointMapper.put(endpoint.getKey(), endpoint);
		}
	}
}
