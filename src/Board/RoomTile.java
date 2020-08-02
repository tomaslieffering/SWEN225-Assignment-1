package Board;

import Card.Card;
import Card.PersonCard;

public class RoomTile implements BoardTile {

    /**
     * List of all the different rooms you can have on the board
     * Note that "HALL" is one of the rooms room and "HALLWAY" is the space between all the rooms
     */
    public enum RoomType{
        KITCHEN, BALL_ROOM, CONSERVATORY, DINING_ROOM, BILLIARD_ROOM, LIBRARY, LOUNGE, HALL, STUDY, BASEMENT, HALLWAY
    }

    /**
     * The room that this tile is part of
     */
    private RoomType room;

    private PersonCard.PersonType playerOnTile = null;
    /**
     * Default constructor
     * Field "room" is set to "HALLWAY"
     */
    public RoomTile(){
        this.playerOnTile = PersonCard.PersonType.NO_PLAYER;
        this.room = RoomType.HALLWAY;
    }

    /**
     * @param room
     *   The room for this tile to be set to
     */
    public RoomTile(RoomType room){
        this.playerOnTile = PersonCard.PersonType.NO_PLAYER;
        this.room = room;
    }

    public RoomTile(PersonCard.PersonType personOnTile){
        this.playerOnTile = personOnTile;
        this.room = RoomType.HALLWAY;
    }

    @Override
    public boolean canMoveHere() {
        return true;
        //TODO return false if there is already a player on this tile
    }

    public void setTile(PersonCard.PersonType personType){
        this.playerOnTile = personType;
    }

    public boolean isPlayer(){
        if (playerOnTile == PersonCard.PersonType.NO_PLAYER){
            return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return playerOnTile.toString();
    }

    public String toString() {
        if (isPlayer()){
            if (playerOnTile == PersonCard.PersonType.MISS_SCARLETT){
                return "\uD83C\uDD42" + " ";
            }
            if (playerOnTile == PersonCard.PersonType.COLONEL_MUSTARD){
                return "\uD83C\uDD3C" + " ";
            }
            if (playerOnTile == PersonCard.PersonType.MRS_WHITE){
                return "\uD83C\uDD46" + " ";
            }
            if (playerOnTile == PersonCard.PersonType.MR_GREEN){
                return "\uD83C\uDD36" + " ";
            }
            if (playerOnTile == PersonCard.PersonType.MRS_PEACOCK){
                return "\uD83D\uDC26" + " ";
            }
            if (playerOnTile == PersonCard.PersonType.PROFESSOR_PLUM){
                return "\uD83C\uDD3F" + " ";
            }
        }
        if(room == RoomType.HALLWAY)
            return "\u2591\u2591";
        else
            return "\u2592\u2592";
    }
}
