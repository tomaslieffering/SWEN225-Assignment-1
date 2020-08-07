package Cluedo;

import Cluedo.Board.Board;
import Cluedo.Board.RoomTile;
import Cluedo.Card.Card;
import Cluedo.Card.PersonCard;
import Cluedo.Card.RoomCard;
import Cluedo.Card.WeaponCard;
import Cluedo.Game;

import java.util.*;

import static Cluedo.Card.PersonCard.PersonType.NO_PLAYER;
import static Cluedo.Card.PersonCard.PersonType.PROFESSOR_PLUM;

public class Turn {
    private List<Player> players;

    Turn(List<Player> players) {
        this.players = players;
    }

    /**
     * generates two random numbers between 1 and 6
     * @return the sum of the two numbers generated
     */
    public static int rollDice() {
        int diceOne = (int)(Math.random()*6) + 1;
        int diceTwo = (int)(Math.random()*6) + 1;
        printDice(diceOne);
        printDice(diceTwo);
        System.out.println("= " + (diceOne + diceTwo));
        return diceOne + diceTwo;
    }

    /**
     * Prints a unicode representation of the dice rolled
     * @param dice the dice to print
     */
    private static void printDice(int dice) {
        switch (Integer.toString(dice)){
            case "1":
                System.out.println("\u2680" + "1");
                break;
            case "2":
                System.out.println("\u2681" + "2");
                break;
            case "3":
                System.out.println("\u2682" + "3");
                break;
            case "4":
                System.out.println("\u2683" + "4");
                break;
            case "5":
                System.out.println("\u2684" + "5");
                break;
            case "6":
                System.out.println("\u2685" + "6");
                break;
        }
    }


    //TODO: for testing, remove
    public static void main(String[] args){
        int players = 3;
        List<Player> subPlayers = new ArrayList<Player>();
        // get the list of all possible players
        List<PersonCard.PersonType> characters = new ArrayList<>(Arrays.asList(PersonCard.PersonType.values()));

        // assign character to players randomly using Collection.shuffle
        for (int i = 0; i < players; i++) {
            Collections.shuffle(characters);
            if (!characters.get(0).toString().equals(PersonCard.PersonType.NO_PLAYER.toString())){
                subPlayers.add(new Player(characters.get(0)));
            }
            else{
                i--;
            }
            characters.remove(0);
        }

        Turn t = new Turn(subPlayers);
        Player p = t.players.get(0);
        RoomTile r = new RoomTile(RoomTile.RoomType.BALL_ROOM);
        Suggestion sug = t.makeSuggestion(p,r);
        Card c = t.disproveSuggestion(p, sug);
    }

    //TODO - replace dummy class when made
    public Suggestion makeSuggestion(Player p, RoomTile r){
        Suggestion suggestion = new Suggestion(PersonCard.PersonType.PROFESSOR_PLUM, RoomCard.RoomType.BALL_ROOM, WeaponCard.WeaponType.CANDLESTICK);
        return suggestion;
    }

    /**
     *  Checks if other players have cards that can disprove the suggestion just made
     * @param p - player who made the suggestion
     * @param suggestion
     */
    public Card disproveSuggestion(Player p, Suggestion suggestion){
        System.out.println("A suggestion has been made. Does anyone have evidence to the contrary?");
        int originalPlayer = players.indexOf(p), playerNumber = players.indexOf(p), index = 0;
        boolean round = false, found = false;
        Card card = null;
        while (!round || !found){ //until all players have been asked or card has been found
            if (playerNumber < players.size()){
                if (!(players.get(playerNumber).equals(p))){
                    System.out.println("Checking with player " + playerNumber + "...");
                    Player next = players.get(playerNumber);
                    ArrayList<Card> cards = new ArrayList<Card>();
                    if (next.hand.contains(suggestion.person)){
                        Set<PersonCard> cardSet = (Set<PersonCard>)PersonCard.getAllCards();
                        for (PersonCard c : cardSet){
                            if (c.getType().equals(suggestion.person)){
                                cards.add(c);
                            }
                        }
                    } else if (next.hand.contains(suggestion.room)){
                        Set<RoomCard> cardSet = (Set<RoomCard>)RoomCard.getAllCards();
                        for (RoomCard c : cardSet){
                            if (c.getType().equals(suggestion.person)){
                                cards.add(c);
                            }
                        }
                    } else if (next.hand.contains(suggestion.weapon)){
                        Set<WeaponCard> cardSet = (Set<WeaponCard>)WeaponCard.getAllCards();
                        for (WeaponCard c : cardSet){
                            if (c.getType().equals(suggestion.person)){
                                cards.add(c);
                            }
                        }
                    }

                        if (cards.size() == 1) {
                            found = true;
                            card = cards.get(0);
                        } else if (cards.size() >= 2) {
                            found = true;
                            card = chooseCard(playerNumber, cards);
                        } else if (cards.size() == 0) {
                            System.out.println("Player " + playerNumber + " cannot help.");
                        }

                }
                playerNumber++;
                index++;
                if (index >= players.size()){
                    round = true;
                }
            } else {
                playerNumber = 0;
                index++;
                if (index >= players.size()){
                    round = true;
                }
            }
        }
        System.out.println("Player " + playerNumber + " whispers to Player " + originalPlayer);
        System.out.println("I have evidence against " + card.toString());
        return card;
    }

    /**
     * Helper method for player input choose between mutliple cards to disprove
     * @param playerNumber
     * @param cards
     * @return
     */
    private Card chooseCard(int playerNumber, ArrayList<Card> cards) {
        System.out.println("Player " + playerNumber + ": You have more than one card suitable to refute this suggestion.");
        System.out.println("Which would you like to choose?");
        int index = 1;
        for (Card c : cards){
            System.out.println(index + ". " + c.toString());
            index++;
        }
        Scanner sc = new Scanner(System.in);
        Card chosen = cardChoice(sc, cards);
        return chosen;
    }

    /**
     * Helper method for choice between cards from input
     *
     * @param sc
     * @param cards
     * @return
     */
    private Card cardChoice(Scanner sc, ArrayList<Card> cards){
        boolean picked = false;
        while (!picked) {
            String str = sc.next();
            switch (str) {
                case "1":
                    picked = true;
                    return cards.get(0);
                case "2":
                    picked = true;
                    return cards.get(1);
                case "3":
                    if (cards.size() > 2) {
                        picked = true;
                        return cards.get(2);
                    } else {
                        picked = false;
                        System.out.println("There are only two options! Please pick 1 or 2");
                    }
            }
        }
        return null;
    }
    //checking accusation
}
