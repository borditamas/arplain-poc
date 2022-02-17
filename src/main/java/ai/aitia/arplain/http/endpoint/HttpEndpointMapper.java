package ai.aitia.arplain.http.endpoint;

import java.util.HashMap;
import java.util.Map;

import ai.aitia.arplain.http.properties.HttpMethod;

public class HttpEndpointMapper {
	
	private static final char keyDelimiter = ':';
	private static final Map<String,HttpEndpoint> map = new HashMap<>();
	
	public static void put(final String key, final HttpEndpoint endpoint) {
		if (!(endpoint instanceof HttpRequestHandler)) {
			// TODO throw
		}		
		//TODO throw if already contains
		map.put(key, endpoint);
	}
	
	public static String mapPath(final HttpMethod method, final String targetWithoutQuery) {
		final char[] targetArray = HttpEndpointMapper.defineEndpointKey(method, targetWithoutQuery).toCharArray();
		String bestFit = "";
		for (final String key : map.keySet()) {
			if (key.length() <= targetArray.length) {
				final char[] keyArray = key.toCharArray();
				for (int i = 0; i < keyArray.length; i++) {
					if (keyArray[i] != targetArray[i]) {
						break;
					}
				}
				if (bestFit.length() < key.length()) {
					bestFit = key;
				}
			}
		}

		if (bestFit.length() == 0) {
			return null;
		}
		
		return bestFit.substring(method.name().length() + 1);
	}
	
	public static HttpRequestHandler findHandler(final HttpMethod method, final String path) {
		return map.get(HttpEndpointMapper.defineEndpointKey(method, path));
	}
	
	public static String defineEndpointKey(final HttpMethod method, final String path) {
		final StringBuilder sb = new StringBuilder(method.name());
		sb.append(keyDelimiter);
		sb.append(path.trim());
		return sb.toString();
	}
}
