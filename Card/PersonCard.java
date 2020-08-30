package Cluedo.Card;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class PersonCard implements Card {
	
	public enum PersonType {
		MISS_SCARLETT, COLONEL_MUSTARD, MRS_WHITE, MR_GREEN, MRS_PEACOCK, PROFESSOR_PLUM
	}
	
	private final PersonType personType;
	
	private PersonCard(PersonType personType) {
		this.personType = personType;
	}
	
	/**
	 * Constructs and returns a set of all possible cards of this type
	 * @return the set of cards
	 */
	public static Set<? extends Card> getAllCards() {
		
		Set<PersonCard> personCards = new HashSet<>();
		for(PersonType pt: PersonType.values()) {
			personCards.add(new PersonCard(pt));
		}
		return personCards;
	}
	
	@Override
	public String toString() {
		return this.personType.toString();
	}

	public void draw(Graphics g, int xPos, int yPos){
		g.setFont(new Font("Montserrat", Font.BOLD, 10));
		g.drawRoundRect(xPos, yPos, 130, 180, 20, 20);
		g.drawString(this.personType.toString(), xPos + 10, yPos + 50);
	}

	public PersonType getType() {
		return personType;
	}
}