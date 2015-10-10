package com.wizard.poker.crypto.rsa;

import java.math.BigInteger;

import com.wizard.poker.profile.PublicProfile;

public class RSAPublicProfile implements PublicProfile {

	private final BigInteger publicKey;
	
	public RSAPublicProfile(BigInteger publicKey) {
		this.publicKey = publicKey;
	}
	
	@Override
	public BigInteger getPublicKey() {
		return publicKey;
	}

}
