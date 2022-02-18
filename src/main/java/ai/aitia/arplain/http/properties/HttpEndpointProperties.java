package ai.aitia.arplain.http.properties;

import java.util.ArrayList;
import java.util.List;

public class HttpEndpointProperties {

	private HttpMethod method;
	private String path = "/";
	private final List<HttpPathVariableType> pathVariables = new ArrayList<HttpPathVariableType>();
	private final List<HttpQueryParam> queryParameters = new ArrayList<HttpQueryParam>();
	private Class<?> payloadType;
	
	public HttpMethod getMethod() { return method; }
	public String getPath() { return path; }
	public Class<?> getPayloadType() { return payloadType; }
	public List<HttpPathVariableType> getPathVariables() { return pathVariables; }
	public List<HttpQueryParam> getQueryParameters() { return queryParameters; }
	
	public void setMethod(HttpMethod method) { this.method = method; }	
	public void setPath(String path) { this.path = path; }	
	public void setPayloadType(Class<?> payloadType) { this.payloadType = payloadType; }
	
	public void addPathVariableType(final HttpPathVariableType pathVariableType) {
		this.pathVariables.add(pathVariableType);
	}
	
	public void addQueryParameter(final HttpQueryParam queryParam) {
		this.queryParameters.add(queryParam);
	}
}
