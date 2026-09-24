package ships;

import gameboard.Cell;

public class Ship {
    private String name;
    private int length;
    private Cell[] cells;
    private int hitsTaken;

    /**
     * Constructs a Ship with the specified name and length.
     *
     * @param name   the name of the ship
     * @param length the length of the ship
     * @throws IllegalArgumentException if name is null or length is less than or
     *                                  equal to 0
     */
    public Ship(String name, int length) {
        if (name == null || length <= 0) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.length = length;
        this.cells = new Cell[length];
        this.hitsTaken = 0;
    }

    /**
     * Checks if the ship has been hit.
     *
     * @return true if the ship has been hit, false otherwise
     */
    public boolean isHit() {
        return hitsTaken > 0;
    }

    /**
     * Checks if the ship has been sunk.
     *
     * @return true if the ship has been sunk, false otherwise
     */
    public boolean isSunk() {
        return hitsTaken >= length;
    }

    /**
     * Gets the name of the ship.
     *
     * @return the name of the ship
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the length of the ship.
     *
     * @return the length of the ship
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the cells occupied by the ship.
     *
     * @param cells an array of Cell objects representing the ship's position
     * @throws IllegalArgumentException if cells is null
     * @throws IllegalArgumentException if the length of cells does not match the
     *                                  ship's length
     */
    public void setCells(Cell[] cells) {
        if (cells == null) {
            throw new IllegalArgumentException("Cells array cannot be null.");
        }
        if (cells.length != length) {
            throw new IllegalArgumentException("Number of cells does not match ship length.");
        }
        this.cells = cells;
    }

    /**
     * Gets the cells occupied by the ship.
     *
     * @return an array of Cell objects representing the ship's position
     */
    public Cell[] getCells() {
        return cells;
    }

    /**
     * Checks if the ship occupies the specified cell.
     *
     * @param cell the Cell to check
     * @return true if the ship occupies the cell, false otherwise
     * @throws NullPointerException if cell is null
     */
    public boolean containsCell(Cell cell) {
        if (cell == null) {
            throw new NullPointerException("Cell cannot be null.");
        }
        if (cells != null) {
            for (int i = 0; i < cells.length; i++) {
                if (cells[i] != null && cells[i].equals(cell)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Registers a hit on the ship by counting the number of cells that have been
     * hit.
     */
    public void registerHit() {
        int hits = 0;
        if (cells != null) {
            for (int i = 0; i < cells.length; i++) {
                if (cells[i] != null && cells[i].toString().equals("X")) {
                    hits++;
                }
            }
        }
        this.hitsTaken = hits;
    }

    /**
     * Returns a string representation of the ship, including its name, length, hits
     * taken, and sunk status.
     *
     * @return a string representation of the ship
     */
    @Override
    public String toString() {
        return name + ", " + length + ", " + hitsTaken + ", " + isSunk();
    }
}
