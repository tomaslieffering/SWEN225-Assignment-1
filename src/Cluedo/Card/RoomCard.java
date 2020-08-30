package Cluedo.Card;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class RoomCard implements Card {
	public enum RoomType {
		KITCHEN, BALL_ROOM, CONSERVATORY, BILLIARD_ROOM, DINING_ROOM, LIBRARY, LOUNGE, HALL, STUDY,
	}

	private final RoomType roomType;

	private RoomCard(RoomType roomType) {
		this.roomType = roomType;
	}

	/**
	 * Constructs and returns a set of all possible cards of this type
	 * @return the set of cards
	 */
	public static Set<? extends Card> getAllCards() {

		Set<RoomCard> roomCards = new HashSet<>();
		for (RoomType pt : RoomType.values()) {
			roomCards.add(new RoomCard(pt));
		}
		return roomCards;
	}

	@Override
	public void draw(Graphics g, int xPos, int yPos){
		g.setFont(new Font("Montserrat", Font.BOLD, 10));
		g.drawRoundRect(xPos, yPos, 130, 180, 20, 20);
		g.drawString(this.roomType.toString(), xPos + 10, yPos + 50);
	}
	
	@Override
	public String toString() {
		return this.roomType.toString();
	}

	public RoomCard.RoomType getType() {
		return roomType;
	}
}
