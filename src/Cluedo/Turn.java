package Cluedo;

import Cluedo.Card.Card;
import Cluedo.Card.PersonCard;
import Cluedo.Card.RoomCard;
import Cluedo.Card.WeaponCard;

import java.util.*;

public class Turn {
    private final List<Player> players;

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

    /**
     * asks the player for the suggestion it wants to create and creates it
     * @param p the player
     * @param r the roomType
     * @param accusation is it an accusation?
     * @return the suggestion object
     */
    public Suggestion makeSuggestion(Player p, RoomCard.RoomType r, boolean accusation){
        Scanner sc = new Scanner(System.in);
        System.out.println("MISS_SCARLETT   : 1 \n"+
                "COLONEL_MUSTARD : 2\n"+
                "MRS_WHITE       : 3\n"+
                "MR_GREEN        : 4\n"+
                "MRS_PEACOCK     : 5\n"+
                "PROFESSOR_PLUM  : 6\n");
        System.out.println("Enter a number to select a character");
        int person;
        do person = suggestPerson(sc); while (person == -1);
        PersonCard.PersonType personCard = null;
        switch (Integer.toString(person)){
            case "1":
                personCard = PersonCard.PersonType.MISS_SCARLETT;
                break;
            case "2":
                personCard = PersonCard.PersonType.COLONEL_MUSTARD;
                break;
            case "3":
                personCard = PersonCard.PersonType.MRS_WHITE;
                break;
            case "4":
                personCard = PersonCard.PersonType.MR_GREEN;
                break;
            case "5":
                personCard = PersonCard.PersonType.MRS_PEACOCK;
                break;
            case "6":
                personCard = PersonCard.PersonType.PROFESSOR_PLUM;
                break;
        }
        RoomCard.RoomType roomCard = r;
        if (accusation) {
            System.out.println("KITCHEN        : 1 \n" +
                    "BALL_ROOM      : 2 \n" +
                    "CONSERVATORY   : 3 \n" +
                    "BILLIARD_ROOM  : 4 \n" +
                    "DINING_ROOM    : 5 \n" +
                    "LIBRARY        : 6 \n" +
                    "LOUNGE         : 7 \n" +
                    "HALL           : 8 \n" +
                    "STUDY          : 9 \n");
            System.out.println("Enter a number to select a room");
            int room;
            do {
                room = suggestRoom(sc);
            } while (room == -1);
            switch (Integer.toString(room)) {
                case "1":
                    roomCard = RoomCard.RoomType.KITCHEN;
                    break;
                case "2":
                    roomCard = RoomCard.RoomType.BALL_ROOM;
                    break;
                case "3":
                    roomCard = RoomCard.RoomType.CONSERVATORY;
                    break;
                case "4":
                    roomCard = RoomCard.RoomType.BILLIARD_ROOM;
                    break;
                case "5":
                    roomCard = RoomCard.RoomType.DINING_ROOM;
                    break;
                case "6":
                    roomCard = RoomCard.RoomType.LIBRARY;
                    break;
                case "7":
                    roomCard = RoomCard.RoomType.LOUNGE;
                    break;
                case "8":
                    roomCard = RoomCard.RoomType.HALL;
                    break;
                case "9":
                    roomCard = RoomCard.RoomType.STUDY;
                    break;
            }
        }
        System.out.println("CANDLESTICK : 1 \n"+
                "DAGGER      : 2 \n"+
                "LEAD_PIPE   : 3 \n"+
                "REVOLVER    : 4 \n"+
                "ROPE        : 5 \n"+
                "SPANNER     : 6 \n");
        System.out.println("Enter a number to select a tool");
        int tool;
        do {
            tool = suggestTool(sc);
        } while (tool == -1);
        WeaponCard.WeaponType weaponCard = null;
        switch(Integer.toString(tool)){
            case "1":
                weaponCard = WeaponCard.WeaponType.CANDLESTICK;
                break;
            case "2":
                weaponCard = WeaponCard.WeaponType.DAGGER;
                break;
            case "3":
                weaponCard = WeaponCard.WeaponType.LEAD_PIPE;
                break;
            case "4":
                weaponCard = WeaponCard.WeaponType.REVOLVER;
                break;
            case "5":
                weaponCard = WeaponCard.WeaponType.ROPE;
                break;
            case "6":
                weaponCard = WeaponCard.WeaponType.SPANNER;
                break;
        }
        return new Suggestion(personCard, roomCard, weaponCard);
    }

    /**
     * get a personType from the player
     * @param sc the system.in scanner
     * @return the number the player wants to select
     */
    private int suggestPerson(Scanner sc) {
        return suggestSomething(sc);
    }

    /**
     * helper method for suggesting something
     * @param sc the system.in
     * @return the number of the thing theplayer wants to suggest
     */
    private int suggestSomething(Scanner sc) {
        if (sc.hasNext()) {
            try {
                int person = Integer.parseInt(sc.nextLine());
                if (person < 1 || person > 6) {
                    System.out.println("Please enter a number between 1 and 6:");
                    return -1;
                }
                else {
                    return person;
                }
            }
            catch (Exception e) {
                System.out.println("Please enter a integer number:");
                return -1;
            }
        }
        return 0;
    }

    /**
     * get number to select a room for a suggestion
     * @param sc the system.in scanner
     * @return the number of the thing theplayer wants to suggest
     */
    private int suggestRoom(Scanner sc) {
        if (sc.hasNext()) {
            try {
                int room = Integer.parseInt(sc.nextLine());
                if (room < 1 || room > 9) {
                    System.out.println("Please enter a number between 1 and 9:");
                    return -1;
                }
                else {
                    return room;
                }
            }
            catch (Exception e) {
                System.out.println("Please enter a integer number:");
                return -1;
            }
        }
        return 0;
    }

    private int suggestTool(Scanner sc) {
        return suggestSomething(sc);
    }

    /**
     *  Checks if other players have cards that can disprove the suggestion just made
     * @param p - player who made the suggestion
     * @param suggestion the suggestion that has been suggested
     */
    public Card disproveSuggestion(Player p, Suggestion suggestion){
        System.out.println("A suggestion has been made. Does anyone have evidence to the contrary?");
        int originalPlayer = players.indexOf(p), playerNumber = players.indexOf(p), index = 0;
        boolean round = false, found = false;
        Card card = null;
        while (!round && !found) { //until all players have been asked or card has been found
            if (playerNumber < players.size()) {
                if (!(players.get(playerNumber).equals(p))) {
                    System.out.println("Checking with player " + (playerNumber + 1) + "...");
                    Player next = players.get(playerNumber);
                    ArrayList<Card> cards = new ArrayList<Card>();
                    List<Card> plHand = next.hand;
                    for (Card c : plHand) {
                        if (c.toString().equals(suggestion.person.toString())) {
                            cards.add(c);
                        } else if (c.toString().equals(suggestion.room.toString())) {
                            cards.add(c);
                        } else if (c.toString().equals(suggestion.weapon.toString())) {
                            cards.add(c);
                        }
                    }
                    if (cards.size() == 1) {
                        found = true;
                        card = cards.get(0);
                        System.out.println("Player " + (playerNumber + 1)  + " (" + players.get(playerNumber).personType.toString() + ")");
                        System.out.print(" whispers to Player " + (originalPlayer + 1) + " (" + players.get(originalPlayer).personType.toString() + ")");
                        System.out.println(" I have evidence against " + card.toString());
                    } else if (cards.size() >= 2) {
                        found = true;
                        card = chooseCard(playerNumber, cards);
                        System.out.println("Player " + (playerNumber + 1) + " (" + players.get(playerNumber).personType.toString() + ")");
                        System.out.print(" whispers to Player " + (originalPlayer + 1) + " (" + players.get(originalPlayer).personType.toString() + ")");
                        System.out.println(" I have evidence against " + card.toString());
                    } else {
                        System.out.println("Player " + (playerNumber + 1) + " cannot help.");
                    }
                }
                playerNumber++;
            } else {
                playerNumber = 1;
            }
            index++;
            if (index >= players.size()) {
                round = true;
            }
        }
        return card;
    }

    /**
     * After an accusation has been made, check if the player wins or loses the game
     * @param p the player who made the accusation
     * @param suggestion the suggestion the accusation is based of
     * @param envelope the envelope with the actual murder circumstance
     * @return whether the accusation was correct
     */
    public boolean accusationCheck(Player p, Suggestion suggestion, Set<Card> envelope){
        int found = 0;
        for (Card c : envelope){
            if (c.toString().equals(suggestion.person.toString())){
                found++;
            } else if (c.toString().equals(suggestion.room.toString())){
                found++;
            } else if (c.toString().equals(suggestion.weapon.toString())){
                found++;
            }
        }
        if (found == 3){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Helper method for player input choose between mutliple cards to disprove
     * @param playerNumber what player needs to make a choice
     * @param cards the cords to be chosen from
     * @return the selected card
     */
    private Card chooseCard(int playerNumber, ArrayList<Card> cards) {
        System.out.println("Player " + (playerNumber + 1) + ": You have more than one card suitable to refute this suggestion.");
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
     * helper method to get the selected card from the player
     * @param sc the system.in scanner
     * @param size how many cards there is to pick from
     * @return the number of the card that was selected
     */
    public static int getCardNumber(Scanner sc, int size) {
        // if there is something that has been typed
        if (sc.hasNext()) {
            try {
                // get the inputed string and try parsing it to a int
                int card = Integer.parseInt(sc.nextLine());
                // if the wrong number of player
                if (card < 1 || card > size) {
                    System.out.println("Please enter a number between 1 and 3:");
                    return -1;
                }
                // else everything is correct, return the number of players
                else {
                    return card;
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
     * Helper method for choice between cards from input
     *
     * @param sc the system.in scanner
     * @param cards the cards to pick from
     * @return the card choosen
     */
    private Card cardChoice(Scanner sc, ArrayList<Card> cards) {
        boolean picked = false;
        int card = -1;
        do card = getCardNumber(sc, cards.size()); while (card == -1);
        String str = Integer.toString(card);
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
                    System.out.println("There are only two options! Please pick 1 or 2");
                }
        }

        return null;
    }
}
