package Cluedo;

import Cluedo.Card.Card;
import Cluedo.Card.PersonCard;
import Cluedo.Card.RoomCard;
import Cluedo.Card.WeaponCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GUI {
    public JMenuBar menubar;
    public JFrame window;
    public JTextArea textArea;
    public JPanel controls;
    public JPanel boardGraphics;
    public JPanel cardGraphics;

    public GUI() {
        initialize();
    }

    protected abstract void drawBoard(Graphics g);

    protected abstract void drawCards(Graphics g);

    private void setupTextArea() {}

    protected ArrayList<JButton> playerNumbers = new ArrayList<>();
    protected JButton yes, no, ready;
    protected JButton left, right, up, down;
    protected Map<PersonCard.PersonType, JRadioButton> people = new HashMap<>();
    protected Map<PersonCard.PersonType, JButton> characters = new HashMap<>();
    protected Map<WeaponCard.WeaponType, JButton> weapons = new HashMap<>();
    protected Map<RoomCard.RoomType, JButton> rooms = new HashMap<>();
    protected JLabel pLabel, wLabel, rLabel;
     
    
    private void setupControls() {
        /*
        //todo set positions of arrows
        left = new JButton("←");
        right = new JButton("→");
        up = new JButton("↑");
        down = new JButton("↓");

        controls.add(left);
        controls.add(right);
        controls.add(up);
        controls.add(down);
         */
        //todo input number of players --> make pop-up?
        playerNumbers.add(new JButton("3"));
        playerNumbers.add(new JButton("4"));
        playerNumbers.add(new JButton("5"));
        playerNumbers.add(new JButton("6"));
        for (JButton b : playerNumbers){
            controls.add(b);
            b.setVisible(false);
        }
        //Todo yes/no selection --> make pop-up?
        yes = new JButton("Yes");
        no = new JButton("No");
        ready = new JButton("Ready");
        controls.add(yes);
        controls.add(no);
        controls.add(ready);
        yes.setVisible(false);
        no.setVisible(false);
        ready.setVisible(false);

        Box chars = Box.createVerticalBox();

        //person selection buttons
        pLabel = new JLabel("People: ");
        chars.add(pLabel);
        pLabel.setVisible(false);

        for (PersonCard.PersonType pers : PersonCard.PersonType.values()){
            characters.put(pers, new JButton(pers.toString()));
            people.put(pers, new JRadioButton(pers.toString(), false));
        }
        for (JButton character : characters.values()){
            chars.add(character);
            character.setVisible(false);
        }
        for (JRadioButton person : people.values()){
            chars.add(person);
            person.setVisible(false);
        }

        //weapon selection buttons
        wLabel = new JLabel("Weapons: ");
        chars.add(wLabel);
        wLabel.setVisible(false);

        for (WeaponCard.WeaponType weap : WeaponCard.WeaponType.values()){
            weapons.put(weap, new JButton(weap.toString()));
        }
        for (JButton w : weapons.values()){
            chars.add(w);
            w.setVisible(false);
        }

        //room selection buttons
        rLabel = new JLabel("Rooms: ");
        chars.add(rLabel);
        rLabel.setVisible(false);

        for (RoomCard.RoomType room : RoomCard.RoomType.values()){
            rooms.put(room, new JButton(room.toString()));
        }
        for (JButton r : rooms.values()){
            chars.add(r);
            r.setVisible(false);
        }
        controls.add(chars);
    }

    private void setupBoardGraphics() {}

    private void setupCardGraphics() {}


    private void initialize(){
        window = new JFrame();
        menubar = new JMenuBar(); 
        JMenu menu;
        menu=new JMenu("File Game");  
        menubar.add(menu);  
        window.setJMenuBar(menubar);  
        window.setSize(400,400);  
        window.setLayout(null);  
        window.setVisible(true); 
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        
        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(350, 500));
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        setupTextArea();

        controls = new JPanel();
        controls.setPreferredSize(new Dimension(300, 500));
        setupControls();

        boardGraphics = new JPanel() {
            protected void paintComponent(Graphics g) {
                drawBoard(g);
            }
        };
        boardGraphics.setPreferredSize(new Dimension(500, 500));
        setupBoardGraphics();

        cardGraphics = new JPanel() {
            protected void paintComponent(Graphics g) {
                drawCards(g);
            }
        };
        cardGraphics.setPreferredSize(new Dimension(700, 200));
        setupCardGraphics();

        window.add(textArea, BorderLayout.WEST);
        window.add(controls, BorderLayout.EAST);
        window.add(boardGraphics, BorderLayout.CENTER);
        window.add(cardGraphics, BorderLayout.SOUTH);
        window.pack();
        window.setVisible(true);
    }

    public static void main(String[] args) {
        //new GUI();
    }
}
