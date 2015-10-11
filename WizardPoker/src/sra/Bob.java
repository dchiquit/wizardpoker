package sra;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import networking.CardStream;
import networking.Networking;

import com.wizard.poker.api.Action;
import com.wizard.poker.api.Actor;
import com.wizard.poker.api.Card;
import com.wizard.poker.api.Pool;
import com.wizard.poker.crypto.Crypto;
import com.wizard.poker.crypto.rsa.RSAPrivateProfile;

public class Bob {
	public static void main(String[] args) throws IOException,
			NoSuchAlgorithmException, InvalidKeySpecException {

		CardStream cs = Networking.openSocket(args);
		RSAPrivateProfile key = new RSAPrivateProfile();
		Pool deck = Pool.defaultDeck();
		System.out.println(deck);

		System.out.println("Encrypting cards...");
		deck.shuffle(Actor.BOB, key, new Random());
		System.out.println("Encrypted");
		System.out.println(deck);
		System.out.println("Sending encrypted cards...");

		deck.writeAll(cs);
		System.out.println("Sent");

		System.out.println("Recieving Alice's hand...");

		// See comments in Alice.java
		// Pool aliceHand = deck.draw(Action.ENCRYPT, Actor.ALICE, 5, cs);
		// System.out.println(aliceHand);
		//
		// aliceHand.decrypt(Actor.BOB, key);
		// System.out.println("Decrypted: "+aliceHand);
		// System.out.println(aliceHand.get(0).getTransactions());
		//
		// aliceHand.writeAll(cs);

		for (int i = 0; i < 5; i++) {
			Card c = deck.draw(Action.ENCRYPT, Actor.ALICE, cs);
			c.localDecrypt(Actor.BOB, key);
			cs.writeCard(c);
		}

	}
}
