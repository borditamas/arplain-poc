package ai.aitia.arplain.http.properties;

public class HttpResponse {

	private final HttpStatus status;
	private final Object payload;
	
	public HttpResponse(HttpStatus status) {
		this.status = status;
		this.payload = null;
	}
	
	public HttpResponse(HttpStatus status, Object payload) {
		this.status = status;
		this.payload = payload;
	}

	public HttpStatus getStatus() { return status; }
	public Object getPayload() { return payload; }
}
