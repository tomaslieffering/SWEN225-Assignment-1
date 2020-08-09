package Cluedo.Board;

/**
 * For representing invalid move exceptions
 */
public class InvalidMoveException extends Exception {
    public InvalidMoveException(String cause){
        super(cause);
    }
}
