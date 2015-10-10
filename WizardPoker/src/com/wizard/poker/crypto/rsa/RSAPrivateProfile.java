package com.wizard.poker.crypto.rsa;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import com.wizard.poker.profile.PrivateProfile;

public class RSAPrivateProfile implements PrivateProfile {

	private final BigInteger publicExponent;
	private final BigInteger privateExponent;
	private final BigInteger modulus;

	public RSAPrivateProfile(BigInteger publicExponent,
			BigInteger privateExponent, BigInteger modulus) {
		this.publicExponent = publicExponent;
		this.privateExponent = privateExponent;
		this.modulus = modulus;
	}

	public RSAPrivateProfile() throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		KeyPairGenerator kpg;
		kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair kp = kpg.genKeyPair();
		KeyFactory fact = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(),
				RSAPublicKeySpec.class);
		RSAPublicKeySpec priv = fact.getKeySpec(kp.getPrivate(),
				RSAPublicKeySpec.class);
		this.publicExponent = pub.getPublicExponent();
		this.privateExponent = priv.getPublicExponent();
		this.modulus = pub.getModulus();
		System.out.println(pub.getPublicExponent() + "," + pub.getModulus());
		System.out.println(pub.getPublicExponent() + "," + pub.getModulus());
	}

	public BigInteger getPublicExponent() {
		return publicExponent;
	}

	public BigInteger getPrivateExponent() {
		return privateExponent;
	}

	public BigInteger getModulus() {
		return modulus;
	}

	public BigInteger encrypt(BigInteger plaintext) {
		return plaintext.modPow(publicExponent, modulus);
	}

	public BigInteger decrypt(BigInteger ciphertext) {
		return ciphertext.modPow(privateExponent, modulus);
	}
}
