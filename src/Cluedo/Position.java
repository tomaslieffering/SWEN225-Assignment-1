package Cluedo;

import Cluedo.Board.BoardTile;

public class Position {
    private int col;
    private int row;

    /**
     * Constructs a position object, based on the position on the board
     * @param row the row number
     * @param col the column number
     */
    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }

    /**
     * Moves a positions a given number of rows and columns
     * @param rows
     *   Amount of rows to be moved
     * @param cols
     *   Amount of columns to be moved
     * @return
     *   The new position object
     */
    public Position move (int rows, int cols){
        return new Position(this.getRow() + rows, this.getCol() + cols);
    }

    /**
     * Moves a positions one unit in a given direction
     * @param direction
     *   Direction to move in
     * @return
     *   The new position object
     */
    public Position move (BoardTile.MoveDirection direction){
        switch (direction) {
            case LEFT:
                return move(0, -1);
            case RIGHT:
                return move(0, 1);
            case UP:
                return move(-1, 0);
            case DOWN:
                return move(1, 0);
            default:
                return move(0,0);
        }
    }
}
