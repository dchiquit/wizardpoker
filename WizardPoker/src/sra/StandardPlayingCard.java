package sra;

public class StandardPlayingCard {
	
	public static final int DECKSIZE = 52;
	public static final int RANKS = 13;
	
	public static final int HANDSIZE = 5;
	
	enum Suit {
		DIAMONDS, CLUBS, HEARTS, SPADES;
	}
	

	
	private int value;

	public Suit getSuit() {
		return Suit.values()[value/RANKS];
	}
	
	public int getRank() {
		return value % RANKS;
	}
	
}
