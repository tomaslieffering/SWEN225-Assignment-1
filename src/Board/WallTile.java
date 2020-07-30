package Board;

public class WallTile implements BoardTile {
    @Override
    public boolean canMoveHere() {
        return false;
        //TODO better walls af
    }

    @Override
    public String toString() {
        return "\u2588\u2588";
    }
}
