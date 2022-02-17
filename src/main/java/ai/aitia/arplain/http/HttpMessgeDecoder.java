package ai.aitia.arplain.http;

import java.io.InputStream;

import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectDecoder;

public class HttpMessgeDecoder extends HttpObjectDecoder {
	
	public HttpMessage decode(final InputStream is) {
		//super.decode(ctx, buffer, out);
		return null;
	}

	@Override
	protected boolean isDecodingRequest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected HttpMessage createMessage(String[] initialLine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected HttpMessage createInvalidMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
