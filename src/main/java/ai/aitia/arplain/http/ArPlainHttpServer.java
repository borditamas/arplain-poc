package ai.aitia.arplain.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import ai.aitia.arplain.http.endpoint.HttpEndpoint;
import ai.aitia.arplain.http.endpoint.HttpEndpointRegister;
import sun.net.httpserver.HttpServerImpl;

public class ArPlainHttpServer {
	
	private final static Logger logger = LoggerFactory.getLogger(ArPlainHttpServer.class);
	
	private static HttpServer httpServer;

	public static void init(final int port, final int backlog) {
		try {
			httpServer = HttpServerImpl.create(new InetSocketAddress(port), backlog);
			final HttpContext httpContext = httpServer.createContext("/");
			httpContext.setHandler(new ArPlainHttpHandler());
			
		} catch (final IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public static void registerHttpEndpoint(final HttpEndpoint endpoint) {
		HttpEndpointRegister.add(endpoint);
	}
	
	public static void start() {
		HttpEndpointRegister.flush();
		httpServer.start();
		logger.info("ArPlain listening HTTP on port: " + httpServer.getAddress().getPort());		
	}
}
