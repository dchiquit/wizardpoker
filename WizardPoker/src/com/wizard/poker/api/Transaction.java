package com.wizard.poker.api;

import java.math.BigInteger;

public class Transaction {

	public final Action action;
	public final Actor signator;
	public final BigInteger value;
	
	public Transaction(Action action, Actor signator, BigInteger value) {
		this.action = action;
		this.signator = signator;
		this.value = value;
	}
}
