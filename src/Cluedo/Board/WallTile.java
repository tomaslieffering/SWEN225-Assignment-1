package Cluedo.Board;

import Cluedo.Card.RoomCard;

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
        switch (faces) {
            case 1:
                // left
                if(room == null)
                    return "\u258c\u2591 ";
                return "\u258c\u2592 ";
            case 2:
                // right
                if(room == null)
                    return "\u2591\u2590 ";
                return "\u2592\u2590 ";
            case 4:
                // up
                return "\u2580\u2580 ";
            case 5:
                // left + up
                return "\u258c\u2580 ";
            case 6:
                // right + up
                return "\u2580\u2590 ";
            case 8:
                // down
                return "\u2584\u2584 ";
            case 9:
                // left + down
                return "\u258c\u2584 ";
            case 10:
                // right + down
                return "\u2584\u2590 ";
        }
        return "\u2588\u2588 ";
    }
}
