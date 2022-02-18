package ai.aitia.arplain.http.decode;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.aitia.arplain.http.decode.exception.HttpDecodingException;
import ai.aitia.arplain.http.endpoint.HttpEndpointMapper;
import ai.aitia.arplain.http.properties.HttpMethod;
import ai.aitia.arplain.http.properties.HttpStatus;
import ai.aitia.arplain.http.properties.HttpVersion;

public class HttpDecodedRequestMessage {
	
	private final static Logger logger = LoggerFactory.getLogger(HttpDecodedRequestMessage.class);
	private final static char queryStart = '?';
	
	private HttpMethod method;
	private String originalTarget;
	private String targetWithoutQueryParams;
	private Map<String, List<String>> queryParams;
	private String path;
	private List<String> pathVars;
	private HttpVersion bestCompatibleVersion;
	private String originalVersionLiteral;
	
	public HttpMethod getMethod() { return method; }
	public String getOriginalTarget() { return originalTarget; }
	public String getTargetWithoutQueryParams() { return targetWithoutQueryParams; }
	public Map<String, List<String>> getQueryParams() { return queryParams; }
	public String getPath() { return path; }
	public List<String> getPathVars() { return pathVars; }
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
		this.originalTarget = URLDecoder.decode(target, StandardCharsets.US_ASCII);
		logger.info("Target: {}", this.originalTarget);
		
		final StringBuilder sb = new StringBuilder();
		boolean hasQuery = false;
		for (char ch : this.originalTarget.toCharArray()) {
			if (!hasQuery && ch == queryStart) {
				hasQuery = true;
				this.targetWithoutQueryParams = sb.toString();
				logger.info("Target w/o query: {}", this.targetWithoutQueryParams);
				sb.delete(0, sb.length());
			} else {
				sb.append(ch);
			}
		}
		if (hasQuery) {
			final Map<String, List<String>> extractedQueryParams = HttpEndpointMapper.extractQueryParams(sb.toString());
			if (extractedQueryParams == null) {
				throw new HttpDecodingException(HttpStatus.BAD_REQUEST_400);
			}
			this.queryParams = extractedQueryParams;
			logger.info("QueryParams extracted");
		}
		
		final String mappedPath = HttpEndpointMapper.mapPath(this.targetWithoutQueryParams);
		if (mappedPath == null) {
			throw new HttpDecodingException(HttpStatus.NOT_FOUND_404);
		}else if (!HttpEndpointMapper.methodIsAllowed(mappedPath, method)) {
			throw new HttpDecodingException(HttpStatus.METHOD_NOT_ALLOWED_405);
		}
		this.path = mappedPath;
		logger.info("Path: {}", this.path);
		
		this.pathVars = HttpEndpointMapper.extractPathVariables(this.path, this.targetWithoutQueryParams);
		logger.info("PathVariables extracted");
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
				throw new HttpDecodingException(HttpStatus.HTTP_VERSION_NOT_SUPPORTED_505);
			}
		}
	}
}
