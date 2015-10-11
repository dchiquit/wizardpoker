package sra;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import networking.Networking;

import com.wizard.poker.crypto.Crypto;
import com.wizard.poker.crypto.rsa.RSAPrivateProfile;

public class Bob {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		Socket socket = Networking.openSocket(args);
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		RSAPrivateProfile key = new RSAPrivateProfile();
		BigInteger[] encryptedCards = new BigInteger[StandardPlayingCard.DECKSIZE];
		for(int i = 0; i < StandardPlayingCard.DECKSIZE; i++) {
			encryptedCards[i] = Crypto.encrypt(key, BigInteger.valueOf(i));
		}
		out.writeObject(encryptedCards);
	}
}
