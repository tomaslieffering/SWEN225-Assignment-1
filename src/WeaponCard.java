import java.util.HashSet;
import java.util.Set;

public class WeaponCard extends Card {
	public static enum WeaponType {
		CANDLESTICK, DAGGER, LEAD_PIPE, REVOLVER, ROPE, SPANNER
	}

	private final WeaponType weaponType;

	private WeaponCard(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	public static Set<? extends Card> getAllCards() {

		Set<WeaponCard> weaponCards = new HashSet<>();
		for (WeaponType pt : WeaponType.values()) {
			weaponCards.add(new WeaponCard(pt));
		}
		return weaponCards;
	}
	
	public String toString() {
		return this.weaponType.toString();
	}
}
