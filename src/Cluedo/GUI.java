package Cluedo;

import javax.swing.*;
import java.awt.*;

public abstract class GUI {

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

    private void setupControls() {}

    private void setupBoardGraphics() {}

    private void setupCardGraphics() {}


    private void initialize(){
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(300, 500));
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
