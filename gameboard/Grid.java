package gameboard;

import ships.Ship;

/**
 * Represents the game's fixed 10-by-10 grid of cells.
 */
public class Grid {
    // The amount of ships that will be placed on the grid. This is a constant value
    // for the game.
    private static final int SHIP_COUNT = 5;
    private static final int MAX_ROWS = 24;
    private static final int MAX_COLS = 24;

    /** The cells that make up this grid. */
    private final Cell[][] arr;
    private final Ship[] ships;
    private final int rows;
    private final int cols;

    /** Creates and initializes an empty grid. */

    /**
     * Creates a grid with the specified number of rows and columns.
     *
     * @param rows the number of rows in the grid (1-24)
     * @param cols the number of columns in the grid (1-24)
     * @throws IllegalArgumentException if the number of rows or columns is invalid
     */
    public Grid(int rows, int cols) {
        if (rows <= 0 || rows > MAX_ROWS || cols <= 0 || cols > MAX_COLS) {
            throw new IllegalArgumentException("Invalid grid size");
        }

        this.rows = rows;
        this.cols = cols;

        arr = new Cell[rows][cols];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = new Cell(i, j);
            }
        }

        ships = new Ship[SHIP_COUNT];
        ships[0] = new Ship("Destroyer", 2);
        ships[1] = new Ship("Submarine", 3);
        ships[2] = new Ship("Cruiser", 3);
        ships[3] = new Ship("Battleship", 4);
        ships[4] = new Ship("Carrier", 5);
    }

    /**
     * Returns the cell at the specified row and column.
     *
     * @param row the zero-based row coordinate
     * @param col the zero-based column coordinate
     * @return the cell at the specified coordinates
     * @throws IllegalArgumentException if the coordinates are invalid
     */
    public Cell getCell(int row, int col) {
        if (!isValidPosition(row, col)) {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }
        return arr[row][col];
    }

    /**
     * Places a ship on the grid at the specified position and direction.
     *
     * @param ship      the ship to place
     * @param row       the zero-based row coordinate
     * @param col       the zero-based column coordinate
     * @param direction the direction in which to place the ship
     * @return true if the ship was placed successfully, false otherwise
     */
    public boolean placeShip(Ship ship, int row, int col, String direction) {
        if (ship == null || direction == null) {
            throw new NullPointerException("Ship and direction cannot be null");
        }
        if (!isValidPosition(row, col)) {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }

        Cell[] cellsToAdd = new Cell[ship.getLength()];
        switch (direction.toUpperCase()) {
            case "NORTH":
                if (row - ship.getLength() + 1 < 0) {
                    return false; // Ship would go out of bounds
                }

                for (int i = 0; i < ship.getLength(); i++) {
                    Cell cell = getCell(row - i, col);
                    if (cell.containsShip()) {
                        return false; // Cell already occupied by another ship
                    }
                    cellsToAdd[i] = cell;
                }

                // break;
            case "SOUTH":
                if (row + ship.getLength() - 1 >= rows) {
                    return false; // Ship would go out of bounds
                }

                for (int i = 0; i < ship.getLength(); i++) {
                    Cell cell = getCell(row + i, col);
                    if (cell.containsShip()) {
                        return false; // Cell already occupied by another ship
                    }
                    cellsToAdd[i] = cell;
                }

                break;
            case "EAST":
                if (col + ship.getLength() - 1 >= cols) {
                    return false; // Ship would go out of bounds
                }

                for (int i = 0; i < ship.getLength(); i++) {
                    Cell cell = getCell(row, col + i);
                    if (cell.containsShip()) {
                        return false; // Cell already occupied by another ship
                    }
                    cellsToAdd[i] = cell;
                }

                break;
            case "WEST":
                if (col - ship.getLength() + 1 < 0) {
                    return false; // Ship would go out of bounds
                }

                for (int i = 0; i < ship.getLength(); i++) {
                    Cell cell = getCell(row, col - i);
                    if (cell.containsShip()) {
                        return false; // Cell already occupied by another ship
                    }
                    cellsToAdd[i] = cell;
                }

                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        ship.setCells(cellsToAdd);
        return true;
    }

    /**
     * Checks if all ships on the grid have been sunk.
     *
     * @return true if all ships are sunk, false otherwise
     */
    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the specified position is valid within the grid.
     *
     * @param row the zero-based row coordinate
     * @param col the zero-based column coordinate
     * @return true if the position is valid, false otherwise
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /**
     * Processes a shot fired at the grid.
     *
     * @param shot the shot to process
     * @return true if the shot resulted in a hit, false otherwise
     * @throws NullPointerException if the shot is null
     */
    public boolean receiveShot(Shot shot) {
        if (shot == null) {
            throw new NullPointerException("Shot cannot be null");
        }

        // Waiting for the implementation of shot processing logic
        return false;
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

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Ship[] getShips() {
        return ships;
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
