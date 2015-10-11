package com.wizard.poker.crypto.rsa;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
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
	
	public static void main(String[] args) {
		try {
			new RSAPrivateProfile();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(),
				RSAPrivateKeySpec.class);
		this.publicExponent = pub.getPublicExponent();
		this.privateExponent = priv.getPrivateExponent();
		this.modulus = pub.getModulus();
		System.out.printf("Modulus: %d\nModulus: %d\n", pub.getModulus(), priv.getModulus());
		System.out.printf("Public Key: %d\n", pub.getPublicExponent());
		System.out.printf("Private Key: %d\n", priv.getPrivateExponent());
		//System.out.printf("ed mod n = %d\n", this.publicExponent.multiply(privateExponent).mod(modulus));
		for(int i = 0; i < 10000; i++) {
			/*if(!BigInteger.valueOf(i).modPow(privateExponent, modulus).modPow(publicExponent, modulus).equals(BigInteger.valueOf(i))) {
				System.out.printf("FAIL at %d\n", i);
			}
			if(!BigInteger.valueOf(i).modPow(publicExponent, modulus).modPow(privateExponent, modulus).equals(BigInteger.valueOf(i))) {
				Syste.out.printf("FAIL at %d\n", i);
			}*/
			BigInteger bi = BigInteger.valueOf(i);
			if(!encrypt(decrypt(bi)).equals(bi)) {
				System.out.printf("FAIL at %d\n", i);
			}
			if(!decrypt(encrypt(bi)).equals(bi)) {
				System.out.printf("FAIL at %d\n", i);
			}
		}
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
