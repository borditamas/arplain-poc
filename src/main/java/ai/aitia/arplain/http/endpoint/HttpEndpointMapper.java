package ai.aitia.arplain.http.endpoint;

import java.util.HashMap;
import java.util.Map;

public class HttpEndpointMapper {
	
	private static final Map<String,HttpEndpoint> MAP = new HashMap<String,HttpEndpoint>();
	
	public static void put(final String key, final HttpEndpoint endpoint) {
		if (!(endpoint instanceof HttpRequestHandler)) {
			// TODO throw
		}		
		//TODO throw if already contains
		MAP.put(key, endpoint);
	}
	
	public static HttpRequestHandler map(final String method, final String path) {
		return MAP.get(HttpEndpointMapper.defineEndpointKey(method, path));
	}
	
	public static String defineEndpointKey(final String method, final String path) {
		final StringBuilder sb = new StringBuilder(method.toUpperCase().trim());
		sb.append(' ');
		sb.append(path.trim());
		return sb.toString();
	}
}
