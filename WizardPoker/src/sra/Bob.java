package sra;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import networking.Networking;

import com.wizard.poker.crypto.Crypto;
import com.wizard.poker.crypto.rsa.RSAPrivateProfile;

public class Bob {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		Random random = ThreadLocalRandom.current();
		
		int HANDSIZE = StandardPlayingCard.HANDSIZE;
		
		Socket socket = Networking.openSocket(args);
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		RSAPrivateProfile key = new RSAPrivateProfile();
		BigInteger[] encryptedCards = new BigInteger[StandardPlayingCard.DECKSIZE];
		for(int i = 0; i < StandardPlayingCard.DECKSIZE; i++) {
			encryptedCards[i] = Crypto.encrypt(key, BigInteger.valueOf(i));
		}
		
		for(int i = encryptedCards.length - 1; i > 0; i--) {
			int index = random.nextInt(i+1);
			BigInteger temp = encryptedCards[index];
			encryptedCards[index] = encryptedCards[i];
			encryptedCards[i] = temp;
		}
		out.writeObject(encryptedCards);
		
		Integer[] bobCardIds;
		try {
			bobCardIds = (Integer[]) in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		BigInteger[] bobCards = new BigInteger[HANDSIZE];
		for(int i = 0; i < HANDSIZE; i++) {
			bobCards[i] = Crypto.decrypt(key, encryptedCards[bobCardIds[i]]);
		}
		
		BigInteger[] aliceCards;
		
		try {
			aliceCards = (BigInteger[]) in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		
	}
}
