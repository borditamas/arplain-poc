package ai.aitia.netplain.core.http;

import java.io.IOException;
import java.io.InputStream;

public class HttpParser {
	
	public void parse(final InputStream inputStream) throws IOException {
		int byte_;
		while ((byte_ = inputStream.read()) >= 0) {
			System.out.print((char)byte_);
		}
	}
}
