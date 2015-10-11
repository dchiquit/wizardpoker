package test;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import com.wizard.poker.api.Action;
import com.wizard.poker.api.Actor;
import com.wizard.poker.api.Card;
import com.wizard.poker.crypto.Crypto;
import com.wizard.poker.crypto.rsa.RSAPrivateProfile;
import com.wizard.poker.profile.PrivateProfile;

public class Test {
	public static void main(String[] args) throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		PrivateProfile a = new RSAPrivateProfile();
		PrivateProfile b = new RSAPrivateProfile();
		Random r = new Random();
		BigInteger initial = BigInteger.valueOf(42);
		System.out.println("initial "+Card.toString(initial));
		Card c = new Card(Action.ENCRYPT, Actor.ALICE, Crypto.encrypt(a,
				initial));
		System.out.println(c);
		c.localEncrypt(Actor.BOB, b);
		System.out.println(c);
		c.remoteDecrypt(Actor.ALICE, Crypto.decrypt(a, c.getValue()));
		System.out.println(c);
		c.localDecrypt(Actor.BOB, b);
		System.out.println(c);
		System.out.println(c.canResolve());
		c.resolve();
		System.out.println(c);
		
	}
}