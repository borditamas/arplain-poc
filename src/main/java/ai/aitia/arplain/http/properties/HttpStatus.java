package ai.aitia.arplain.http.properties;

public enum HttpStatus {

	OK_200(200, "OK"),
	BAD_REQUEST_400(400, "Bad Request"),
	NOT_FOUND_404(404, "Not Found"),
	METHOD_NOT_ALLOWED_405(405, "Method Not Allowed"),
	URI_TOO_LONG_414(414, "URI Too Long"),
	INTERNAL_SERVER_ERROR_500(500, "Internal Server Error"),
	NOT_IMPLEMENTED_501(501, "Not Implemented"),
	HTTP_VERSION_NOT_SUPPORTED_505(505, "HTTP Version Not Supported");

	public final int code;
	public final String msg;
	
	HttpStatus(final int code, final String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}
}
