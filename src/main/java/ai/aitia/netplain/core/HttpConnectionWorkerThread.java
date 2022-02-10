package ai.aitia.netplain.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnectionWorkerThread extends Thread {
	
	//=================================================================================================
	// members
	
	private final static Logger logger = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
	
	private final Socket socket;
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public HttpConnectionWorkerThread(final Socket socket) {
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
			
			//TODO read
			
			final String html = "<html><head><title>NetPlain POC</title></head><body><h1>This page was served by NetPlain from Aitia</h1></body></html>";
			
			final String CRLF = "\n\r";
			
			final String response = 
					"HTTP/1.1 200 OK" + CRLF + //Status Line :
					"Content-Length: " + html.getBytes().length + CRLF + //Header
					CRLF +
					html +
					CRLF + CRLF;
			
			outputStream.write(response.getBytes());
			
			logger.info(" * Connection processing finished");
			
		} catch (final IOException ex) {
			logger.error("Problem with commnication", ex);
			
		} finally {
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
}
