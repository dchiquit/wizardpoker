package com.wizard.poker.crypto.rsa;

import java.math.BigInteger;

import com.wizard.poker.profile.PublicProfile;

public class RSAPublicProfile implements PublicProfile {

	private final BigInteger exponent;
	private final BigInteger modulus;
	
	public RSAPublicProfile(BigInteger exponent, BigInteger modulus) {
		this.exponent = exponent;
		this.modulus = modulus;
	}

	public BigInteger getExponent() {
		return exponent;
	}
	public BigInteger getModulus() {
		return modulus;
	}

	public BigInteger encrypt(BigInteger plaintext) {
		return plaintext.modPow(exponent, modulus);
	}
}
