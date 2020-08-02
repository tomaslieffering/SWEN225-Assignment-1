package Cluedo;

import Cluedo.Board.Board;
import Cluedo.Board.BoardFormatException;
import Cluedo.Card.Card;
import Cluedo.Card.PersonCard;
import Cluedo.Card.RoomCard;
import Cluedo.Card.WeaponCard;

import java.io.File;
import java.util.*;

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

	/**
	 * Deals with general playing mechanics until the game is over
	 */
	public void play() {
		int roundNumber  = 1;
		boolean ready;
		Scanner sc = new Scanner(System.in);
		while (!isOver) {
			System.out.println("Round " + roundNumber + " starting!");
			int playerNumber = 1;
			for (Player p : players) {
				System.out.println(board);
				int diceNumber = 0;
				System.out.println("Player " + playerNumber + "'s turn! (" + p.personType.toString() + ") Rolling dice...");
				diceNumber = Turn.rollDice();
				System.out.println("You have to take " + diceNumber + " moves. What do you want to do?");
				// TODO Nicola to implement turn mechanic

				//get input from player
				String input = "";
				//make sure the input is correct
				do {
					input = getInput(sc);
				} while (!board.checkMove(input, diceNumber, board.findPlayer(p), p));

				//if it is correct then move the player
				board.movePlayer(p, input);
				playerNumber++;
			}
			System.out.println("Round " + roundNumber + " finished! Ready for the next round?");
			//wait for the players to be ready for the next round
			ready = false;
			do {
				ready = getYesNo(sc);
			} while (!ready);
			roundNumber++;
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
		int players;
		do {
			players = getNumberPlayers(sc);
		} while (players == -1);

		// get the list of all possible players
		List<PersonCard.PersonType> characters = new ArrayList<>(Arrays.asList(PersonCard.PersonType.values()));

		// assign character to players randomly using Collection.shuffle
		for (int i = 0; i < players; i++) {
			Collections.shuffle(characters);
			if (!characters.get(0).toString().equals(PersonCard.PersonType.NO_PLAYER.toString())){
				this.players.add(new Player(characters.get(0)));
			}
			else{
				i--;
			}
			characters.remove(0);
		}
		// tell the players what character they are
		int i = 1;
		for (Player p : this.players) {
			System.out.println("Player " + i + " is: " + p.toString());
			i++;
		}
		System.out.println("Ready to shuffle and deal?");
		
		//waits for the player to say they are ready
		boolean ready;
		do {
			ready = getYesNo(sc);
		} while (!ready);
		
		//deal cards and displays information to the players
		dealDeck();
		System.out.println("The cards in the envelope are:");
		for (Card c : envelope) {
			System.out.println(c.toString());
		}

		for (Player p : this.players) {
			System.out.println(p.toString() + " has the cards:\n" + p.handToString());
		}

		//create the board
		try {
			board = new Board(new File("board-layout.dat"));
		} catch (BoardFormatException e) {
			System.out.println("Oops!\n" + e.getCause());
		}

		//delete players from board not in game

		board.deleteUnusedPlayers(this.players);

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
				}
				else {
					System.out.println("Please enter either 'y' (yes) or 'n' (no)");
				}
				return false;
			} catch (Exception e) {
				System.out.println("Please enter either 'y' (yes) or 'n' (no)");
				return false;
			}
		}
		return false;
	}

	/**
	 * Gets a input from the player of where to move
	 * @param sc the standard input scanner
	 * @return the input string
	 */
	public static String getInput(Scanner sc){
		//make sure the player has typed
		if (sc.hasNext()){
			String input = sc.nextLine();
			//check whether the input is vaild
			for (char c: input.toCharArray()){
				if (!(c == 'u' || c == 'd' || c == 'l' || c == 'r')){
					System.out.println("Please enter a combination of 'u' (up), 'd' (down), 'l' (left) or 'r'(right):");
					return "";
				}
			}
			return input;
		}
		return "";
	}

	/**
	 * Helper method that deal with dealing out the hands to the players and making
	 * the envelope
	 */
	public void dealDeck() {
		// get each type of card
		List<Card> personCards = new ArrayList<>(PersonCard.getAllCards());
		List<Card> weaponCards = new ArrayList<>(WeaponCard.getAllCards());
		List<Card> roomCards = new ArrayList<>(RoomCard.getAllCards());

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
