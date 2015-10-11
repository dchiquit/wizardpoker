package com.wizard.poker.crypto.rsa;

import java.math.BigInteger;

public class RSACrypto {
	public static BigInteger encrypt(RSAPrivateProfile privateProfile,
			BigInteger plaintext) {
		return privateProfile.encrypt(plaintext);
	}

	public static BigInteger decrypt(RSAPrivateProfile privateProfile,
			BigInteger ciphertext) {
		return privateProfile.decrypt(ciphertext);
	}
}