package Cluedo.Board;

import Cluedo.Card.RoomCard;

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
    public String toString() {
        if(label != null)
            return label + " ";
        return "\u2588\u2588 ";
    }

    //=======================================================
    //  Private Utility Methods
    //=======================================================

    private static String generateLabel(RoomCard.RoomType roomType) throws BoardFormatException {
        switch(roomType) {
            case KITCHEN:
                return "K\u0347\u033fI\u0347\u033f";
            case BALL_ROOM:
                return "B\u0347\u033fA\u0347\u033f";
            case CONSERVATORY:
                return "C\u0347\u033fV\u0347\u033f";
            case DINING_ROOM:
                return "D\u0347\u033fI\u0347\u033f";
            case BILLIARD_ROOM:
                return "B\u0347\u033fI\u0347\u033f";
            case LIBRARY:
                return "L\u0347\u033fB\u0347\u033f";
            case LOUNGE:
                return "L\u0347\u033fG\u0347\u033f";
            case HALL:
                return "H\u0347\u033fA\u0347\u033f";
            case STUDY:
                return "S\u0347\u033fT\u0347\u033f";
            default:
                throw new BoardFormatException("Exception during board parsing.\n\"Invalid room\"");
        }
    }
}
