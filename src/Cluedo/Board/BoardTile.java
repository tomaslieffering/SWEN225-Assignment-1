package Cluedo.Board;

import Cluedo.Card.PersonCard;

public interface BoardTile {

    /**
     * @return
     *   Whether or not a player is allowed to move onto this tile
     */
    boolean canMoveHere();

    String getName();

    void setTile(PersonCard.PersonType personType);

    boolean isPlayer();

}
