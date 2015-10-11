
package test;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import com.wizard.poker.crypto.Crypto;
import com.wizard.poker.crypto.rsa.RSAPrivateProfile;
import com.wizard.poker.profile.PrivateProfile;

public class Test {
	public static void main(String[] args) throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		PrivateProfile a = new RSAPrivateProfile();
		PrivateProfile b = new RSAPrivateProfile();
		Random r = new Random();
		long j;
		BigInteger m;
		for (int i = 0; i < 100; i++) {
			j = Math.abs(r.nextLong());
			m = BigInteger.valueOf(j);
			System.out.println("(" + i + ")\tTesting " + m);
			BigInteger t1 = Crypto.decrypt(a, Crypto.encrypt(a, m));
			System.out.println("\ta " + t1);
			if (!t1.equals(m))
				break;
			BigInteger t2 = Crypto.decrypt(b, Crypto.encrypt(b, m));
			System.out.println("\tb " + t2);
			if (!t2.equals(m))
				break;
			BigInteger t3 = Crypto.decrypt(b,
					Crypto.decrypt(a, Crypto.encrypt(b, Crypto.encrypt(a, m))));
			System.out.println("\tabab " + t3);
			if (!t3.equals(m))
				break;
		}
	}
}