package Cluedo;

import Cluedo.Board.*;
import Cluedo.Card.Card;
import Cluedo.Card.PersonCard;
import Cluedo.Card.RoomCard;
import Cluedo.Card.WeaponCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class Game extends GUI{

	Set<Card> envelope = new HashSet<>();
	List<Player> players = new ArrayList<>();
	Map<WeaponCard.WeaponType, RoomCard.RoomType> weaponsInRoom = new HashMap<>();
	Board board;
	Player currentPlayer;
	int diceNumber1 = 0;
	int diceNumber2 = 0;

	RoomCard.RoomType hoveredRoom = null;
	String currentMove = "";
	int currentMoveCount = 0;


	@Override
	protected void drawBoard(Graphics g) {
		g.setColor(WallTile.wallColor);
		g.fillRect(0, 0, 500,500);

		if (board != null) {
			board.draw(g);
			if(currentPlayer != null)
				board.drawMove(currentMove, currentMoveCount, currentPlayer, g);
		}
		if (hoveredRoom != null) {
			g.setColor(new Color(0x000000));
			g.fillRect(0, 480, 120, 20);
			g.setColor(new Color(0xFFFFFF));
			g.drawString(hoveredRoom.toString().replace('_', ' '), 5, 495);
		}
	}

	@Override
	protected void doMouseMoved(MouseEvent e) {
		if (board == null)
			return;
		if (e.getX() <= 10 || e.getX() >= 490 ||
				e.getY() <= 10 || e.getY() >= 490)
			return;
		Position position = new Position((e.getY() - 10) / 20, (e.getX() - 10) / 20);
		BoardTile tile = board.getTileAt(position);
		if (tile instanceof RoomTile) {
			if (hoveredRoom != ((RoomTile) tile).getRoom()) {
				hoveredRoom = ((RoomTile) tile).getRoom();
				boardGraphics.updateUI();
			}
		}
	}

	@Override
	protected void drawCards(Graphics g) {
		g.setColor(WallTile.wallColor);
		g.fillRect(0, 0, 1150, 200);
		int xPos = 200;
		if (currentPlayer != null) {
			g.setColor(new Color (0x75525D));
			for (Card c : currentPlayer.hand) {
				c.draw(g, xPos, 10);
				xPos += 140;
			}
		}
	}

	@Override
	protected void drawDice(Graphics g) {
		drawSingleDice(g, 10, 10, diceNumber1);
		drawSingleDice(g, 70, 10, diceNumber2);
	}


	public void drawSingleDice(Graphics g, int xPos, int yPos, int value) {
		g.setColor(new Color(0x75525D));
		g.fillRoundRect(xPos, yPos, 50, 50, 10, 10);
		g.setColor(new Color(0x664751));
		g.drawRoundRect(xPos, yPos, 50, 50, 10, 10);
		g.setColor(Color.BLACK);
		if (value == 1) {
			g.fillOval(xPos + 22, yPos + 22, 6, 6);
		}
		if (value == 2) {
			g.fillOval(xPos + 39, yPos + 39, 6, 6);
			g.fillOval(xPos + 5, yPos + 5, 6, 6);
		}
		if (value == 3) {
			g.fillOval(xPos + 39, yPos + 39, 6, 6);
			g.fillOval(xPos + 5, yPos + 5, 6, 6);
			g.fillOval(xPos + 22, yPos + 22, 6, 6);
		}
		if (value == 4) {
			g.fillOval(xPos + 39, yPos + 39, 6, 6);
			g.fillOval(xPos + 5, yPos + 5, 6, 6);
			g.fillOval(xPos + 5, yPos + 39, 6, 6);
			g.fillOval(xPos + 39, yPos + 5, 6, 6);
		}
		if (value == 5) {
			g.fillOval(xPos + 39, yPos + 39, 6, 6);
			g.fillOval(xPos + 5, yPos + 5, 6, 6);
			g.fillOval(xPos + 5, yPos + 39, 6, 6);
			g.fillOval(xPos + 39, yPos + 5, 6, 6);
			g.fillOval(xPos + 22, yPos + 22, 6, 6);
		}
		if (value == 6) {
			g.fillOval(xPos + 39, yPos + 39, 6, 6);
			g.fillOval(xPos + 5, yPos + 5, 6, 6);
			g.fillOval(xPos + 5, yPos + 39, 6, 6);
			g.fillOval(xPos + 39, yPos + 5, 6, 6);
			g.fillOval(xPos + 5, yPos + 22, 6, 6);
			g.fillOval(xPos + 39, yPos + 22, 6, 6);
		}
	}

	/**
	 * Deals with general playing mechanics until the game is over
	 */
	public void play() {
		int roundNumber = 1;
		boolean ready;
		gameLoop:
		while (true) {
			textArea.append("Round " + roundNumber + " starting!\n");
			int playerNumber = 1;
			int numPlayersLeft = 0;
			for (Player p : players) {
				if (!p.hasLost) {
					JDialog readySelect = new JDialog();
					readySelect.setLocation(435, 275);
					readySelect.setResizable(false);
					readySelect.setBackground(WallTile.wallColor);
					readySelect.setSize(new Dimension(280, 150));
					readySelect.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

					JPanel readySelectPanel = new JPanel();
					readySelectPanel.setLayout(new GridLayout(2, 1));
					readySelectPanel.setBackground(WallTile.wallColor);
					readySelectPanel.setBorder(BorderFactory.createLineBorder(Color.white, 4));

					JLabel readyLabel = new JLabel("Player " + playerNumber + "'s turn. Are you ready?");
					readyLabel.setFont(new Font("Montserrat", Font.PLAIN, 15));
					readyLabel.setForeground(RoomTile.lightRoomTile);
					readyLabel.setHorizontalAlignment(SwingConstants.CENTER);
					readyLabel.setVisible(true);
					readySelectPanel.add(readyLabel);

					readySelectPanel.add(this.ready);
					readySelect.add(readySelectPanel);
					readySelect.setVisible(true);

					do {
						ready = doReady();
					} while (!ready);

					readySelect.setVisible(false);
					boardGraphics.updateUI();
					currentPlayer = p;
					drawCards(cardGraphics.getGraphics());
					if (p.hasLost) {
						playerNumber++;
						continue;
					}
					textArea.append("Player " + playerNumber + "'s turn! (" + p.personType.toString() + ") Rolling dice...\n");
					diceNumber1 = Turn.rollDice();
					diceNumber2 = Turn.rollDice();
					drawDice(cardGraphics.getGraphics());
					textArea.append("You have to take " + (diceNumber1 + diceNumber2) + " moves. What do you want to do?\n");

					//get input from player
					left.setVisible(true);
					right.setVisible(true);
					down.setVisible(true);
					up.setVisible(true);

					doMoves(diceNumber1 + diceNumber2, p, boardGraphics.getGraphics());

					left.setVisible(false);
					right.setVisible(false);
					down.setVisible(false);
					up.setVisible(false);

					RoomCard.RoomType r = board.getPlayerRoom(p);
					Turn t = new Turn(players);
					if (r != null) {
						textArea.append("You have entered the " + r.toString() + "\n");
						textArea.append("The weapons in this room are: ");
						for (Map.Entry<WeaponCard.WeaponType, RoomCard.RoomType> e : weaponsInRoom.entrySet()) {
							if (e.getValue() == r) {
								textArea.append(e.getKey().toString() + "\n");
							}
						}
						textArea.append("Would you like to make a suggestion?\n");
						boolean suggest = yesOrNo();
						if (suggest) {
							Suggestion suggestion = t.makeSuggestion(r, false, this);
							clearSelections();
							weaponsInRoom.remove(suggestion.weapon);
							weaponsInRoom.put(suggestion.weapon, suggestion.room);
							Player inSuggestion = this.getPlayerFromType(suggestion.person);
							if (inSuggestion != null) {
								board.movePlayerToPlayer(p, inSuggestion);
							}
							boardGraphics.updateUI();
							t.disproveSuggestion(p, suggestion, this);
						}
						textArea.append("Would you now like to make an accusation? This is will be your final guess\n");
						boolean accuse = yesOrNo();
						if (accuse) {
							Suggestion accusation = t.makeSuggestion(r, true, this);
							boolean win = t.accusationCheck(accusation, envelope);
							clearSelections();
							if (win) {
								textArea.append("Player " + playerNumber + " has solved the murder, and wins the game!\n");
								break gameLoop;
							} else {
								textArea.append("Player " + playerNumber + " has guessed incorrectly. They are now out of the game.");
								p.hasLost = true;
								board.killPlayer(p);
							}
						}

					}
				}
				playerNumber++;
				textArea.setText("");
			}
			for (Player p: players){
				if (!p.hasLost){
					numPlayersLeft++;
				}
			}
			if (numPlayersLeft == 1){
				break;
			}
			//wait for the players to be ready for the next round
			roundNumber++;
		}
		JDialog end = new JDialog();
		end.setLocation(435, 275);
		end.setResizable(false);
		end.setBackground(WallTile.wallColor);
		end.setSize(new Dimension(280, 150));

		JPanel endPanel = new JPanel();
		endPanel.setBackground(WallTile.wallColor);

		JLabel endLabel = new JLabel("Game Over");
		endLabel.setFont(new Font("Montserrat", Font.PLAIN, 11));
		endLabel.setForeground(RoomTile.lightRoomTile);

		endPanel.add(endLabel);
		end.add(endPanel);
		end.setVisible(true);
	}


	/**
	 * This method initializes the state of the game, creating players, their hands,
	 * the envelope and the board
	 */

	public void initialise() {

		textArea.setBackground(WallTile.wallColor);

		JDialog start = new JDialog();
		start.setLocation(435, 275);
		start.setResizable(false);
		start.setBackground(WallTile.wallColor);
		start.setSize(new Dimension(280, 150));
		start.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		JPanel startComponent = new JPanel();
		startComponent.setLayout(new GridLayout(2, 1));
		startComponent.setBackground(WallTile.wallColor);

		JLabel startLabel = new JLabel("Welcome to Cluedo! How many are playing?");
		startLabel.setFont(new Font("Montserrat", Font.PLAIN, 11));
		startLabel.setForeground(RoomTile.lightRoomTile);
		startLabel.setHorizontalAlignment(SwingConstants.CENTER);
		startComponent.add(startLabel);

		JPanel startButtons = new JPanel();
		startButtons.setLayout(new GridLayout(1, 4));
		startComponent.add(startButtons);
		startComponent.setBorder(BorderFactory.createLineBorder(Color.white, 4));
		start.add(startComponent);

		for (JButton b: playerNumbers){
			startButtons.add(b);
			b.setVisible(true);
		}

		start.setVisible(true);
		int players;

		// get the number of players playing

		do {
			players = getPlayerNumbers(); //GUI method that allows user to click buttons
		} while (players == -1);

		start.setVisible(false);

		JDialog playerSelect = new JDialog();
		playerSelect.setLocation(435, 275);
		playerSelect.setResizable(false);
		playerSelect.setBackground(WallTile.wallColor);
		playerSelect.setSize(new Dimension(280, 300));
		playerSelect.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		JPanel playerSelectPanel = new JPanel();
		playerSelectPanel.setLayout(new GridLayout(2, 1));
		playerSelectPanel.setBackground(WallTile.wallColor);
		playerSelectPanel.setBorder(BorderFactory.createLineBorder(Color.white, 4));
		playerSelect.add(playerSelectPanel);

		JLabel playerSelectLabel = new JLabel("");
		playerSelectLabel.setFont(new Font("Montserrat", Font.PLAIN, 15));
		playerSelectLabel.setForeground(RoomTile.lightRoomTile);
		playerSelectLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerSelectLabel.setVisible(true);
		playerSelectPanel.add(playerSelectLabel);

		JPanel charButtons = new JPanel();
		charButtons.setLayout(new GridLayout(6, 1));
		playerSelectPanel.add(charButtons);

		for (Map.Entry<PersonCard.PersonType, JRadioButton> e: people.entrySet()){
			charButtons.add(e.getValue());
			e.getValue().setVisible(true);
		}
		playerSelect.setVisible(true);

		//let players select characters
		int index = 1;
		while (this.players.size() < players){
			playerSelectLabel.setText("Player " + index + " pick a character");
			PersonCard.PersonType selected = chooseChar();
			this.players.add(new Player(selected));
			index++;
		}
		for (JRadioButton button : people.values()){
			button.setVisible(false);
		}
		playerSelect.setVisible(false);

		JDialog readySelect = new JDialog();
		readySelect.setLocation(435, 275);
		readySelect.setResizable(false);
		readySelect.setBackground(WallTile.wallColor);
		readySelect.setSize(new Dimension(280, 150));
		readySelect.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		JPanel readySelectPanel = new JPanel();
		readySelectPanel.setLayout(new GridLayout(2, 1));
		readySelectPanel.setBackground(WallTile.wallColor);
		readySelectPanel.setBorder(BorderFactory.createLineBorder(Color.white, 4));

		JLabel readyLabel = new JLabel("Ready to shuffle, deal and play?");
		readyLabel.setFont(new Font("Montserrat", Font.PLAIN, 15));
		readyLabel.setForeground(RoomTile.lightRoomTile);
		readyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		readyLabel.setVisible(true);
		readySelectPanel.add(readyLabel);

		readySelectPanel.add(ready);
		readySelect.add(readySelectPanel);
		readySelect.setVisible(true);

		//waits for the player to say they are ready
		boolean ready;
		do {
			ready = doReady();
		} while (!ready);

		this.ready.setVisible(false);
		readySelect.setVisible(false);
		
		//deal cards and displays information to the players
		dealDeck();

		//create the board
		try {
			board = new Board(Board.DEFAULT_BOARD, this.players);
		} catch (BoardFormatException e) {
			System.out.println("Oops!\n" + e.getCause());
		}

		List<WeaponCard.WeaponType> weapons = new ArrayList<>(Arrays.asList(WeaponCard.WeaponType.values()));
		List<RoomCard.RoomType> rooms = new ArrayList<>(Arrays.asList(RoomCard.RoomType.values()));
		Collections.shuffle(rooms);
		int roomToPut = 0;
		for (WeaponCard.WeaponType w: weapons){
			weaponsInRoom.put(w, rooms.get(roomToPut));
			roomToPut++;
		}

		for (Map.Entry<WeaponCard.WeaponType, RoomCard.RoomType> e: weaponsInRoom.entrySet()){
			textArea.append("The weapon " + e.getKey() + " is in the room " + e.getValue() + "\n");
		}
	}

	public PersonCard.PersonType chooseChar(){
		final PersonCard.PersonType[] selected = new PersonCard.PersonType[1];
		for (JRadioButton p : people.values()){
			p.setVisible(true);
		}
		while (selected[0] == null) {
			for (PersonCard.PersonType pt : people.keySet()) {
				JRadioButton button = people.get(pt);
				button.addActionListener(e -> {
					button.setEnabled(false);
					button.setSelected(true);
					selected[0] = pt;
				});
			}
		}
		return selected[0];
	}

	/**
	 * New getPlayerNumber method to select number of players: uses buttons
	 */
	private int plSelected = -1;
	public int getPlayerNumbers(){
		for (JButton b : playerNumbers){
			b.addActionListener(e -> {
				plSelected = 3 + playerNumbers.indexOf(b);
				for (JButton button : playerNumbers){
					button.setVisible(false);
				}
			});
		}
		return plSelected;
	}

	/**
	 * New yes/no method: uses buttons
	 */
	private boolean answer;
	public boolean yesOrNo(){
		final boolean[] answered = new boolean[1]; //checks if an answer has been selected
		while (!answered[0]) {
			yes.setVisible(true);
			no.setVisible(true);
			yes.addActionListener(e -> {
				answered[0] = true;
				answer = true;
				yes.setVisible(false);
				no.setVisible(false);
			});
			no.addActionListener(e -> {
				answered[0] = true;
				answer = false;
				yes.setVisible(false);
				no.setVisible(false);
			});
		}
		return answer;
	}

	public boolean doReady(){
		final boolean[] bReady = new boolean[1];
		ready.setVisible(true);
		while (!bReady[0]) {
			ready.addActionListener(e -> bReady[0] = true);
		}
		return true;
	}

	/**
	 *  @param diceNumber the number rolled
	 * @param p player
	 */
	public void doMoves(int diceNumber, Player p, Graphics g){
		StringBuilder moveString;
		int movesTaken;
		do {
			boardGraphics.updateUI();
			movesTaken = 0;
			boolean first = true;
			moveString = new StringBuilder();
			while (movesTaken < diceNumber) {
				ActionListener listener = e -> {
					JButton button = (JButton) e.getSource();
					if (button == left) {
						mLeft = true;
					}
					if (button == right) {
						mRight = true;
					}
					if (button == up) {
						mUp = true;
					}
					if (button == down) {
						mDown = true;
					}
				};

				if (first) {
					left.addActionListener(listener);
					right.addActionListener(listener);
					down.addActionListener(listener);
					up.addActionListener(listener);
				}
				//boardGraphics.updateUI();
				int result = -1;
				if (mLeft) {
					if ((result = board.drawMove(moveString + "l", movesTaken + 1, p, g)) > 0) {
						movesTaken++;
						moveString.append("l");
						currentMove = moveString.toString();
						currentMoveCount = movesTaken;
					}
					mLeft = false;
				} else if (mRight) {
					if ((result = board.drawMove(moveString + "r", movesTaken + 1, p, g)) > 0) {
						movesTaken++;
						moveString.append("r");
						currentMove = moveString.toString();
						currentMoveCount = movesTaken;
					}
					mRight = false;
				} else if (mUp) {
					if ((result = board.drawMove(moveString + "u", movesTaken + 1, p, g)) > 0) {
						movesTaken++;
						moveString.append("u");
						currentMove = moveString.toString();
						currentMoveCount = movesTaken;
					}
					mUp = false;
				} else if (mDown) {
					if ((result = board.drawMove(moveString + "d", movesTaken + 1, p, g)) > 0) {
						movesTaken++;
						moveString.append("d");
						currentMove = moveString.toString();
						currentMoveCount = movesTaken;
					}
					mDown = false;
				}
				if (result == 2)
					break;
				first = false;
				if (movesTaken == diceNumber - 1) { //if last loop
					left.removeActionListener(listener);
					right.removeActionListener(listener);
					up.removeActionListener(listener);
					down.removeActionListener(listener);
				}
			}
			boardGraphics.updateUI();
		} while (!board.movePlayer(moveString.toString(), movesTaken, p));
		currentMove = "";
		currentMoveCount = 0;
	}

	public void clearSelections(){
		for (JButton button : characters.values()) {
			button.setBackground(new Color(0x1f1d1d));
			button.setVisible(false);
		}
		for (JButton button : weapons.values()) {
			button.setBackground(new Color(0x1f1d1d));
			button.setVisible(false);
		}
		for (JButton button : rooms.values()){
			button.setBackground(new Color(0x1f1d1d));
			button.setVisible(false);
		}
	}

	/**
	 * gets a player from a specific playerType
	 * @param p the personType player to find
	 * @return the player with the given personType, null if not in game
	 */
	private Player getPlayerFromType(PersonCard.PersonType p){
		for (Player player: players){
			if (player.personType == p){
				return player;
			}
		}
		return null;
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

	public static void main(String[] args) {
		Game game = new Game();
		game.initialise();
		game.play();
	}
}
