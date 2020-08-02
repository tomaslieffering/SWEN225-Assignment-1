package Cluedo.Board;

import Cluedo.Card.PersonCard;

public class WallTile implements BoardTile {
    @Override
    public boolean canMoveHere() {
        return false;
        //TODO better walls af
    }

    @Override
    public String getName() {
        return "WALL";
    }

    @Override
    public void setTile(PersonCard.PersonType personType) {

    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public String toString() {
        return "\u2588\u2588";
    }
}
