package com.wizard.poker.api;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
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

	public Card(long value) {
		this(BigInteger.valueOf(value));
	}

	public Card(Action action, Actor actor, BigInteger value) {
		remote(action, actor, value);
	}

	public Card(Action action, Actor actor, ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		this(action, actor, (BigInteger) ois.readObject());
	}

	/**
	 * sometimes we know the transactions a card has undergone, but not the
	 * values of those transactions. We need a constructor which will copy the
	 * actor and action of the transaction stack to a blank card.
	 * 
	 * @param transactions
	 */
	public Card(Stack<Transaction> transactions) {
		for (Transaction trans : transactions) {
			this.transactions.add(new Transaction(trans.action, trans.actor, null));
		}
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

	public boolean canResolve() {
		int a = 0;
		int b = 0;
		for (Transaction trans : transactions) {
			if (trans.actor == Actor.ALICE && trans.action == Action.ENCRYPT) {
				a++;
			}
			if (trans.actor == Actor.ALICE && trans.action == Action.DECRYPT) {
				a--;
			}
			if (trans.actor == Actor.BOB && trans.action == Action.ENCRYPT) {
				b++;
			}
			if (trans.actor == Actor.BOB && trans.action == Action.DECRYPT) {
				b--;
			}
		}
		return a == 0 && b == 0;
	}

	public void resolve() {
		if (!canResolve()) {
			throw new IllegalStateException("Can't resolve transaction stack "
					+ transactions);
		}
		if (value != null && !value.equals(getValue())) {
			throw new IllegalStateException("Resolve failed: "
					+ toString(value) + " != " + toString(getValue()));
		}
		if (value == null) {
			value = getValue();
		}
		transactions.clear();

	}

	public void writeObject(ObjectOutputStream oos) throws IOException {
		oos.writeObject(getValue());
	}

	public String toString() {
		return toString(getValue());
	}

	public static String toString(BigInteger i) {
		if (i == null) {
			return "null";
		}
		BigInteger x = BigInteger.valueOf(256);
		StringBuilder sb = new StringBuilder();
		while (!i.max(BigInteger.ZERO).equals(BigInteger.ZERO)) {
			sb.append((char) (i.mod(x).longValue()));
			i = i.shiftRight(8);
		}
		return sb.toString().replace('\n', ' ').replace('\t', ' ')
				.replace('\r', ' ');
	}

	public Stack<Transaction> getTransactions() {
		return transactions;
	}
}
