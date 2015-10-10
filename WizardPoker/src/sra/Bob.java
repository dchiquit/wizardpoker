package sra;

import java.math.BigInteger;

import com.wizard.poker.crypto.Crypto;
import com.wizard.poker.profile.PublicProfile;

public class Bob {
	public static void main(String[] args) {
		//Determine key
		Crypto crypto;
		PublicProfile key;
		BigInteger[] encryptedCards = new BigInteger[StandardPlayingCard.DECKSIZE];
		for(int i = 0; i < StandardPlayingCard.DECKSIZE; i++) {
			encryptedCards[i] = crypto.encrypt(key, BigInteger.valueOf(i));
		}
		
	}
}
