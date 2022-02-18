package ai.aitia.arplain.http.decode;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.aitia.arplain.http.decode.exception.HttpDecodingException;
import ai.aitia.arplain.http.properties.HttpMethod;
import ai.aitia.arplain.http.properties.HttpStatus;

public class HttpMessageDecoder {
	
	private static final int SP = 0x20;
	private static final int CR = 0x0D;
	private static final int LF = 0x0A;
	
	private final static Logger logger = LoggerFactory.getLogger(HttpMessageDecoder.class);
	
	public HttpDecodedRequestMessage decode(final InputStream in) throws IOException, HttpDecodingException {
		try(final InputStreamReader reader = new InputStreamReader(in, StandardCharsets.US_ASCII)) {
			final HttpDecodedRequestMessage decodedInbound = new HttpDecodedRequestMessage();
			
			parseRequestLine(reader, decodedInbound);
			if (decodedInbound.getMethod() == null) {
				logger.info("No incoming HTTP message has arrived");
				return null;
			}
			
			parseHeaders(reader, decodedInbound);			
			
			parseBody(in, decodedInbound);				
					
			
			logger.info("Incoming HTTP message decoded");
			return decodedInbound;			
		}
	}
	
	private void parseRequestLine(final InputStreamReader reader, final HttpDecodedRequestMessage request) throws IOException, HttpDecodingException {
		final StringBuilder processingDataBuffer = new StringBuilder();
		
		boolean methodDecoded = false;
		boolean requestTargetDecoded = false;
		
		int byte_;
		while ((byte_ = reader.read()) >= 0) {
			if (byte_ == CR) {
				byte_ = reader.read();
				if (byte_ == LF) {
					// VERSION
					if (!methodDecoded || !requestTargetDecoded) {
						throw new HttpDecodingException(HttpStatus.BAD_REQUEST_400);
					}
					request.parseVersion(processingDataBuffer.toString());
					return;
				} else {
					throw new HttpDecodingException(HttpStatus.BAD_REQUEST_400);
				}
			}
			
			if (byte_ == SP) {
				if (!methodDecoded) {
					// METHOD
					request.parseMethod(processingDataBuffer.toString());
					methodDecoded = true;
				} else if(!requestTargetDecoded) {
					// TARGET
					request.parseTarget(processingDataBuffer.toString());
					requestTargetDecoded = true;
				} else {
					throw new HttpDecodingException(HttpStatus.BAD_REQUEST_400);
				}
				processingDataBuffer.delete(0, processingDataBuffer.length());
				
			} else {
				processingDataBuffer.append((char)byte_);
				if (!methodDecoded) {
					if (processingDataBuffer.length() > HttpMethod.MAX_LENGTH) {
						throw new HttpDecodingException(HttpStatus.NOT_IMPLEMENTED_501);
					}
				}
			}
		}
	}
	
	private void parseHeaders(final InputStreamReader reader, final HttpDecodedRequestMessage request) throws IOException, HttpDecodingException {
		final StringBuilder processingDataBuffer = new StringBuilder();
		final List<String> rawHeaders = new ArrayList<>();
		
		int byte_;
		while ((byte_ = reader.read()) >= 0) {
			if (byte_ == CR) {
				byte_ = reader.read();
				if (byte_ == LF) {
					byte_ = reader.read();
					if (byte_ == CR) {
						byte_ = reader.read();
						if (byte_ == LF) {
							//End of headers							
							request.parseHeaders(rawHeaders);
							return;
						} else {
							throw new HttpDecodingException(HttpStatus.BAD_REQUEST_400);
						}
					} else {
						//new header begins
						rawHeaders.add(processingDataBuffer.toString());
						System.out.println(processingDataBuffer.toString());
						processingDataBuffer.delete(0, processingDataBuffer.length());
					}
				} else {
					throw new HttpDecodingException(HttpStatus.BAD_REQUEST_400);
				}
			}
			
			processingDataBuffer.append((char)byte_);
		}		
	}
	
	private void parseBody(final InputStream in, final HttpDecodedRequestMessage request) throws IOException {
		//TODO read only until according to the content length
		request.setBodyByteArray(in.readAllBytes());
	}
}
