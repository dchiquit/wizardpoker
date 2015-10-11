package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

import com.wizard.poker.api.Action;
import com.wizard.poker.api.Actor;
import com.wizard.poker.api.Card;

public class CardStream {

	private final ObjectInputStream ois;
	private final ObjectOutputStream oos;

	public CardStream(ObjectInputStream ois, ObjectOutputStream oos) {
		this.ois = ois;
		this.oos = oos;
	}
	
	public void writeCard(Card c) {
		try {
			c.writeObject(oos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Card readCard(Action action, Actor actor) {
		try {
			return new Card(action, actor, (BigInteger)ois.readObject());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Card readCard(Card c, Action action, Actor actor) {
		try {
			c.remote(action, actor, (BigInteger)ois.readObject());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
}
