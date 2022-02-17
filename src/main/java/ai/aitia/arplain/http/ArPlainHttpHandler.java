package ai.aitia.arplain.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.http.HttpMessage;

public class ArPlainHttpHandler extends Thread {
	
	private final static Logger logger = LoggerFactory.getLogger(ArPlainHttpHandler.class);

	private final Socket socket;
	private final HttpMessgeDecoder decoder = new HttpMessgeDecoder();
	
	public ArPlainHttpHandler(final Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			inputStream= this.socket.getInputStream();
			outputStream = this.socket.getOutputStream();
			
			final HttpMessage message = decoder.decode(inputStream);
			
			//TODO Access Control Filter
			
			//TODO map to endpoint
			
			//TODO response
			
			logger.info(" Http meassage processing finished");
			
		} catch (final IOException ex) {
			logger.error("Problem with commnication", ex);
			
		} finally {
			close(inputStream, outputStream);
		}
	}
	
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
