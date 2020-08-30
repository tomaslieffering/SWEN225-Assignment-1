package Cluedo.Card;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class WeaponCard implements Card {
	public enum WeaponType {
		CANDLESTICK, DAGGER, LEAD_PIPE, REVOLVER, ROPE, SPANNER
	}

	private final WeaponType weaponType;

	private WeaponCard(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	/**
	 * Constructs and returns a set of all possible cards of this type
	 * @return the set of cards
	 */
	public static Set<? extends Card> getAllCards() {

		Set<WeaponCard> weaponCards = new HashSet<>();
		for (WeaponType pt : WeaponType.values()) {
			weaponCards.add(new WeaponCard(pt));
		}
		return weaponCards;
	}
	
	@Override
	public String toString() {
		return this.weaponType.toString();
	}

	public void draw(Graphics g, int xPos, int yPos){
		g.setFont(new Font("Montserrat", Font.BOLD, 10));
		g.drawRoundRect(xPos, yPos, 130, 180, 20, 20);
		g.drawString(this.weaponType.toString(), xPos + 10, yPos + 50);
	}

	public WeaponCard.WeaponType getType(){
		return weaponType;
	}
}
