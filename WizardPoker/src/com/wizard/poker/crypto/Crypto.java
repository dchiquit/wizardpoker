package com.wizard.poker.crypto;

import java.math.BigInteger;

import com.wizard.poker.crypto.rsa.RSACrypto;
import com.wizard.poker.crypto.rsa.RSAPrivateProfile;
import com.wizard.poker.profile.PrivateProfile;

public class Crypto {

	public static BigInteger encrypt(PrivateProfile privateProfile,
			BigInteger plaintext) {
		return RSACrypto.encrypt((RSAPrivateProfile) privateProfile, plaintext);
	}

	public static BigInteger decrypt(PrivateProfile privateProfile,
			BigInteger ciphertext) {
		return RSACrypto
				.decrypt((RSAPrivateProfile) privateProfile, ciphertext);
	}
}