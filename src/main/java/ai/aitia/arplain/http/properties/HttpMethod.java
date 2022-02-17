package ai.aitia.arplain.http.properties;

public enum HttpMethod {

	GET, HEAD, POST, PUT, DELETE;
	
	public static final int MAX_LENGTH;
	
	static {
		int tempMaxValue = -1;
		for (HttpMethod method : HttpMethod.values()) {
			if (method.name().length() > tempMaxValue) {
				tempMaxValue = method.name().length();
			}
		}
		MAX_LENGTH = tempMaxValue;
	}
}
