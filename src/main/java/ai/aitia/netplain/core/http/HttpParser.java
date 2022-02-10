package ai.aitia.netplain.core.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.config.Http1Config;
import org.apache.hc.core5.http.impl.io.DefaultHttpRequestParser;
import org.apache.hc.core5.http.impl.io.SessionInputBufferImpl;
import org.apache.hc.core5.http.io.SessionInputBuffer;

//https://hc.apache.org/httpcomponents-client-5.1.x/index.html

public class HttpParser {
	
	private final DefaultHttpRequestParser parser = new DefaultHttpRequestParser();
	private final SessionInputBuffer buffer = new SessionInputBufferImpl(Http1Config.DEFAULT.getBufferSize());

	public ClassicHttpRequest parseRequest(final InputStream inputStream) throws IOException, HttpException {
		return parser.parse(buffer, inputStream);
	}
}
