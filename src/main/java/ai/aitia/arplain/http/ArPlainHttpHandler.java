package ai.aitia.arplain.http;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ai.aitia.arplain.http.endpoint.HttpEndpointMapper;
import ai.aitia.arplain.http.endpoint.HttpRequestHandler;
import ai.aitia.arplain.http.properties.HttpResponse;

public class ArPlainHttpHandler implements HttpHandler {

	@Override
	public void handle(final HttpExchange exchange) throws IOException {
		
		System.out.println(exchange.getProtocol());
		System.out.println(exchange.getRequestURI().getPath());
		
		//TODO Access Control filter
		
		HttpRequestHandler handler = HttpEndpointMapper.map(exchange.getRequestMethod(), exchange.getRequestURI().getPath());
		if (handler == null) {
			//TODO no endpoint send error response
		}
		HttpResponse response = handler.handle();
		
		//TODO  send response
	}
}
