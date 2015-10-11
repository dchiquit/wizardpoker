package com.wizard.poker.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import networking.CardStream;

import com.wizard.poker.profile.PrivateProfile;

public class Pool implements Iterable<Card> {
	private final List<Card> cards = new ArrayList<Card>();

	public Pool() {

	}

	public void add(Card c) {
		cards.add(c);
	}

	public int size() {
		return cards.size();
	}

	public Card get(int index) {
		return cards.get(index);
	}

	public void clear() {
		cards.clear();
	}

	public void encrypt(Actor actor, PrivateProfile profile) {
		for (Card c : cards) {
			c.localEncrypt(actor, profile);
		}
	}

	public void decrypt(Actor actor, PrivateProfile profile) {
		for (Card c : cards) {
			c.localDecrypt(actor, profile);
		}
	}

	public void shuffle(Actor actor, PrivateProfile profile, Random r) {
		Collections.shuffle(cards, r);
		encrypt(actor, profile);
	}

	public void writeAll(CardStream cs) {
		for (Card c : cards) {
			cs.writeCard(c);
		}
	}

	public static Pool shuffleRemote(Actor actor, CardStream cs, int count) {
		Pool pool = new Pool();
		for (int i = 0; i < count; i++) {
			pool.add(cs.readCard(Action.ENCRYPT, actor));
		}
		return pool;
	}

	public static Pool defaultDeck() {
		Pool pool = new Pool();
		for (int i = 65; i < 65 + 52; i++) {
			pool.add(new Card(i));
		}
		return pool;
	}

	public String toString() {
		StringBuilder ret = new StringBuilder();
		ret.append("[\n");
		for (Card c : cards) {
			ret.append("\t" + c + "\n");
		}
		ret.append("]");
		return ret.toString();
	}

	/**
	 * draws 1 card from this pool that the opposing actor has encrypted. It is
	 * important that drawn card maintains the same transaction history, or else
	 * it won't be resolvable.
	 * 
	 * @param actor
	 * @param i
	 * @param cs
	 * @return
	 */
	public Card draw(Action action, Actor actor, CardStream cs) {
		Card c = new Card(cards.get(0).getTransactions()); // make a new blank
															// card from this
															// pool
		cs.readCard(c, action, actor);
		return c;
	}

	/**
	 * draws i cards from this pool that the opposing actor has encrypted. It is
	 * important that drawn cards maintain the same transaction history, or else
	 * they won't be resolvable.
	 * 
	 * @param actor
	 * @param i
	 * @param cs
	 * @return
	 */
	public Pool draw(Action action, Actor actor, int i, CardStream cs) {
		Pool pool = new Pool();
		if (i > size()) {
			throw new IllegalStateException("Cannot draw " + i
					+ " cards from deck of size " + size());
		}
		for (int j = 0; j < i; j++) {
			pool.add(draw(action, actor, cs));
		}
		return pool;
	}

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}
}
