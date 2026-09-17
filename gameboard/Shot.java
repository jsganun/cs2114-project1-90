package gameboard;

/**
 * Represents one shot directed at a cell on the game board.
 */
public class Shot {
    /** The cell targeted by this shot. */
    private final Cell targetCell;
    private final boolean shotHit;

    /**
     * Creates a shot targeting the specified cell.
     *
     * @param targetCell the cell to target
     */
    public Shot(Cell targetCell) {
        this.targetCell = targetCell;
        this.shotHit = targetCell.containsShip();
    }

    /**
     * Determines whether this shot hits a ship.
     *
     * @return {@code true} if the target cell contains a ship
     */
    public boolean shoot() {
        return targetCell.containsShip();
    }

    public boolean shotHit() {
        return shotHit;
    }
}
