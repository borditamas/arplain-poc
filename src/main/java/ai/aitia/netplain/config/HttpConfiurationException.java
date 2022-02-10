package ai.aitia.netplain.config;

public class HttpConfiurationException extends RuntimeException {

	private static final long serialVersionUID = 48556569837449842L;

	public HttpConfiurationException() {
		super();
	}

	public HttpConfiurationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public HttpConfiurationException(final String message) {
		super(message);
	}

	public HttpConfiurationException(final Throwable cause) {
		super(cause);
	}

}
