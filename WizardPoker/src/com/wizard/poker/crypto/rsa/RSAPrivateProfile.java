package com.wizard.poker.crypto.rsa;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
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
			System.out.println(r + "/" + nr + "=" + quot);
			BigInteger nt_tmp = t.subtract(quot.multiply(nt));
			t = nt;
			nt = nt_tmp;
			System.out.println("t nt " + t + " " + nt);

			BigInteger nr_tmp = r.subtract(quot.multiply(nr));
			r = nr;
			nr = nr_tmp;
			System.out.println("r nr " + r + " " + nr);
		}
		this.decryptionExponent = t;
		System.out.println("1: "
				+ encryptionExponent.multiply(decryptionExponent).mod(tot));
		System.out.println("p*q: " + N);
		System.out.println("totiont: " + tot);
		System.out.println("e: " + encryptionExponent);
		System.out.println("d: " + decryptionExponent);
		System.out.println("ayy");
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