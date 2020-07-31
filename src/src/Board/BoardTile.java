package Board;

public interface BoardTile {

    /**
     * @return
     *   Whether or not a player is allowed to move onto this tile
     */
    boolean canMoveHere();

    String getName();

}
