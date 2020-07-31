package Board;

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

    /**
     * Default constructor
     * Field "room" is set to "HALLWAY"
     */
    public RoomTile(){
        this(RoomType.HALLWAY);
    }

    /**
     * @param room
     *   The room for this tile to be set to
     */
    public RoomTile(RoomType room){
        this.room = room;
    }

    @Override
    public boolean canMoveHere() {
        return true;
        //TODO return false if there is already a player on this tile
    }

    @Override
    public String getName() {
        return "ROOM";
    }

    public String toString() {
        if(room == RoomType.HALLWAY)
            return "\u2591\u2591";
        else
            return "\u2592\u2592";
    }
}
