package Cluedo.Board;

public class WallTile extends RoomTile {
    @Override
    public boolean canMoveHere(MoveDirection direction) {
        return false;
        //TODO better walls af
    }

    @Override
    public boolean canMoveFromHere(MoveDirection direction) {
        return false;
        //TODO ^
    }

    @Override
    public String toString() {
        return "\u2588\u2588";
    }
}
