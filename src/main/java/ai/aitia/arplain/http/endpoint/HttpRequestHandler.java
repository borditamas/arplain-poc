package ai.aitia.arplain.http.endpoint;

import ai.aitia.arplain.http.properties.HttpResponse;

public interface HttpRequestHandler {

	public HttpResponse handle();
}
