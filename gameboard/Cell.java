package gameboard;

import ships.Ship;

/**
 * Represents one location on the game board.
 *
 * A cell tracks its board coordinates, displayed result, and optional ship.
 */
public class Cell {
    /** The zero-based row coordinate of this cell. */
    private final int row;
    /** The zero-based column coordinate of this cell. */
    private final int col;
    /** The symbol shown before the cell has been revealed. */
    private static final char HIDDEN_SYMBOL = '-';
    /** The symbol shown when a shot hits a ship. */
    private static final char HIT_SYMBOL = 'X';
    /** The symbol shown when a shot misses. */
    private static final char MISS_SYMBOL = 'O';
    /** The current display symbol for this cell. */
    private String displaySymbol;
    /** The ship occupying this cell, if any. */
    private Ship ship;

    /**
     * Creates a hidden cell at the supplied coordinates.
     *
     * @param row the zero-based row coordinate
     * @param col the zero-based column coordinate
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.displaySymbol = String.valueOf(HIDDEN_SYMBOL);
        this.ship = null;
    }

    /**
     * Gets this cell's row coordinate.
     *
     * @return the zero-based row coordinate
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets this cell's column coordinate.
     *
     * @return the zero-based column coordinate
     */
    public int getCol() {
        return col;
    }

    /**
     * Reveals the result of a shot at this cell.
     *
     * @param hit {@code true} if the shot hit a ship; {@code false} otherwise
     */
    public void reveal(boolean hit) {
        displaySymbol = hit ? String.valueOf(HIT_SYMBOL) : String.valueOf(MISS_SYMBOL);
    }

    /**
     * Places a ship in this cell.
     *
     * @param ship the ship to place
     * @return {@code true} if the ship was accepted; {@code false} otherwise
     */
    public boolean addShip(Ship ship) {
        if (this.ship != null || ship == null) {
            return false;
        }
        this.ship = ship;
        return true;
    }

    /**
     * Determines whether this cell contains a ship.
     *
     * @return {@code true} when a ship is present
     */
    public boolean containsShip() {
        return ship != null;
    }

    /**
     * Returns the symbol currently displayed for this cell.
     *
     * @return the display symbol
     */
    @Override
    public String toString() {
        return displaySymbol;
    }
}
