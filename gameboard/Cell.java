package gameboard;

import ships.Ship;

public class Cell {
    private final int row;
    private final int col;
    private static final char HIDDEN_SYMBOL = '-';
    private static final char HIT_SYMBOL = 'X';
    private static final char MISS_SYMBOL = 'O';
    private String displaySymbol;
    private Ship ship;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.displaySymbol = String.valueOf(HIDDEN_SYMBOL);
        this.ship = null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void reveal(boolean hit) {
        displaySymbol = hit ? String.valueOf(HIT_SYMBOL) : String.valueOf(MISS_SYMBOL);
    }

    public boolean addShip(Ship ship) {
        if (ship != null) {
            return false;
        }
        this.ship = ship;
        return true;
    }

    public boolean containsShip() {
        return ship != null;
    }

    @Override
    public String toString() {
        return displaySymbol;
    }
}
