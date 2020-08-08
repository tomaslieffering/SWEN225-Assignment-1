package Cluedo.Board;

import Cluedo.Card.PersonCard;
import Cluedo.Card.RoomCard;
import Cluedo.Player;
import Cluedo.Position;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Board {

    /**
     * Array containing all of the tiles on the board
     */
    private final BoardTile[][] board;

    //=======================================================
    //  Constructors
    //=======================================================

    /**
     * Constructs a new board with the given layout
     * @param boardLayout
     *   File containing the layout of the board
     * @throws BoardFormatException
     *   Thrown if the is an IO exception or if the file does not describe a valid board
     */
    public Board(File boardLayout, List<Player> players) throws BoardFormatException{
        try{
            Scanner scan = new Scanner(boardLayout);
            String currentRow = scan.nextLine();
            String playerRow = null;

            // scanning everything into list
            List<BoardTile> tileList = new ArrayList<>(Board.parseRow(currentRow));
            int rowLength = tileList.size();
            while(scan.hasNext()) {
                currentRow = scan.nextLine();
                // get player row
                if(currentRow.startsWith("+")) {
                    playerRow = currentRow.substring(1);
                    break;
                }
                tileList.addAll(Board.parseRow(currentRow));
                if(tileList.size() % rowLength != 0)
                    throw new BoardFormatException("Exception during board parsing.\n\"Row Length mismatched\"");
            }
            if(playerRow == null)
                throw new BoardFormatException("Exception during board parsing.\n\"Player positions not specified\"");

            // compiling into array
            BoardTile[][] boardTiles = new BoardTile[tileList.size() / rowLength][rowLength];
            for(int i = 0; i < boardTiles.length; i++) {
                for(int j = 0; j < boardTiles[0].length; j++) {
                    boardTiles[i][j] = tileList.get(j + (i * rowLength));
                }
            }
            board = boardTiles;

            // adding players
            parsePlayerRow(playerRow, players);
        } catch (IOException e) {
            throw new BoardFormatException("Exception during board data file handling.");
        }
    }

    //=======================================================
    //  Methods
    //=======================================================

    /**
     * Checks whether a specific input is valid, and then moves the player
     * @param p
     *   The player to be moved
     * @param input
     *   The input string to move the player
     * @param diceNumber
     *   The number the player rolled on the dice
     * @return
     *   True if the move is valid, false if not
     *   Move is not done if it is invalid
     */
    public boolean movePlayer(String input, int diceNumber, Player p) {
        Position start = findPlayer(p);
        int moveCount = 0;
        Set<BoardTile> previousTiles = new HashSet<>();
        Position currentPosition = start;
        RoomCard.RoomType startRoom = ((RoomTile)getTileAt(start)).getRoom();
        while(input.length() > 0) {
            char c = input.charAt(0);
            input = input.substring(1);
            BoardTile.MoveDirection direction = parseDirection(c);
            //if the input char is wrong
            if (direction == null) {
                System.out.println("'" + c + "' is not a valid direction!");
                return false;
            }

            //if the location is out of bounds
            if(isOutOfBounds(currentPosition.move(direction))) {
                System.out.println("You can't move off the board!");
                return false;
            }

            //if the tile cannot be moved to
            if(!getTileAt(currentPosition).canMoveFromHere(direction) ||
                    !getTileAt(currentPosition.move(direction)).canMoveHere(direction)) {
                System.out.println("You can't move through walls!");
                return false;
            }

            //if the tile has already been moved to
            if(previousTiles.contains(getTileAt(currentPosition.move(direction)))) {
                System.out.println("You can't retrace your own steps!");
                return false;
            }

            //update position
            previousTiles.add(getTileAt(currentPosition));
            currentPosition = currentPosition.move(direction);
            moveCount++;
        }
        if(moveCount > diceNumber) {
            System.out.println("You can't use more moves than your roll!");
            return false;
        }
        else if(moveCount < diceNumber) {
            if(((RoomTile)getTileAt(currentPosition)).getRoom() == startRoom ||
                    ((RoomTile)getTileAt(currentPosition)).getRoom() == null) {
                System.out.println("You must use all of your moves if you haven't entered a new room!");
                return false;
            }
        }
        RoomTile.movePlayer((RoomTile)getTileAt(start), (RoomTile)getTileAt(currentPosition));
        return true;
    }

    /**
     * Returns the tile at the given position
     * @param position
     *   Position describing the tile
     */
    public BoardTile getTileAt(Position position){
        return board[position.getRow()][position.getCol()];
    }

    /**
     * Returns the room of a player
     * @param p
     *   The player to find the room of
     */
    public RoomCard.RoomType getPlayerRoom(Player p) {
        return ((RoomTile)getTileAt(findPlayer(p))).getRoom();
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++)
                str.append(board[i][j].toString());
            str.append("\n");
        }
        return str.toString();
    }

    //=======================================================
    //  Private Utility Methods
    //=======================================================

    /**
     * Checks if the given position is out of bounds of the board
     * @param position
     *   The position to check
     * @return
     *   True if it is out of bounds
     */
    private boolean isOutOfBounds(Position position) {
        return position.getRow() >= board.length ||
                position.getRow() < 0 ||
                position.getCol() >= board[0].length ||
                position.getCol() < 0;
    }

    /**
     * Find a specific player's position on the board
     * @param p
     *   The name of the person to be found
     * @return
     *   The position of found player
     */
    private Position findPlayer(Player p) {
        for (int row = 0; row < board.length; row++){
            for (int col = 0; col < board[row].length; col++){
                if(!(board[row][col] instanceof RoomTile))
                    continue;
                if(((RoomTile)board[row][col]).getPlayer() == p)
                    return new Position(row, col);
            }
        }
        throw new IllegalStateException("Couldn't find the player");
    }

    /**
     * Will parse a string into a list of board tiles
     */
    private static List<BoardTile> parseRow(String row) throws BoardFormatException{
        String[] args = row.split("/");
        List<BoardTile> tiles = new ArrayList<>();
        for(String s : args) {
            switch (s.charAt(0)) {
                case '0':
                    tiles.add(parseRoomTile(s));
                    break;
                case 'f':
                    tiles.add(parseVoidTile(s));
                    break;
                case '\n':
                    return tiles;
                default:
                    tiles.add(parseWallTile(s));
            }
        }
        return tiles;
    }

    /**
     * Will parse a string into the positions of the players on the board
     */
    private void parsePlayerRow(String row, List<Player> players) throws BoardFormatException {
        String[] args = row.split("[|]");
        Map<PersonCard.PersonType, Player> playerMap = new HashMap<>();
        for(Player p : players) {
            playerMap.put(p.getPersonType(), p);
        }
        for(String s : args) {
            String[] values = s.split(",");
            if(values.length != 3)
                throw new BoardFormatException("Exception during board parsing.\n\"Malformed player position line\"");

            PersonCard.PersonType personType;
            switch (values[0]) {
                case "w":
                    personType = PersonCard.PersonType.MRS_WHITE;
                    break;
                case "g":
                    personType = PersonCard.PersonType.MR_GREEN;
                    break;
                case "c":
                    personType = PersonCard.PersonType.MRS_PEACOCK;
                    break;
                case "p":
                    personType = PersonCard.PersonType.PROFESSOR_PLUM;
                    break;
                case "s":
                    personType = PersonCard.PersonType.MISS_SCARLETT;
                    break;
                case "m":
                    personType = PersonCard.PersonType.COLONEL_MUSTARD;
                    break;
                default:
                    throw new BoardFormatException("Exception during board parsing.\n\"Invalid person\"");
            }
            if(playerMap.containsKey(personType))
                addPlayer(playerMap.get(personType), values);
        }
    }

    /**
     *
     */
    private void addPlayer(Player p, String[] args) throws BoardFormatException {
        Position pos;
        try {
            pos = new Position(Integer.parseInt(args[2]), Integer.parseInt(args[1]));
        } catch(NumberFormatException e) {
            throw new BoardFormatException("Exception during board parsing.\n\"Invalid coordinates\"");
        }
        BoardTile tile = getTileAt(pos);
        if(!(tile instanceof RoomTile))
            throw new BoardFormatException("Exception during board parsing.\n\"Player start position invalid\"");
        ((RoomTile)tile).setPlayer(p);
    }

    /**
     * Parses one RoomTile
     * @param tile
     *   String representing the tile
     */
    private static BoardTile parseRoomTile(String tile) throws BoardFormatException{
        if(tile.length() != 2)
            throw new BoardFormatException("Exception during board parsing.\n\"Tile descriptor has invalid length\"");
        return new RoomTile(determineRoom(tile.charAt(1)));
    }

    /**
     * Parses one VoidTile
     * @param tile
     *   String representing the tile
     */
    private static BoardTile parseVoidTile(String tile) throws BoardFormatException{
        if(tile.length() != 2)
            throw new BoardFormatException("Exception during board parsing.\n\"Tile descriptor has invalid length\"");
        if(tile.charAt(1) == 'E')
            return new VoidTile("  ");
        return new VoidTile(determineRoom(tile.charAt(1)));
    }

    /**
     * Parses one WallTile
     * @param tile
     *   String representing the tile
     */
    private static BoardTile parseWallTile(String tile) throws BoardFormatException{
        if(tile.length() != 2)
            throw new BoardFormatException("Exception during board parsing.\n\"Tile descriptor has invalid length\"");
        try {
            return new WallTile(determineRoom(tile.charAt(1)), Short.parseShort("" + tile.charAt(0), 16));
        } catch (NumberFormatException e) {
            throw new BoardFormatException("Exception during board parsing.\n\"Invalid wall description\"");
        }
    }

    /**
     * Parses a char into a RoomType
     * @param c
     *   Char representing the room
     */
    private static RoomCard.RoomType determineRoom(char c) throws BoardFormatException {
        switch(c) {
            case 'K':
                return RoomCard.RoomType.KITCHEN;
            case 'B':
                return RoomCard.RoomType.BALL_ROOM;
            case 'C':
                return RoomCard.RoomType.CONSERVATORY;
            case 'D':
                return RoomCard.RoomType.DINING_ROOM;
            case 'b':
                return RoomCard.RoomType.BILLIARD_ROOM;
            case 'L':
                return RoomCard.RoomType.LIBRARY;
            case 'l':
                return RoomCard.RoomType.LOUNGE;
            case 'H':
                return RoomCard.RoomType.HALL;
            case 'S':
                return RoomCard.RoomType.STUDY;
            case 'h':
            case '0':
                return null;
            default:
                throw new BoardFormatException("Exception during board parsing.\n\"Invalid room: '" + c + "'\"");
        }
    }

    /**
     * Parses a char into a move direction
     * @param c
     *   Char representing the direction
     */
    private static BoardTile.MoveDirection parseDirection(char c) {
        switch (c) {
            case 'l':
                return BoardTile.MoveDirection.LEFT;
            case 'r':
                return BoardTile.MoveDirection.RIGHT;
            case 'u':
                return BoardTile.MoveDirection.UP;
            case 'd':
                return BoardTile.MoveDirection.DOWN;
            default:
                return null;
        }
    }

    //TODO remove this when not needed for testing anymore
    public static void main(String[] args) throws BoardFormatException {
        ArrayList<Player> al = new ArrayList<>();
        al.add(new Player(PersonCard.PersonType.COLONEL_MUSTARD));
        al.add(new Player(PersonCard.PersonType.MISS_SCARLETT));
        al.add(new Player(PersonCard.PersonType.MRS_WHITE));
        al.add(new Player(PersonCard.PersonType.MR_GREEN));
        al.add(new Player(PersonCard.PersonType.MRS_PEACOCK));
        al.add(new Player(PersonCard.PersonType.PROFESSOR_PLUM));
        Board b = new Board(new File("board-layout.dat"), al);
        Player player = new Player(PersonCard.PersonType.COLONEL_MUSTARD);
        //((RoomTile)b.board[0][9]).setPlayer(player);
        //b.movePlayer("dllfg", 100, player);
        System.out.println(b);
    }
}
