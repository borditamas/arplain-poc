package ai.aitia.netplain.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerListenerThread extends Thread {
	
	//=================================================================================================
	// members
	
	private final static Log logger = LogFactory.getLog(ServerListenerThread.class);

	private final int port;
	private final String webroot;
	private final ServerSocket serverSocket;
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public ServerListenerThread(final int port, final String webroot) throws IOException {
		this.port = port;
		this.webroot = webroot;
		this.serverSocket = new ServerSocket(this.port);
	}

	//-------------------------------------------------------------------------------------------------
	@Override
	public void run() {
		try {
			while (this.serverSocket.isBound() && !this.serverSocket.isClosed()) {
				final Socket socket = this.serverSocket.accept();
				logger.info(" * Connection accepted: " + socket.getInetAddress());
				
				final HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
				workerThread.start();
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
	}	
}
