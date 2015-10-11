package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Networking {
	public static final int PORT = 4344;

	public static CardStream openSocket(String[] args) throws IOException {
		if (args.length > 0) {
			return startClient(args[0]);
		} else {
			return startServer();
		}
	}

	public static CardStream startServer() throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		Socket socket = serverSocket.accept();
		serverSocket.close();
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		return new CardStream(ois, oos);
	}

	public static CardStream startClient(String address) throws IOException {
		InetAddress ipAddress = InetAddress.getByName(address);
		Socket socket = new Socket(ipAddress, PORT);
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		return new CardStream(ois, oos);
	}
}
