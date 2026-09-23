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
    /** Whether this cell has been shot. */
    private boolean shot;

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
        this.shot = false;
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
     * Marks this cell as shot and reveals the hit or miss symbol
     * depending on whether it contains a ship.
     */
    public void shoot() {
        shot = true;
        displaySymbol = containsShip() ? String.valueOf(HIT_SYMBOL) : String.valueOf(MISS_SYMBOL);
    }

    /**
     * Determines whether this cell has been shot.
     *
     * @return {@code true} if the cell has been shot
     */
    public boolean isShot() {
        return shot;
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
        if (ship == null) {
            throw new NullPointerException();
        }
        if (this.ship != null) {
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
     * Determines whether this cell is the same as obj.
     *
     * @param obj the object to compare this cell with
     * @return {@code true} when obj has the same row and col as this cell
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Cell other = (Cell) obj;
        return other.getRow() == this.getRow() && other.getCol() == this.getCol();
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
