import java.util.ArrayList;
import java.util.List;

public class Player {
	PersonCard.PersonType personType;
	List<Card> hand = new ArrayList<>();
	
	public Player(PersonCard.PersonType personType) {
		this.personType = personType;
	}
	
	public void giveCard(Card toGive) {
		hand.add(toGive);
	}
	
	public String toString() {
		return this.personType.toString();
	}
	
	public String printHand() {
		StringBuilder str = new StringBuilder();
		for (Card c: hand) {
			str.append(c.toString()).append("\n");
		}
		return str.toString();
	}
}
