import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Game {

	Set<Card> envelope = new HashSet<>();
	List<Player> players = new ArrayList<>();
	Board board;
	boolean isOver = false;

	public static void main(String[] args) {
		Game game = new Game();
		game.initialise();
		game.play();
	}

	public void play() {
		while (!isOver) {
			for (Player p : players) {
				// TODO Nicola to implement turn mechanic
			}
		}
	}

	/**
	 * This method initializes the state of the game, creating players, their hands,
	 * the envelope and the board
	 */

	public void initialise() {
		Scanner sc = new Scanner(System.in);
		System.out.println(" a88888b.  dP                            dP          \r\n" + 
						   "d8'   `88  88                            88          \r\n" + 
						   "88         88  dP    dP  .d8888b.  .d888b88  .d8888b. \r\n" + 
						   "88         88  88    88  88ooood8  88'  `88  88'  `88 \r\n" + 
						   "Y8.   .88  88  88.  .88  88.  ...  88.  .88  88.  .88 \r\n" + 
						   " Y88888P'  dP  `88888P'  `88888P'  `88888P8  `88888P'\n");
		
		System.out.println("*************************************************\n"
						 + " Welcome to Cluedo! How many people are playing? \n");
		// get the number of players playing
		int players = -1;
		do {
			players = getNumberPlayers(sc);
		} while (players == -1);

		// get the list of all possible players
		List<PersonCard.PersonType> characters = new ArrayList<>(Arrays.asList(PersonCard.PersonType.values()));

		// assign character to players randomly using Collection.shuffle
		for (int i = 0; i < players; i++) {
			Collections.shuffle(characters);
			this.players.add(new Player(characters.get(0)));
			characters.remove(0);
		}
		// tell the players what character they are
		int i = 1;
		for (Player p : this.players) {
			System.out.println("Player " + i + " is: " + p.toString());
			i++;
		}
		System.out.println("");
		System.out.println("Ready to shuffle and deal?");
		
		//waits for the player to say they are ready
		boolean ready = false;
		do {
			ready = getYesNo(sc);
		} while (!ready);
		
		//deal cards and displays information to the players
		dealDeck();
		System.out.println("The cards in the envelope are:");
		for (Card c : envelope) {
			System.out.println(c.toString());
		}
		System.out.println("");

		for (Player p : this.players) {
			System.out.println(p.toString() + " has the cards:\n" + p.handToString());
		}

		board = new Board();

		System.out.println("Everything is ready! Ready to start?");
		
		//waits for the player to say they are ready
		ready = false;
		do {
			ready = getYesNo(sc);
		} while (!ready);
	}

	/**
	 * Helper method to get the number of players, and handles input errors
	 * 
	 * @param sc the scanner which reads in from the System.in standard input
	 * @return the number of people playing or -1 if there is a error
	 */
	public static int getNumberPlayers(Scanner sc) {
		// if there is something that has been typed
		if (sc.hasNext()) {
			try {
				// get the inputed string and try parsing it to a int
				int players = Integer.parseInt(sc.nextLine());
				// if the wrong number of player
				if (players < 3 || players > 6) {
					System.out.println("Please enter a number between 3 and 6:");
					return -1;
				}
				// else everything is correct, return the number of players
				else {
					return players;
				}
			}
			// catch parsing exception, if a digit is not inputed
			catch (Exception e) {
				System.out.println("Please enter a integer number:");
				return -1;
			}
		}
		return 0;
	}
	
	/**
	 * Gets a yes or no input from the players
	 * @param sc scanner for the standard input stream
	 * @return true if yes, false if no
	 */
	public static boolean getYesNo(Scanner sc) {
		//if something has been typed
		if (sc.hasNext()) {
			try {
				//get the input
				String input = sc.nextLine();
				//check whether the input is either 'y' or 'n'
				if (input.equals("y")) {
					//return true if yes
					return true;
				}
				if (input.equals("n")) {
					System.out.println("Enter 'y' when you are ready!");
					return false;
				}
				else {
					System.out.println("Please enter either 'y' (yes) or 'n' (no)");
					return false;
				}
			} catch (Exception e) {
				System.out.println("Please enter either 'y' (yes) or 'n' (no)");
				return false;
			}
		}
		return false;
	}

	/**
	 * Helper method that deal with dealing out the hands to the players and making
	 * the envelope
	 */
	public void dealDeck() {
		// get each type of card
		List<Card> personCards = new ArrayList<>();
		personCards.addAll(PersonCard.getAllCards());
		List<Card> weaponCards = new ArrayList<>();
		weaponCards.addAll(WeaponCard.getAllCards());
		List<Card> roomCards = new ArrayList<>();
		roomCards.addAll(RoomCard.getAllCards());

		Collections.shuffle(roomCards);
		Collections.shuffle(weaponCards);
		Collections.shuffle(personCards);

		// create envelope
		envelope.add(roomCards.get(0));
		roomCards.remove(0);
		envelope.add(weaponCards.get(0));
		weaponCards.remove(0);
		envelope.add(personCards.get(0));
		personCards.remove(0);

		// add the rest to the deck, ready to be dealt
		List<Card> deck = new ArrayList<>();
		deck.addAll(roomCards);
		deck.addAll(weaponCards);
		deck.addAll(personCards);
		Collections.shuffle(deck);
		int playerToDealTo = 0;

		// deal the cards to each player
		for (Card c : deck) {
			players.get(playerToDealTo).giveCard(c);
			playerToDealTo++;
			if (playerToDealTo == players.size()) {
				playerToDealTo = 0;
			}
		}
	}
}
