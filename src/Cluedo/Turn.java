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

    public Suggestion makeSuggestion(Player p, RoomCard.RoomType r){
        Scanner sc = new Scanner(System.in);
                System.out.println("MISS_SCARLETT   : 1 \n"+
                        "COLONEL_MUSTARD : 2\n"+
                        "MRS_WHITE       : 3\n"+
                        "MR_GREEN        : 4\n"+
                        "MRS_PEACOCK     : 5\n"+
                        "PROFESSOR_PLUM  : 6\n");
                System.out.println("Enter the number for your suggestion of the character");
                int person;
                do {
                    person = suggestperson(sc);
                } while (person == -1);
                PersonCard.PersonType personCard = null;
                switch (person){
                    case 1:
                        personCard = PersonCard.PersonType.MISS_SCARLETT;
                    case 2:
                        personCard = PersonCard.PersonType.COLONEL_MUSTARD;
                    case 3:
                        personCard = PersonCard.PersonType.MRS_WHITE;
                    case 4:
                        personCard = PersonCard.PersonType.MR_GREEN;
                    case 5:
                        personCard = PersonCard.PersonType.MRS_PEACOCK;
                    case 6:
                        personCard = PersonCard.PersonType.PROFESSOR_PLUM;
        }
                System.out.println("KITCHEN        : 1 \n"+
                        "BALL_ROOM      : 2 \n"+
                        "CONSERVATORY   : 3 \n"+
                        "BILLIARD_ROOM  : 4 \n"+
                        "DINING_ROOM    : 5 \n"+
                        "LIBRARY        : 6 \n"+
                        "LOUNGE         : 7 \n"+
                        "HALL           : 8 \n"+
                        "STUDY          : 9 \n");
                System.out.println("Enter the number for your suggestion of the room");
                RoomCard.RoomType roomCard = null;
                int room;
                do {
                    room = suggestroom(sc);
                } while (room == -1);
                switch (room){
                    case 1:
                        roomCard = RoomCard.RoomType.KITCHEN;
                    case 2:
                        roomCard = RoomCard.RoomType.BALL_ROOM;
                    case 3:
                        roomCard = RoomCard.RoomType.CONSERVATORY;
                    case 4:
                        roomCard = RoomCard.RoomType.BILLIARD_ROOM;
                    case 5:
                        roomCard = RoomCard.RoomType.DINING_ROOM;
                    case 6:
                        roomCard = RoomCard.RoomType.LIBRARY;
                    case 7:
                        roomCard = RoomCard.RoomType.LOUNGE;
                    case 8:
                        roomCard = RoomCard.RoomType.HALL;
                    case 9:
                        roomCard = RoomCard.RoomType.STUDY;
                }
                System.out.println("CANDLESTICK : 1 \n"+
                        "DAGGER      : 2 \n"+
                        "LEAD_PIPE   : 3 \n"+
                        "REVOLVER    : 4 \n"+
                        "ROPE        : 5 \n"+
                        "SPANNER     : 6 \n");
                System.out.println("Enter the number for your suggestion of the tool");
                int tool;
                do {
                    tool = suggesttool(sc);
                } while (tool == -1);
                WeaponCard.WeaponType weaponCard = null;
                switch(tool){
                    case 1:
                        weaponCard = WeaponCard.WeaponType.CANDLESTICK;
                    case 2:
                        weaponCard = WeaponCard.WeaponType.DAGGER;
                    case 3:
                        weaponCard = WeaponCard.WeaponType.LEAD_PIPE;
                    case 4:
                        weaponCard = WeaponCard.WeaponType.REVOLVER;
                    case 5:
                        weaponCard = WeaponCard.WeaponType.ROPE;
                    case 6:
                        weaponCard = WeaponCard.WeaponType.SPANNER;
                }
                Suggestion suggestion = new Suggestion(personCard, roomCard, weaponCard);
                return suggestion;
    }

            private int suggestperson(Scanner sc) {
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

            private int suggestroom(Scanner sc) {
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

            private int suggesttool(Scanner sc) {
                if (sc.hasNext()) {
                    try {
                        int tool = Integer.parseInt(sc.nextLine());
                        if (tool < 1 || tool > 6) {
                            System.out.println("Please enter a number between 1 and 6:");
                            return -1;
                        }
                        else {
                            return tool;
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
     *  Checks if other players have cards that can disprove the suggestion just made
     * @param p - player who made the suggestion
     * @param suggestion
     */
    public Card disproveSuggestion(Player p, Suggestion suggestion){
        System.out.println("A suggestion has been made. Does anyone have evidence to the contrary?");
        int originalPlayer = players.indexOf(p) + 1, playerNumber = players.indexOf(p), index = 0;
        boolean round = false, found = false;
        Card card = null;
        while (!round && !found){ //until all players have been asked or card has been found
            if (playerNumber < players.size()){
                if (!(players.get(playerNumber).equals(p))){
                    System.out.println("Checking with player " + playerNumber + "...");
                    Player next = players.get(playerNumber);
                    ArrayList<Card> cards = new ArrayList<Card>();
                    List<Card> plHand = next.hand;
                    for (Card c : plHand){
                        if (c.toString().equals(suggestion.person.toString())){
                            cards.add(c);
                        } else if (c.toString().equals(suggestion.room.toString())){
                            cards.add(c);
                        } else if (c.toString().equals(suggestion.weapon.toString())){
                            cards.add(c);
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
            } else {
                playerNumber = 0;
            }
            index++;
            if (index >= players.size()){
                round = true;
            }
        }
        if (round && !found) {
            System.out.println("No one knew enough to disprove your suggestion.");
            return null;
        } else {
            System.out.println("Player " + playerNumber + " whispers to Player " + originalPlayer);
            System.out.println("I have evidence against " + card.toString());
            return card;
        }
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
