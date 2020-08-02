package Cluedo;

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
     * @param rows amount of rows to be moved
     * @param cols amount of columns to be moved
     * @return the new postion object
     */
    public Position move (int rows, int cols){
        return new Position(this.getRow() + rows, this.getCol() + cols);
    }
}
