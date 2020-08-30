package Cluedo.Board;

import Cluedo.Card.RoomCard;
import Cluedo.Player;

public class WallTile extends RoomTile {

    public static final short LEFT = 0x1;
    public static final short RIGHT = 0x2;
    public static final short UP = 0x4;
    public static final short DOWN = 0x8;

    /**
     * Represents what faces this wall tile has
     */
    private final short faces;

    //=======================================================
    //  CONSTRUCTORS
    //=======================================================

    /**
     * @param room
     *   The room for this tile to be set to
     * @param faces
     *   The short describing where the faces are
     */
    public WallTile(RoomCard.RoomType room, short faces){
        this.room = room;
        this.faces = faces;
    }

    //=======================================================
    //  METHODS
    //=======================================================

    @Override
    public boolean canMoveHere(MoveDirection direction) {
        if(!hasFace(flipDirection(direction))) {
            return super.canMoveHere(direction);
        }
        return false;
    }

    @Override
    public boolean canMoveFromHere(MoveDirection direction) {
        if(!hasFace(direction)) {
            return super.canMoveFromHere(direction);
        }
        return false;
    }

    /**
     * Returns whether this wall has the face in the given direction
     * @param direction
     *   Side the face is on when moving outward (e.g. MoveDirection.UP = top face)
     */
    public boolean hasFace(RoomTile.MoveDirection direction) {
        short face = convertDirection(direction);
        return (face & faces) == face;
    }

    //=======================================================
    //  STATIC METHODS
    //=======================================================

    /**
     * Converts MoveDirection to a short
     */
    public static short convertDirection(RoomTile.MoveDirection direction) {
        switch (direction) {
            case LEFT:
                return LEFT;
            case RIGHT:
                return RIGHT;
            case UP:
                return UP;
            case DOWN:
                return DOWN;
            default:
                throw new IllegalStateException("Not a direction");
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if(hasFace(MoveDirection.LEFT)) {
            //left
            str.append(Board.leftWall);
            contentString(str);
            str.append(" ");
        } else if(hasFace(MoveDirection.RIGHT)) {
            //right
            contentString(str);
            str.append(Board.rightWall).append(" ");
        } else {
            if(hasFace(MoveDirection.UP))
                str.append(Board.upWall);
            else if(hasFace(MoveDirection.DOWN))
                str.append(Board.downWall);
            contentString(str);
            str.append(" ");
        }
        return str.toString();
    }

    /**
     * Utility method for toString()
     * Appends the contents of this tile as a string
     */
    public void contentString(StringBuilder str) {
        if(player != null) {
            str.append(player.toChar());
        } else {
            if(hasFace(MoveDirection.UP))
                str.append(Board.upWall);
            else if(hasFace(MoveDirection.DOWN))
                str.append(Board.downWall);
            else if(room == null)
                str.append(Board.hallwayFloor);
            else
                str.append(Board.roomFloor);
        }
    }
}
