package ai.aitia.arplain.http.decode.exception;

import ai.aitia.arplain.http.properties.HttpStatus;

public class HttpDecodingException extends Exception {
	
	private static final long serialVersionUID = -2402516219134797797L;
	
	private final HttpStatus status;

	public HttpDecodingException(final HttpStatus status) {
		this.status = status;
	}

	public HttpStatus getStatus() { return status; }	
}
