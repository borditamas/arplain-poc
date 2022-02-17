package ai.aitia.arplain.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.aitia.arplain.http.decode.HttpDecodedRequestMessage;
import ai.aitia.arplain.http.decode.HttpMessageDecoder;
import ai.aitia.arplain.http.decode.exception.HttpDecodingException;
import ai.aitia.arplain.http.endpoint.HttpEndpointMapper;
import ai.aitia.arplain.http.endpoint.HttpRequestHandler;
import ai.aitia.arplain.http.properties.HttpResponse;

public class ArPlainHttpSocketWorker extends Thread {
	
	private final static Logger logger = LoggerFactory.getLogger(ArPlainHttpSocketWorker.class);

	private final Socket socket;
	private final HttpMessageDecoder decoder = new HttpMessageDecoder();
	
	public ArPlainHttpSocketWorker(final Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			inputStream= this.socket.getInputStream();
			outputStream = this.socket.getOutputStream();
			
			//Decode
			final HttpDecodedRequestMessage message;
			try {
				message = decoder.decode(inputStream);				
			} catch (final HttpDecodingException ex) {
				// TODO: handle exception
				return;
			}
			
			if (message == null) {
				return; // means no message on the socket
			}
			
			//TODO Access Control Filter
			
			final HttpRequestHandler handler = HttpEndpointMapper.findHandler(message.getMethod(), message.getPath());
			final HttpResponse response = handler.handle();
			
			//TODO encode response
			
			//TODO write output stream
			
			logger.info(" Http meassage processing finished");
			
		} catch (final Exception ex) {
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
