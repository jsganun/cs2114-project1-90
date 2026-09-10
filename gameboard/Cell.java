package gameboard;

import ships.Ship;

public class Cell {
    private final int x;
    private final int y;
    private static final char HIDDEN_SYMBOL = '-';
    private static final char HIT_SYMBOL = 'X';
    private static final char MISS_SYMBOL = 'O';
    private String displaySymbol;
    private Ship ship;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.displaySymbol = String.valueOf(HIDDEN_SYMBOL);
        this.ship = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
