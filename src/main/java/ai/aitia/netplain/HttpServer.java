package ai.aitia.netplain;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import ai.aitia.netplain.config.Configuration;
import ai.aitia.netplain.config.ConfigurationManager;

public class HttpServer {

	public static void main(final String[] args) {
		System.out.println("Server strating...");
		
		ConfigurationManager.getInstance().loadConfigFile("src/main/resources/http.json");
		Configuration config = ConfigurationManager.getInstance().getCurrentConfig();
		
		System.out.println("Using port: " + config.getPort());
		System.out.println("Using webroot: " + config.getWebroot());
		
		try {
			final ServerSocket serverSocket = new ServerSocket(config.getPort());
			Socket socket = serverSocket.accept();
			
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			//TODO read
			
			String html = "<html><head><title>NetPlain POC</title></head><body><h1>This page was served by NetPlain from Aitia</h1></body></html>";
			
			final String CRLF = "\n\r";
			
			String response = 
					"HTTP/1.1 200 OK" + CRLF + //Status Line :
					"Content-Length: " + html.getBytes().length + CRLF + //Header
						CRLF +
						html +
						CRLF + CRLF;
			
			outputStream.write(response.getBytes());
			
			inputStream.close();
			outputStream.close();
			socket.close();
			serverSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
