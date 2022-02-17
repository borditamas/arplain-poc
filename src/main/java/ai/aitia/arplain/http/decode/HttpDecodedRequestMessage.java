package ai.aitia.arplain.http.decode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.aitia.arplain.http.decode.exception.HttpDecodingException;
import ai.aitia.arplain.http.properties.HttpMethod;
import ai.aitia.arplain.http.properties.HttpStatus;
import ai.aitia.arplain.http.properties.HttpVersion;

public class HttpDecodedRequestMessage {
	
	private final static Logger logger = LoggerFactory.getLogger(HttpDecodedRequestMessage.class);

	private HttpMethod method;
	private String target;
	private HttpVersion bestCompatibleVersion;
	private String originalVersionLiteral;
	
	public HttpMethod getMethod() { return method; }
	public String getTarget() { return target; }
	public HttpVersion getBestCompatibleVersion() { return bestCompatibleVersion; }	
	public String getOriginalVersionLiteral() { return originalVersionLiteral; }
	
	public void parseMethod(final String methodStr) throws HttpDecodingException {
		for (final HttpMethod httpMethod : HttpMethod.values()) {
			if (methodStr.equals(httpMethod.name())) {
				this.method = httpMethod;
				logger.info("Method: {}", this.method.name());
				return;
			}
		}		
		throw new HttpDecodingException(HttpStatus.NOT_IMPLEMENTED_501);
	}
	
	public void parseTarget(final String target) throws HttpDecodingException {
		if (target == null || target.length() == 0) {
			throw new HttpDecodingException(HttpStatus.INTERNAL_SERVER_ERROR_500);
		}
		this.target = target;
		logger.info("Target: {}", this.target);
	}
	
	public void parseVersion(final String versionStr) throws HttpDecodingException {
		if (!HttpVersion.checkPattern(versionStr)) {
			throw new HttpDecodingException(HttpStatus.BAD_REQUEST_400);
			
		} else {
			this.originalVersionLiteral = versionStr;
			logger.info("Version original: {}", this.originalVersionLiteral);
			final HttpVersion compatibleVersion = HttpVersion.getBestCompatibleVersion(versionStr);
			if (compatibleVersion != null) {
				this.bestCompatibleVersion = compatibleVersion;
				logger.info("Version Compatible: {}", this.bestCompatibleVersion.literal);
			} else {
				throw new HttpDecodingException(HttpStatus.VERSION_NOT_SUPPORTED_505);
			}
		}
	}
}
