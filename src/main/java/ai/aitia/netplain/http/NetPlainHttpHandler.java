package ai.aitia.netplain.http;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class NetPlainHttpHandler implements HttpHandler {

	@Override
	public void handle(final HttpExchange exchange) throws IOException {
		
		System.out.println(exchange.getProtocol());
		System.out.println(exchange.getRequestURI().getPath());		
	}

}
