package ships;

import gameboard.Cell;

/**
 * Represents a ship occupying one cell on the game board.
 */
public class Ship {
    /** The cell occupied by this ship. */
    private final Cell cell;

    /**
     * Creates a ship at the specified cell.
     *
     * @param cell the cell occupied by the ship
     */
    public Ship(Cell cell) {
        this.cell = cell;
    }

    /**
     * Gets the cell occupied by this ship.
     *
     * @return the occupied cell
     */
    public Cell getCell() {
        return cell;
    }
}
