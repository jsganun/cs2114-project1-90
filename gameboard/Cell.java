package gameboard;

public class Cell {
    private final int x;
    private final int y;
    private static final char HIDDEN_SYMBOL = '-';
    private static final char REVEALED_SYMBOL = '+';
    private String displaySymbol;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.displaySymbol = String.valueOf(HIDDEN_SYMBOL);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void reveal() {
        displaySymbol = String.valueOf(REVEALED_SYMBOL);
    }

    @Override
    public String toString() {
        return displaySymbol;
    }
}
