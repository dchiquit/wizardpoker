package com.wizard.poker.crypto.rsa;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import com.wizard.poker.profile.PrivateProfile;

public class RSAPrivateProfile implements PrivateProfile {

	public static final BigInteger P = new BigInteger(
			"1654591337368911640128593748203215386720970120252520089602335933244413366225468017652935449265871");
	public static final BigInteger N = P.multiply(BigInteger.valueOf(2)).add(
			BigInteger.ONE);

	private final BigInteger decryptionExponent;
	private final BigInteger encryptionExponent;

	public RSAPrivateProfile(BigInteger publicExponent,
			BigInteger privateExponent, BigInteger publicModulus,
			BigInteger modulus) {
		this.decryptionExponent = publicExponent;
		this.encryptionExponent = publicExponent;
	}

	public RSAPrivateProfile() throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		BigInteger tot = N.subtract(BigInteger.ONE);
		Random rr = new Random();
		this.encryptionExponent = (new BigInteger(320, rr)).multiply(
				BigInteger.valueOf(2)).add(BigInteger.ONE);// TODO could be 320
															// with additional
															// checks
		BigInteger t = BigInteger.ZERO;
		BigInteger nt = BigInteger.ONE;
		BigInteger r = tot;
		BigInteger nr = encryptionExponent;
		while (!nr.equals(BigInteger.ZERO)) {
			BigInteger quot = r.divide(nr);
			BigInteger nt_tmp = t.subtract(quot.multiply(nt));
			t = nt;
			nt = nt_tmp;

			BigInteger nr_tmp = r.subtract(quot.multiply(nr));
			r = nr;
			nr = nr_tmp;
		}
		this.decryptionExponent = t;
	}

	public BigInteger getPublicExponent() {
		return encryptionExponent;
	}

	public BigInteger getPrivateExponent() {
		return decryptionExponent;
	}

	public BigInteger encrypt(BigInteger plaintext) {
		return plaintext.modPow(encryptionExponent, N);
	}

	public BigInteger decrypt(BigInteger ciphertext) {
		return ciphertext.modPow(decryptionExponent, N);
	}
}