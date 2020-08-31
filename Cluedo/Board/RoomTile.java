package Cluedo.Board;


import Cluedo.Card.RoomCard;
import Cluedo.Player;

import java.awt.*;

public class RoomTile implements BoardTile {

    /**
     * The player standing on this tile, null if there is not player here
     */
    protected Player player = null;

    /**
     * The room that this tile is part of
     * The value null is used for Hallway
     */
    protected RoomCard.RoomType room;

    /**
     * Colors of the floor tiles
     */
    public static Color lightTile = new Color(0x777777);
    public static Color darkTile = new Color(0x676767);
    public static Color lightRoomTile = new Color(0x75525D);
    public static Color darkRoomTile = new Color(0x664751);

    /**
     * Colors of the players
     */
    public static Color white = new Color(0x91F2F2);
    public static Color green = new Color(0x42BD44);
    public static Color plum = new Color(0xAD40C9);
    public static Color scarlett = new Color(0xD1355E);
    public static Color mustard = new Color(0xD0A230);
    public static Color peacock = new Color(0x4A58E1);


    //=======================================================
    //  Constructors
    //=======================================================

    /**
     * Default constructor
     * Field "room" is set to null
     */
    public RoomTile(){
        this(null);
    }

    /**
     * @param room
     *   The room for this tile to be set to
     */
    public RoomTile(RoomCard.RoomType room){
        this.room = room;
    }


    //=======================================================
    //  METHODS
    //=======================================================

    @Override
    public boolean canMoveHere(MoveDirection direction) {
        return true;
    }

    @Override
    public boolean canMoveFromHere(MoveDirection direction) {
        return true;
    }

    @Override
    public void draw(Graphics g, int xPos, int yPos) {
        if ((xPos + yPos) % 40 == 0) {
            if (room != null)
                g.setColor(lightRoomTile);
            else
                g.setColor(lightTile);
        }
        else {
            if (room != null)
                g.setColor(darkRoomTile);
            else
                g.setColor(darkTile);
        }
        g.fillRect(xPos, yPos, 20, 20);
        if(player != null) {
            switch (player.getPersonType()) {
                case MRS_WHITE:
                    g.setColor(white);
                    break;
                case MR_GREEN:
                    g.setColor(green);
                    break;
                case PROFESSOR_PLUM:
                    g.setColor(plum);
                    break;
                case MISS_SCARLETT:
                    g.setColor(scarlett);
                    break;
                case COLONEL_MUSTARD:
                    g.setColor(mustard);
                    break;
                case MRS_PEACOCK:
                    g.setColor(peacock);
                    break;
            }
            g.fillOval(xPos + 4, yPos + 4, 12, 12);
            g.setColor(WallTile.wallColor);
            g.drawOval(xPos + 4, yPos + 4, 12, 12);
        }
    }

    /**
     * Returns the room of this tile
     */
    public RoomCard.RoomType getRoom() {
        return room;
    }

    /**
     * Sets the player on this tile
     */
    public void setPlayer(Player p) {
        player = p;
    }

    /**
     * Returns the player on this tile
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        if(player != null)
            return "*" + player.toChar() + " ";
        if(room == null)
            return Board.hallwayFloor + "" + Board.hallwayFloor + " ";
        else
            return Board.roomFloor + "" + Board.roomFloor + " ";
    }

    //=======================================================
    //  STATIC METHODS
    //=======================================================

    /**
     * Moves a player from one tile to another
     * @param from
     *   The tile the player is moving from
     * @param to
     *   The tile the player is moving to
     */
    public static void movePlayer(RoomTile from, RoomTile to) {
        to.player = from.player;
        from.player = null;
    }

    /**
     * Flips the direction given
     * @param direction
     *   Input direction
     * @return
     *   The direction moving in the opposite direction
     */
    public static MoveDirection flipDirection(MoveDirection direction) {
        switch (direction) {
            case LEFT:
                return MoveDirection.RIGHT;
            case RIGHT:
                return MoveDirection.LEFT;
            case UP:
                return MoveDirection.DOWN;
            case DOWN:
                return MoveDirection.UP;
            default:
                throw new IllegalStateException("Not a direction");
        }
    }
}
