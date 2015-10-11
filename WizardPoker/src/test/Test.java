package test;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.wizard.poker.crypto.Crypto;
import com.wizard.poker.crypto.rsa.RSAPrivateProfile;


public class Test {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		RSAPrivateProfile a = new RSAPrivateProfile();
//		RSAPrivateProfile b = new RSAPrivateProfile();
		BigInteger message = new BigInteger("3");
		System.out.println(message);
		BigInteger tmp;
		tmp = Crypto.encrypt(a, message);
		System.out.println(tmp);
		tmp = Crypto.decrypt(a, tmp);
		System.out.println(tmp);
		tmp = Crypto.decrypt(a, tmp);
		System.out.println(tmp);
		tmp = Crypto.encrypt(a, tmp);
		System.out.println(tmp);
		System.out.println(a.getPrivateExponent().multiply(a.getPublicExponent()).mod(a.getModulus()));
		System.out.println();
		System.out.println(message.modPow(a.getPublicExponent(), a.getModulus()));
		System.out.println((message.modPow(a.getPublicExponent(), a.getModulus())).modPow(a.getPrivateExponent(), a.getModulus()));
		System.out.println((message.modPow(a.getPrivateExponent(), a.getModulus())).modPow(a.getPublicExponent(), a.getModulus()));
	}
}
