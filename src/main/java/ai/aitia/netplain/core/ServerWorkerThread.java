package ai.aitia.netplain.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.aitia.netplain.core.http.HttpParser;

//https://hc.apache.org/httpcomponents-core-5.1.x/	
public class ServerWorkerThread extends Thread {
	
	//=================================================================================================
	// members
	
	private final static Logger logger = LoggerFactory.getLogger(ServerWorkerThread.class);
	
	private final Socket socket;
	private final HttpParser parser = new HttpParser();
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public ServerWorkerThread(final Socket socket) {
		this.socket = socket;
	}

	//-------------------------------------------------------------------------------------------------
	@Override
	public void run() {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			inputStream= this.socket.getInputStream();
			outputStream = this.socket.getOutputStream();
			
			parser.parse(inputStream);
			
			logger.info(" * Connection processing finished");
			
		} catch (final IOException ex) {
			logger.error("Problem with commnication", ex);
			
		} finally {
			close(inputStream, outputStream);
		}
	}
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	private void close(final InputStream inputStream, final OutputStream outputStream) {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (final IOException ex) { logger.error("Problem with closing inputStream", ex); }				
		}
		if (outputStream !=  null) {
			try {
				outputStream.close();
			} catch (final IOException ex) { logger.error("Problem with closing outputStream", ex); }				
		}
		if (this.socket != null) {
			try {
				this.socket.close();
			} catch (final IOException ex) { logger.error("Problem with closing socket", ex); }							
		}
	}
}
