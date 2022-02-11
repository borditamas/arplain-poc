package ai.aitia.netplain.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import sun.net.httpserver.HttpServerImpl;

public class NetPlainHttpServer {
	
	private final static Logger logger = LoggerFactory.getLogger(NetPlainHttpServer.class);
	
	private static HttpServer httpServer;

	public static void init(final int port, final int backlog) {
		try {
			httpServer = HttpServerImpl.create(new InetSocketAddress(port), backlog);
			final HttpContext httpContext = httpServer.createContext("/");
			httpContext.setHandler(new NetPlainHttpHandler());
			
		} catch (final IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public static void start() {
		httpServer.start();
		logger.info("NetPlainServer listening HTTP on port: " + httpServer.getAddress().getPort());		
	}
}
