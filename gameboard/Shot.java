package gameboard;

/**
 * Represents one shot directed at a cell on the game board.
 */
public class Shot {
    /** The cell targeted by this shot. */
    private final Cell targetCell;

    /**
     * Creates a shot targeting the specified cell.
     *
     * @param targetCell the cell to target
     */
    public Shot(Cell targetCell) {
        this.targetCell = targetCell;
    }

    /**
     * Determines whether this shot hits a ship.
     *
     * @return {@code true} if the target cell contains a ship
     */
    public boolean shoot() {
        return targetCell.containsShip();
    }
}
