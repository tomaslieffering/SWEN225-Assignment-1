import java.util.HashSet;
import java.util.Set;

public class PersonCard extends Card{
	
	public static enum PersonType {
		MISS_SCARLETT, COLONEL_MUSTARD, MRS_WHITE, MR_GREEN, MRS_PEACOCK, PROFESSOR_PLUM
	}
	
	private final PersonType personType;
	
	private PersonCard(PersonType personType) {
		this.personType = personType;
	}
	
	public static Set<? extends Card> getAllCards() {
		
		Set<PersonCard> personCards = new HashSet<>();
		for(PersonType pt: PersonType.values()) {
			personCards.add(new PersonCard(pt));
		}
		return personCards;
	}
	
	public String toString() {
		return this.personType.toString();
	}
}