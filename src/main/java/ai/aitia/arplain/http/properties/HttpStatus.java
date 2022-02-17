package ai.aitia.arplain.http.properties;

public enum HttpStatus {

	OK_200(200),
	BAD_REQUEST_400(400),
	NOT_FOUND_404(404),
	METHOD_NOT_ALLOWED_405(405),
	URI_TOO_LONG_414(414),
	INTERNAL_SERVER_ERROR_500(500),
	NOT_IMPLEMENTED_501(501),
	VERSION_NOT_SUPPORTED_505(505);

	public final int code;
	
	HttpStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
