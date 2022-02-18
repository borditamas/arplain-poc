package ai.aitia.arplain.http.endpoint;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ai.aitia.arplain.http.properties.HttpMethod;

public class HttpEndpointMapper {
	
	private static final char keyDelimiter = ':';
	
	private static final Map<String,HttpEndpoint> mapToEndpoints = new HashMap<>();
	private static final Map<String,Set<HttpMethod>> mapToMethods = new HashMap<>();
	
	public static void put(final HttpEndpoint endpoint) {
		if (!(endpoint instanceof HttpRequestHandler)) {
			// TODO throw
		}		
		//TODO throw if already contains
		mapToEndpoints.put(defineEndpointKey(endpoint.getMethod(), endpoint.getPath()), endpoint);
		mapToMethods.putIfAbsent(endpoint.getPath(), new HashSet<>());
		mapToMethods.get(endpoint.getPath()).add(endpoint.getMethod());
	}
	
	public static String mapPath(final String targetWithoutQuery) {
		final char[] targetArray = targetWithoutQuery.toCharArray();
		String bestFit = "";
		for (final String path : mapToMethods.keySet()) {
			if (path.length() <= targetArray.length) {
				final char[] pathArray = path.toCharArray();
				for (int i = 0; i < pathArray.length; i++) {
					if (pathArray[i] != targetArray[i]) {
						break;
					}
				}
				if (bestFit.length() < path.length()) {
					bestFit = path;
				}
			}
		}

		if (bestFit.length() == 0) {
			return null;
		}
		
		return bestFit;
	}
	
	public static boolean methodIsAllowed(final String path, final HttpMethod method) {
		if (mapToMethods.containsKey(path)) {
			return mapToMethods.get(path).contains(method);
		}		
		return false;
	}
	
	public static HttpRequestHandler findHandler(final HttpMethod method, final String path) {
		return mapToEndpoints.get(defineEndpointKey(method, path));
	}
	
	private static String defineEndpointKey(final HttpMethod method, final String path) {
		final StringBuilder sb = new StringBuilder(method.name());
		sb.append(keyDelimiter);
		sb.append(path.trim());
		return sb.toString();
	}
}
