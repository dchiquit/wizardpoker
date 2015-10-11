package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Networking {
	public static final int PORT = 4342;
	
	public static Socket openSocket(String[] args) throws IOException {
		if(args.length > 0) {
			return startClient(args[0]);
		}
		else {
			return startServer();
		}
	}
	
	public static Socket startServer() throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		Socket socket = serverSocket.accept();
		serverSocket.close();
		return socket;
	}
	
	public static Socket startClient(String address) throws IOException {
		InetAddress ipAddress = InetAddress.getByName(address);
		return new Socket(ipAddress, PORT);
	}
	
	public static void main(String[] args) {
		try {
			Socket socket = openSocket(args);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), 
	                 true);
			Reader in = new BufferedReader(new InputStreamReader(
	                socket.getInputStream()));
			out.write("Hello, Hello, Hello!");
			while(true) {
				System.out.print(in.read());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
