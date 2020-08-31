package Cluedo;

import Cluedo.Board.RoomTile;
import Cluedo.Board.WallTile;
import Cluedo.Card.Card;
import Cluedo.Card.PersonCard;
import Cluedo.Card.RoomCard;
import Cluedo.Card.WeaponCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

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
                        game.textArea.append("Player " + (playerNumber + 1)  + " (" + players.get(playerNumber).personType.toString() + ")\n");
                        game.textArea.append(" whispers to Player " + (originalPlayer + 1) + " (" + players.get(originalPlayer).personType.toString() + ")\n");
                        game.textArea.append(" I have evidence against " + card.toString() + "\n");
                    } else if (cards.size() >= 2) {
                        found = true;
                        game.textArea.append("Player " + playerNumber + " you have more than one thing that can refute, what do you want to choose?");
                        card = chooseCard(playerNumber, cards, game);
                        game.textArea.append("Player " + (playerNumber + 1) + " (" + players.get(playerNumber).personType.toString() + ")\n");
                        game.textArea.append(" whispers to Player " + (originalPlayer + 1) + " (" + players.get(originalPlayer).personType.toString() + ")\n");
                        game.textArea.append(" I have evidence against " + card.toString() + "\n");
                    } else {
                        game.textArea.append("Player " + (playerNumber + 1) + " cannot help.\n");
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
        g.clearSelections();
        for (JButton button : g.characters.values()){
            button.setVisible(true);
        }
        for (JButton button : g.rooms.values()){
            button.setVisible(true);
        }
        if (!accusation){
            room[0] = r;
            g.rooms.get(r).setBackground(new Color(0x75525D));
        }
        for (JButton button : g.weapons.values()){
            button.setVisible(true);
        }
        while (pers[0] == null) {
            for (PersonCard.PersonType pt : g.characters.keySet()) {
                JButton button = g.characters.get(pt);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        for (JButton b : g.characters.values()){
                            b.setBackground(new Color(0x1f1d1d));
                        }
                        pers[0] = pt;
                        button.setBackground(new Color(0x75525D));
                    }
                });
            }
        }
        while (weap[0] == null) {
            for (WeaponCard.WeaponType wt : g.weapons.keySet()) {
                JButton button = g.weapons.get(wt);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        for (JButton b : g.weapons.values()){
                            b.setBackground(new Color(0x1f1d1d));
                        }
                        weap[0] = wt;
                        button.setBackground(new Color(0x75525D));
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
                            for (JButton b : g.rooms.values()){
                                b.setBackground(new Color(0x1f1d1d));
                            }
                            room[0] = rt;
                            button.setBackground(new Color(0x75525D));
                        }
                    });
                }
            }
        }
        Suggestion suggestion = new Suggestion(pers[0], room[0], weap[0]);
        return suggestion;
    }

    public Card chooseCard(int playerNumber, ArrayList<Card> cards, Game game){
        Card[] chosen = {null};
        Map<Card, JButton> selections = new HashMap<>();
        for (Card c : cards){
            JButton card = new JButton(c.toString());
            selections.put(c,card);
            game.controls.add(card);
            card.setBackground(new Color(0x1f1d1d));
            card.setForeground(WallTile.lightRoomTile);
            card.setFont(new Font("Montserrat", Font.BOLD, 15));
            card.setBorderPainted(false);
        }
        ArrayList<JButton> buttons = new ArrayList<>();
        for (JButton b : selections.values()){
            buttons.add(b);
        }
        if (buttons.size() == 2) {
            game.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttons.get(0), 0, SpringLayout.HORIZONTAL_CENTER, game.controls);
            game.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttons.get(1), 0, SpringLayout.HORIZONTAL_CENTER, game.controls);
            game.layout.putConstraint(SpringLayout.NORTH, buttons.get(0), 50, SpringLayout.NORTH, game.controls);
            game.layout.putConstraint(SpringLayout.NORTH, buttons.get(1), 100, SpringLayout.NORTH, game.controls);
        } else if (buttons.size() == 3){
            game.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttons.get(0), 0, SpringLayout.HORIZONTAL_CENTER, game.controls);
            game.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttons.get(1), 0, SpringLayout.HORIZONTAL_CENTER, game.controls);
            game.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttons.get(2), 0, SpringLayout.HORIZONTAL_CENTER, game.controls);
            game.layout.putConstraint(SpringLayout.NORTH, buttons.get(0), 50, SpringLayout.NORTH, game.controls);
            game.layout.putConstraint(SpringLayout.NORTH, buttons.get(1), 100, SpringLayout.NORTH, game.controls);
            game.layout.putConstraint(SpringLayout.NORTH, buttons.get(2), 150, SpringLayout.NORTH, game.controls);
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
        for (JButton b : selections.values()){
            b.setVisible(false);
        }
        return chosen[0];
    }
}
