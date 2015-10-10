package com.wizard.poker.crypto.rsa;

import java.math.BigInteger;

public class RSACrypto {

	public static BigInteger encrypt(RSAPublicProfile publicProfile,
			BigInteger plaintext) {
		return plaintext.modPow(publicProfile.getExponent(),
				publicProfile.getModulus());
	}

	public static BigInteger encrypt(RSAPrivateProfile privateProfile,
			BigInteger plaintext) {
		return plaintext.modPow(privateProfile.getPublicExponent(),
				privateProfile.getModulus());
	}

	public static BigInteger decrypt(RSAPrivateProfile privateProfile,
			BigInteger ciphertext) {
		return ciphertext.modPow(privateProfile.getPrivateExponent(),
				privateProfile.getModulus());
	}
}
