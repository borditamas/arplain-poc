package ai.aitia.arplain.http.properties;

import java.util.ArrayList;
import java.util.List;

import ai.aitia.arplain.http.endpoint.HttpEndpointMapper;

public class HttpEndpointProperties {

	private String key = "";
	
	private HttpMethod method;
	private String path = "/";
	private final List<HttpPathVariableType> pathVariables = new ArrayList<HttpPathVariableType>();
	private final List<HttpQueryParam> queryParameters = new ArrayList<HttpQueryParam>();
	private Class<?> payloadType;
	
	public String getKey() { return key; }
	public HttpMethod getMethod() { return method; }
	public String getPath() { return path; }
	public Class<?> getPayloadType() { return payloadType; }
	public List<HttpPathVariableType> getPathVariables() { return pathVariables; }
	public List<HttpQueryParam> getQueryParameters() { return queryParameters; }
	
	public void setMethod(HttpMethod method) {
		this.method = method;
		updateKey();
	}
	
	public void setPath(String path) {
		this.path = path;
		updateKey();
	}
	
	public void setPayloadType(Class<?> payloadType) {
		this.payloadType = payloadType;
	}
	
	public void addPathVariableType(final HttpPathVariableType pathVariableType) {
		this.pathVariables.add(pathVariableType);
	}
	
	public void addQueryParameter(final HttpQueryParam queryParam) {
		this.queryParameters.add(queryParam);
	}
	
	private void updateKey() {		
		this.key = HttpEndpointMapper.defineEndpointKey(this.method, this.path);
	}
}
