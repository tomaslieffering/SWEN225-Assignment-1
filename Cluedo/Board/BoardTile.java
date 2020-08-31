package Cluedo.Board;

import java.awt.*;

public interface BoardTile {

    enum MoveDirection {
        LEFT, RIGHT, UP, DOWN
    }

    /**
     * Checks whether a player can move onto this tile in the given direction
     * @param direction
     *   The direction the player is moving onto this tile
     * @return
     *   Whether or not a player is allowed to move onto this tile
     */
    boolean canMoveHere(MoveDirection direction);

    /**
     * Checks whether a player can move from this tile in the given direction
     * @param direction
     *   The direction the player is moving onto this tile
     * @return
     *   Whether or not a player is allowed to move onto this tile
     */
    boolean canMoveFromHere(MoveDirection direction);

    /**
     * Draws this tile
     * @param g
     *   The graphics pane for this tile to be drawn on
     * @param xPos
     *   The x position to draw at
     * @param yPos
     *   The y position to draw at
     */
    void draw(Graphics g, int xPos, int yPos);

}
