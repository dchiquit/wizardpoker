package com.wizard.poker.api;

import java.math.BigInteger;

public class Transaction {

	public final Action action;
	public final Actor actor;
	public final BigInteger value;
	
	public Transaction(Action action, Actor signator, BigInteger value) {
		this.action = action;
		this.actor = signator;
		this.value = value;
	}
	
	public String toString() {
		return "<"+actor+" "+action+" "+Card.toString(value)+">";
	}
}
