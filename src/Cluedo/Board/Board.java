package Cluedo.Board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {

    /**
     * Array containing all of the tiles on the board
     */
    private final BoardTile[][] board;

    /**
     * Constructs a new board with the given layout
     * @param boardLayout
     *   File containing the layout of the board
     * @throws BoardFormatException
     *   Thrown if the is an IO exception or if the file does not describe a valid board
     */
    public Board(File boardLayout) throws BoardFormatException{
        try{
            Scanner scan = new Scanner(boardLayout);
            String currentRow = scan.nextLine();
            int rowLength = currentRow.length() - 1;

            // scanning everything into list
            List<BoardTile> tileList = new ArrayList<>(Board.parseRow(currentRow));
            while(scan.hasNext()) {
                currentRow = scan.nextLine();
                if(rowLength + 1 != currentRow.length())
                    throw new BoardFormatException("Exception during board parsing.\n\"Row Length mismatched\"");
                tileList.addAll(Board.parseRow(currentRow));
            }

            // compiling into array
            BoardTile[][] boardTiles = new BoardTile[tileList.size() / rowLength][rowLength];
            for(int i = 0; i < boardTiles.length; i++) {
                for(int j = 0; j < boardTiles[0].length; j++) {
                    boardTiles[i][j] = tileList.get(j + (i * rowLength));
                }
            }
            board = boardTiles;

        } catch (IOException e) {
            throw new BoardFormatException("Exception during board data file handling.");
        }
    }

    /**
     * Will parse a string into a list of board tiles
     */
    private static List<BoardTile> parseRow(String row) throws BoardFormatException{
        if(!row.endsWith("/"))
            throw new BoardFormatException("Exception during board parsing.\n\"No '/' at end of row\"");
        row = row.substring(0, row.length() - 1);
        List<BoardTile> tiles = new ArrayList<>();
        for(char c : row.toCharArray()) {
            switch (c) {
                case '&':
                    tiles.add(new WallTile());
                    break;
                case 'K':
                    tiles.add(new RoomTile(RoomTile.RoomType.KITCHEN));
                    break;
                case 'B':
                    tiles.add(new RoomTile(RoomTile.RoomType.BALL_ROOM));
                    break;
                case 'C':
                    tiles.add(new RoomTile(RoomTile.RoomType.CONSERVATORY));
                    break;
                case 'D':
                    tiles.add(new RoomTile(RoomTile.RoomType.DINING_ROOM));
                    break;
                case 'b':
                    tiles.add(new RoomTile(RoomTile.RoomType.BILLIARD_ROOM));
                    break;
                case 'L':
                    tiles.add(new RoomTile(RoomTile.RoomType.LIBRARY));
                    break;
                case 'l':
                    tiles.add(new RoomTile(RoomTile.RoomType.LOUNGE));
                    break;
                case 'H':
                    tiles.add(new RoomTile(RoomTile.RoomType.HALL));
                    break;
                case 'S':
                    tiles.add(new RoomTile(RoomTile.RoomType.STUDY));
                    break;
                case ' ':
                    tiles.add(new RoomTile(RoomTile.RoomType.HALLWAY));
                    break;
                default:
                    throw new BoardFormatException("Exception during board parsing.\n\"Unrecognized character '" + c + "'\"");
            }
        }
        return tiles;
    }

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

    //TODO remove this when not needed for testing anymore
    public static void main(String[] args) throws BoardFormatException {
        System.out.println(new Board(new File("board-layout.dat")));
    }
}
