package sra;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedList;
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

public class Alice {
	public static void main(String[] args) throws IOException,
			NoSuchAlgorithmException, InvalidKeySpecException {

		CardStream cs = Networking.openSocket(args);
		RSAPrivateProfile key = new RSAPrivateProfile();

		Pool deck;

		System.out.println("Reading all encrypted cards...");
		deck = Pool.shuffleRemote(Actor.BOB, cs, 52);
		System.out.println("Encrypted cards read");
		System.out.println(deck);

		Pool bobHand = new Pool();
		for (int i = 0; i < 5; i++) {
			bobHand.add(deck.get(i));
		}
		Pool aliceHand = new Pool();
		for (int i = 5; i < 10; i++) {
			aliceHand.add(deck.get(i));
		}

		aliceHand.encrypt(Actor.ALICE, key);
		System.out.println("alice's hand: " + aliceHand);

		// This code assumes that Alice passes Bob her hand, then Bob shuffles
		// it.
		// There's nothing wrong with that, but it's not necessary.
		//
		// System.out.println("Sending hands");
		// aliceHand.writeAll(cs);
		// Pool newAliceHand = aliceHand.draw(Action.DECRYPT, Actor.BOB, 5, cs);
		// System.out.println("Recieved Alice's hand from Bob: "+newAliceHand);
		// newAliceHand.decrypt(Actor.ALICE, key);
		// System.out.println("Final hand:");
		// System.out.println(newAliceHand);
		// System.out.println("stack: "+newAliceHand.get(0).getTransactions());
		// System.out.println("Resolvable: "+newAliceHand.get(0).canResolve());

		// Here we do it card by card.
		for (Card c : aliceHand) {
			cs.writeCard(c);
			cs.readCard(c, Action.DECRYPT, Actor.BOB);
		}
		System.out.println("Decrypted by Bob: " + aliceHand);
		aliceHand.decrypt(Actor.ALICE, key);
		System.out.println("Final hand:");
		System.out.println(aliceHand);
		System.out.println("stack: " + aliceHand.get(0).getTransactions());
		System.out.println("Resolvable: " + aliceHand.get(0).canResolve());
	}
}
