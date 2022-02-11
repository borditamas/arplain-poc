package ai.aitia.netplain.config.exception;

public class ConfiurationException extends RuntimeException {

	private static final long serialVersionUID = 48556569837449842L;

	public ConfiurationException() {
		super();
	}

	public ConfiurationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ConfiurationException(final String message) {
		super(message);
	}

	public ConfiurationException(final Throwable cause) {
		super(cause);
	}

}
