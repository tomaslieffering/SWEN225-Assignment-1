package Cluedo.Board;


import Cluedo.Card.RoomCard;
import Cluedo.Player;

public class RoomTile implements BoardTile {

    /**
     * The player standing on this tile, null if there is not player here
     */
    protected Player player = null;

    /**
     * The room that this tile is part of
     * The value null is used for Hallway
     */
    protected RoomCard.RoomType room;

    //=======================================================
    //  Constructors
    //=======================================================

    /**
     * Default constructor
     * Field "room" is set to null
     */
    public RoomTile(){
        this(null);
    }

    /**
     * @param room
     *   The room for this tile to be set to
     */
    public RoomTile(RoomCard.RoomType room){
        this.room = room;
    }


    //=======================================================
    //  METHODS
    //=======================================================

    @Override
    public boolean canMoveHere(MoveDirection direction) {
        return player == null;
    }

    @Override
    public boolean canMoveFromHere(MoveDirection direction) {
        return true;
    }

    /**
     * Returns the room of this tile
     */
    public RoomCard.RoomType getRoom() {
        return room;
    }

    /**
     * Sets the player on this tile
     */
    public void setPlayer(Player p) {
        player = p;
    }

    /**
     * Returns the player on this tile
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        if(player != null)
            return "*" + player.toChar() + " ";
        if(room == null)
            return Board.hallwayFloor + "" + Board.hallwayFloor + " ";
        else
            return Board.roomFloor + "" + Board.roomFloor + " ";
    }

    //=======================================================
    //  STATIC METHODS
    //=======================================================

    /**
     * Moves a player from one tile to another
     * @param from
     *   The tile the player is moving from
     * @param to
     *   The tile the player is moving to
     */
    public static void movePlayer(RoomTile from, RoomTile to) {
        to.player = from.player;
        from.player = null;
    }

    /**
     * Flips the direction given
     * @param direction
     *   Input direction
     * @return
     *   The direction moving in the opposite direction
     */
    public static MoveDirection flipDirection(MoveDirection direction) {
        switch (direction) {
            case LEFT:
                return MoveDirection.RIGHT;
            case RIGHT:
                return MoveDirection.LEFT;
            case UP:
                return MoveDirection.DOWN;
            case DOWN:
                return MoveDirection.UP;
            default:
                throw new IllegalStateException("Not a direction");
        }
    }
}
