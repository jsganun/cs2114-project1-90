package ships;

import gameboard.Cell;

/**
 * Represents a ship occupying one cell on the game board.
 */
public class Ship {
    /** The cells occupied by this ship. */
    private final Cell[] cells;
    private int length;

    /**
     * Creates a ship in the specified array of cells.
     *
     * @param cell the cells occupied by the ship
     */
    public Ship(Cell[] cells) {
        this.cells = cells;
    }

    /**
     * Gets the cells occupied by this ship.
     *
     * @return an array containing the occupied cells
     */
    public Cell[] getCells() {
        return cells;
    }

    public int getLength() {
        return length;
    }
}
