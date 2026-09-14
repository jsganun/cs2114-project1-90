package gameboard;

import ships.Ship;

/**
 * Represents the game's fixed 10-by-10 grid of cells.
 */
public class Grid {
    /** The cells that make up this grid. */
    private final Cell[][] arr;

    /** Creates and initializes an empty 10-by-10 grid. */
    public Grid() {
        arr = new Cell[10][10];
        fillArray();
    }

    /** Initializes every grid position with a new cell. */
    private void fillArray() {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * Creates a ship at the specified grid location.
     *
     * @param r the zero-based row coordinate
     * @param c the zero-based column coordinate
     */
    public void createShip(int r, int c) {
        arr[r][c].addShip(new Ship(arr[r][c]));
    }

    /**
     * Fires a shot at the specified grid location and reveals a hit.
     *
     * @param r the zero-based row coordinate
     * @param c the zero-based column coordinate
     */
    public void shoot(int r, int c) {
        Cell target = arr[r][c];
        Shot shot = new Shot(target);
        boolean hit = shot.shoot();
        if (hit) {
            target.reveal(hit);
        }
    }

    /**
     * Validates and translates a board coordinate such as {@code A1} or
     * {@code J10}. The translated coordinates are returned in the form of an array.
     *
     * @param input the coordinate string to translate
     * @return an int array with row at index 0 and col at index 1
     * @throws IllegalArgumentException if the input has an invalid format
     */
    public int[] translateCoords(String input) {
        if (input.length() < 2 || input.length() > 3) {
            throw new IllegalArgumentException();
        }
        if (input.length() == 3 && !isNumber(input.substring(1, 2))) {
            throw new IllegalArgumentException();
        }
        String str = input.toUpperCase();
        int row = letterToIndex(str.charAt(0));
        int col;
        if (input.length() == 3) {
            col = Integer.parseInt(str.substring(1, 3)); // 10
        } else {
            col = Integer.parseInt(str.substring(1, 2)); // < 10
        }
        return new int[] { row, col };
    }

    /**
     * Converts a board row letter to its zero-based index.
     *
     * @param letter the row letter
     * @return the zero-based row index
     */
    private int letterToIndex(char letter) {
        // char 'A' is 65 -> index 0
        // char 'J' is 74 -> index 9
        return letter - 65;
    }

    /**
     * Determines whether a string contains a valid integer.
     *
     * @param in the string to inspect
     * @return {@code true} when the string can be parsed as an integer
     */
    private boolean isNumber(String in) {
        try {
            Integer.valueOf(in);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void appendRow(StringBuilder sb, int r) {
        char letter = (char) ('A' + r);
        int c;
        sb.append(letter).append(" ");
        for (c = 0; c < arr[0].length - 1; c++) {
            sb.append(arr[r][c].toString()).append(" ");
        }
        sb.append(arr[r][c]).append("\n");
    }

    /**
     * Returns the textual representation of the grid.
     *
     * @return the grid representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int r = 0; r < arr.length; r++) {
            sb.append(" ").append(r + 1);
        }
        sb.append("\n");
        for (int r = 0; r < arr.length; r++) {
            appendRow(sb, r);
        }
        return sb.toString();
    }

}
