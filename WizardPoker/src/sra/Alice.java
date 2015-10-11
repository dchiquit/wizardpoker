package sra;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import networking.Networking;

import com.wizard.poker.crypto.Crypto;
import com.wizard.poker.crypto.rsa.RSAPrivateProfile;

public class Alice {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		Random random = ThreadLocalRandom.current();
		
		Socket socket = Networking.openSocket(args);
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		RSAPrivateProfile key = new RSAPrivateProfile();
		
		int HANDSIZE = StandardPlayingCard.HANDSIZE;
		
		BigInteger[] encryptedCards;
		
		try {
			encryptedCards = (BigInteger[]) in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		
		LinkedList<Integer> cardIds = new LinkedList<Integer>();
		
		BigInteger[] aliceCards = new BigInteger[HANDSIZE];
		Integer[] aliceCardIds = new Integer[HANDSIZE];
		BigInteger[] bobCards = new BigInteger[HANDSIZE];
		Integer[] bobCardIds = new Integer[HANDSIZE];
		
		for(int i = 0; i < StandardPlayingCard.DECKSIZE; i++) {
			cardIds.add(i);
		}
		
		for(int iteration = 0; iteration < HANDSIZE; iteration++) {
			int pos = random.nextInt(cardIds.size());
			int id = cardIds.get(pos);
			
			bobCardIds[iteration] = id;
			bobCards[iteration] = encryptedCards[id];
			cardIds.remove(id);
		}
		
		out.writeObject(bobCardIds);
		
		for(int iteration = 0; iteration < HANDSIZE; iteration++) {
			int pos = random.nextInt(cardIds.size());
			int id = cardIds.get(pos);
			
			aliceCardIds[iteration] = id;
			aliceCards[iteration] = Crypto.encrypt(key, encryptedCards[id]);
			cardIds.remove(id);
		}
		
		out.writeObject(aliceCards);
	}
}
