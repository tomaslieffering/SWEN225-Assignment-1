package Cluedo;

import Cluedo.Card.Card;
import Cluedo.Card.PersonCard;
import Cluedo.Card.RoomCard;
import Cluedo.Card.WeaponCard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        return (int)(Math.random()*6) + 1;
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
     *  Checks if other players have cards that can disprove the suggestion just made
     * @param p - player who made the suggestion
     * @param suggestion the suggestion that has been suggested
     */
    public Card disproveSuggestion(Player p, Suggestion suggestion, Game game){
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
                        card = chooseCard(playerNumber, cards, game);
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

    public Suggestion makeSuggestion(RoomCard.RoomType r, boolean accusation, Game g){
        final PersonCard.PersonType[] pers = new PersonCard.PersonType[1];
        final RoomCard.RoomType[] room = new RoomCard.RoomType[1];
        final WeaponCard.WeaponType[] weap = new WeaponCard.WeaponType[1];
        g.pLabel.setVisible(true);
        for (JButton button : g.characters.values()){
            button.setVisible(true);
        }
        if (accusation){
            g.rLabel.setVisible(true);
            for (JButton button : g.rooms.values()){
                button.setVisible(true);
            }
        }
        g.wLabel.setVisible(true);
        for (JButton button : g.weapons.values()){
            button.setVisible(true);
        }
        while (pers[0] == null) {
            for (PersonCard.PersonType pt : g.characters.keySet()) {
                JButton button = g.characters.get(pt);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        pers[0] = pt;
                    }
                });
            }
        }
        if (accusation) {
            while (room[0] == null) {
                for (RoomCard.RoomType rt : g.rooms.keySet()) {
                    JButton button = g.rooms.get(rt);
                    button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            room[0] = rt;
                        }
                    });
                }
            }
        } else {
            room[0] = r;
        }
        while (weap[0] == null) {
            for (WeaponCard.WeaponType wt : g.weapons.keySet()) {
                JButton button = g.weapons.get(wt);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        weap[0] = wt;
                    }
                });
            }
        }
        Suggestion suggestion = new Suggestion(pers[0], room[0], weap[0]);
        return suggestion;
    }

    public Card chooseCard(int playerNumber, ArrayList<Card> cards, Game game){
        System.out.println("Player " + (playerNumber + 1) + ": You have more than one card suitable to refute this suggestion.");
        System.out.println("Which would you like to choose?");
        final Card[] chosen = {null};
        Map<Card, JButton> selections = new HashMap<>();
        for (Card c : cards){
            JButton card = new JButton(c.toString());
            selections.put(c,card);
            game.controls.add(card);
        }
        while (chosen[0] == null) {
            for (Card c : selections.keySet()) {
                JButton button = selections.get(c);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        chosen[0] = c;
                    }
                });
            }
        }
        for (JButton button : selections.values()){
            button.setVisible(false);
        }
        return chosen[0];
    }
}
