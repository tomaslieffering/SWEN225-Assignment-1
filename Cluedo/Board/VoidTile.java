package Cluedo.Board;

import Cluedo.Card.RoomCard;

import java.awt.*;

public class VoidTile implements BoardTile {

    private final String label;

    //=======================================================
    //  Constructors
    //=======================================================

    /**
     * Default constructor
     * Sets label to null
     */
    public VoidTile(){
        label = null;
    }

    /**
     * Constructor that takes a label as a string
     * @param label
     *   The label to be displayed
     */
    public VoidTile(String label){
        this.label = label;
    }

    /**
     * Constructor that generates a label from RoomType
     */
    public VoidTile(RoomCard.RoomType roomType) throws BoardFormatException {
        if(roomType == null)
            label = null;
        else
            label = generateLabel(roomType);
    }

    //=======================================================
    //  Methods
    //=======================================================

    @Override
    public boolean canMoveHere(MoveDirection direction) {
        return false;
    }

    @Override
    public boolean canMoveFromHere(MoveDirection direction) {
        return false;
    }

    @Override
    public void draw(Graphics g, int xPos, int yPos) {
        g.setColor(WallTile.wallColor);
        if(label != null) {
            if(!label.equals("  "))
                g.fillRect(xPos, yPos, 20, 20);
        } else {
            g.fillRect(xPos, yPos, 20, 20);
        }
    }

    @Override
    public String toString() {
        if(label != null)
            return label + " ";
        return Board.fullWall + "" + Board.fullWall + " ";
    }

    //=======================================================
    //  Private Utility Methods
    //=======================================================

    private static String generateLabel(RoomCard.RoomType roomType) throws BoardFormatException {
        switch(roomType) {
            case KITCHEN:
                return "KI";
            case BALL_ROOM:
                return "BA";
            case CONSERVATORY:
                return "CV";
            case DINING_ROOM:
                return "DI";
            case BILLIARD_ROOM:
                return "BI";
            case LIBRARY:
                return "LB";
            case LOUNGE:
                return "LG";
            case HALL:
                return "HA";
            case STUDY:
                return "ST";
            default:
                throw new BoardFormatException("Exception during board parsing.\n\"Invalid room\"");
        }
    }
}
