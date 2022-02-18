package ai.aitia.arplain.http.endpoint;

import ai.aitia.arplain.http.properties.HttpEndpointProperties;
import ai.aitia.arplain.http.properties.HttpMethod;
import ai.aitia.arplain.http.properties.HttpPathVariableType;
import ai.aitia.arplain.http.properties.HttpQueryParam;

public abstract class HttpEndpoint implements HttpRequestHandler {
	
	private final HttpEndpointProperties props = new HttpEndpointProperties();

	public HttpMethod getMethod() {
		return this.props.getMethod();
	}
	
	public String getPath() {
		return this.props.getPath();
	}
	
	public void setMethod(HttpMethod method) {
		this.props.setMethod(method);
	}
	
	public void setPath(String path) {
		this.props.setPath(path);
	}
	
	public void setPayloadType(Class<?> payloadType) {
		this.props.setPayloadType(payloadType);
	}
	
	public void addPathVariableType(final HttpPathVariableType pathVariableType) {
		this.props.addPathVariableType(pathVariableType);
	}
	
	public void addQueryParameter(final HttpQueryParam queryParam) {
		this.props.addQueryParameter(queryParam);
	}
}
