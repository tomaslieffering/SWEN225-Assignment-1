package Board;

import Card.PersonCard;

public class PlayerTile implements BoardTile{

    public enum PersonType {
        MISS_SCARLETT, COLONEL_MUSTARD, MRS_WHITE, MR_GREEN, MRS_PEACOCK, PROFESSOR_PLUM
    }

    private PersonType personType;

    private int row;
    private int col;

    public PlayerTile(PersonType personType, int row, int col){
        this.personType = personType;
        this.row = row;
        this.col = col;
    }

    /**
     * Constructor that constructs a new PlayerTile based on the PersonCard.PersonType enum
     * @param personType
     * @param row the row to be constructed on the board
     * @param col the column where the player tile is to be constructed
     */
    public PlayerTile(PersonCard.PersonType personType, int row, int col){
        if (personType.toString().equals("MISS_SCARLETT")){
            this.personType = PersonType.MISS_SCARLETT;
        }
        if (personType.toString().equals("COLONEL_MUSTARD")){
            this.personType = PersonType.COLONEL_MUSTARD;
        }
        if (personType.toString().equals("MRS_WHITE")){
            this.personType = PersonType.MRS_WHITE;
        }
        if (personType.toString().equals("MR_GREEN")){
            this.personType = PersonType.MR_GREEN;
        }
        if (personType.toString().equals("MRS_PEACOCK")){
            this.personType = PersonType.MRS_PEACOCK;
        }
        if (personType.toString().equals("PROFESSOR_PLUM")){
            this.personType = PersonType.PROFESSOR_PLUM;
        }
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean canMoveHere() {
        return false;
    }

    @Override
    public String getName() {
        return personType.toString();
    }

    @Override
    public String toString() {
        if (personType == PersonType.MISS_SCARLETT){
            return "\uD83C\uDD42" + " ";
        }
        if (personType == PersonType.COLONEL_MUSTARD){
            return "\uD83C\uDD3C" + " ";
        }
        if (personType == PersonType.MRS_WHITE){
            return "\uD83C\uDD46" + " ";
        }
        if (personType == PersonType.MR_GREEN){
            return "\uD83C\uDD36" + " ";
        }
        if (personType == PersonType.MRS_PEACOCK){
            return "\uD83D\uDC26" + " ";
        }
        if (personType == PersonType.PROFESSOR_PLUM){
            return "\uD83C\uDD3F" + " ";
        }
        return "  ";
    }
}
