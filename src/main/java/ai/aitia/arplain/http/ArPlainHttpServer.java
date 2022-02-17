package ai.aitia.arplain.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.aitia.arplain.http.endpoint.HttpEndpoint;
import ai.aitia.arplain.http.endpoint.HttpEndpointRegister;

public class ArPlainHttpServer extends Thread {
	
	private final static Logger logger = LoggerFactory.getLogger(ArPlainHttpServer.class);
	
	private final int port;
	private final ServerSocket serverSocket;
	
	public ArPlainHttpServer(final int port, final int backlog) throws IOException {
		this.port = port;
		this.serverSocket = new ServerSocket(this.port);
	}
	
	public void registerHttpEndpoint(final HttpEndpoint endpoint) {
		HttpEndpointRegister.add(endpoint);
	}
	
	@Override
	public void run() {
		HttpEndpointRegister.flush();
		
		try {
			while (this.serverSocket.isBound() && !this.serverSocket.isClosed()) {
				final Socket socket = this.serverSocket.accept();
				logger.info(" * Connection accepted: " + socket.getInetAddress());
				
				final ArPlainHttpHandler handler = new ArPlainHttpHandler(socket);
				handler.start();
			}
			
		} catch (final IOException ex) {
			logger.error("Problem with setting socket", ex);
			
		} finally {
			if (this.serverSocket != null) {
				try {
					this.serverSocket.close();
				} catch (final IOException ex) { logger.info("server socket closed", ex); }
			}
		}
		logger.info("ArPlain listening HTTP on port: " + port);		
	}
}
