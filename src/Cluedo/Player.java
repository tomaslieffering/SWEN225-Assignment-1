package Cluedo;

import Cluedo.Card.Card;
import Cluedo.Card.PersonCard;

import java.util.ArrayList;
import java.util.List;

public class Player {
	PersonCard.PersonType personType;
	List<Card> hand = new ArrayList<>();
	
	public Player(PersonCard.PersonType personType) {
		this.personType = personType;
	}
	
	/**
	 * Adds a dealt card to the players hand
	 * @param toGive the card to be added to the hand
	 */
	public void giveCard(Card toGive) {
		hand.add(toGive);
	}
	
	@Override
	public String toString() {
		return this.personType.toString();
	}
	
	/**
	 * Returns a string representation of the players hand
	 */
	public String handToString() {
		StringBuilder str = new StringBuilder();
		for (Card c: hand) {
			str.append(c.toString()).append("\n");
		}
		return str.toString();
	}

	public PersonCard.PersonType getPersonType(){
		return this.personType;
	}
}
