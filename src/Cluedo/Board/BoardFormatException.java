package Cluedo.Board;

/**
 * For representing errors in the format of the "board-layout.dat" file
 */
public class BoardFormatException extends Exception {
    public BoardFormatException(String cause){
        super(cause);
    }
}
