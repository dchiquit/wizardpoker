package com.wizard.poker.api;

import java.math.BigInteger;
import java.util.Stack;

import com.wizard.poker.crypto.Crypto;
import com.wizard.poker.profile.PrivateProfile;

public class Card {

	private BigInteger value = null;
	private Stack<Transaction> transactions = new Stack<Transaction>();

	public Card(BigInteger value) {
		this.value = value;
	}

	public Card(Action action, Actor actor, BigInteger value) {
		remote(action, actor, value);
	}

	public BigInteger getValue() {
		if (transactions.isEmpty()) {
			return value;
		}
		return transactions.peek().value;
	}

	public void local(Action action, Actor actor, PrivateProfile profile) {
		if (action == Action.ENCRYPT) {
			localEncrypt(actor, profile);
		} else if (action == Action.DECRYPT) {
			localDecrypt(actor, profile);
		}
	}

	public void localEncrypt(Actor actor, PrivateProfile profile) {
		BigInteger newValue = Crypto.encrypt(profile, getValue());
		transactions.add(new Transaction(Action.ENCRYPT, actor, newValue));
	}

	public void localDecrypt(Actor actor, PrivateProfile profile) {
		BigInteger newValue = Crypto.decrypt(profile, getValue());
		transactions.add(new Transaction(Action.DECRYPT, actor, newValue));
	}

	public void remote(Action action, Actor actor, BigInteger newValue) {
		if (action == Action.ENCRYPT) {
			remoteEncrypt(actor, newValue);
		} else if (action == Action.DECRYPT) {
			remoteDecrypt(actor, newValue);
		}
	}

	public void remoteEncrypt(Actor actor, BigInteger newValue) {
		transactions.add(new Transaction(Action.ENCRYPT, actor, newValue));
	}

	public void remoteDecrypt(Actor actor, BigInteger newValue) {
		transactions.add(new Transaction(Action.DECRYPT, actor, newValue));
	}

	public void resolve() {
		
	}
}
