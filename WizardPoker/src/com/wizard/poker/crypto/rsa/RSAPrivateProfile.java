package com.wizard.poker.crypto.rsa;

import java.math.BigInteger;

import com.wizard.poker.profile.PrivateProfile;

public class RSAPrivateProfile implements PrivateProfile {

	private final BigInteger publicKey;
	private final BigInteger privateKey;
	
	public RSAPrivateProfile(BigInteger publicKey, BigInteger privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	
	@Override
	public BigInteger getPublicKey() {
		return publicKey;
	}

	@Override
	public BigInteger getPrivateKey() {
		return privateKey;
	}

}
