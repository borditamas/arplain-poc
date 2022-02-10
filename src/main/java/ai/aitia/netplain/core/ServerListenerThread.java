package ai.aitia.netplain.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerListenerThread extends Thread {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

	private final int port;
	private final String webroot;
	private final ServerSocket serverSocket;
	
	
	public ServerListenerThread(final int port, final String webroot) throws IOException {
		this.port = port;
		this.webroot = webroot;
		this.serverSocket = new ServerSocket(this.port);
	}

	@Override
	public void run() {
		try {
			while (serverSocket.isBound() && !serverSocket.isClosed()) {
				final Socket socket = serverSocket.accept();
				LOGGER.info(" * Connection accepted: " + socket.getInetAddress());
				
				final HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
				workerThread.start();
			}
			
		} catch (final IOException ex) {
			LOGGER.error("Problem with setting socket", ex);
			
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (final IOException ex) { LOGGER.info("server socket closed", ex); }
			}
		}
	}	
}
