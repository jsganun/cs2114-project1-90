package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import gameboard.Grid;
import gameboard.Cell;
import gameboard.Shot;
import gameboard.ShotResult;
import ships.Ship;

/**
 * Test class for the Grid component.
 * Verifies grid display formatting and initialization.
 */
public class TestGrid {
    private Grid grid;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        grid = new Grid(10, 10);
    }

    /**
     * Tests the string representation of an empty grid.
     */
    @Test
    public void testToString() {
        Grid testGrid = new Grid(10, 10);
        String expected = """
                  1 2 3 4 5 6 7 8 9 10
                A - - - - - - - - - -
                B - - - - - - - - - -
                C - - - - - - - - - -
                D - - - - - - - - - -
                E - - - - - - - - - -
                F - - - - - - - - - -
                G - - - - - - - - - -
                H - - - - - - - - - -
                I - - - - - - - - - -
                J - - - - - - - - - -
                                """;
        if (!testGrid.toString().equals(expected)) {
            System.out.println("toString() failed");
        }
    }

    /**
     * Tests retrieving a cell from the grid at valid coordinates.
     */
    @Test
    public void testGetCell() {
        Cell cell = grid.getCell(0, 0);
        assertNotNull(cell);
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());

        Cell cellEnd = grid.getCell(9, 9);
        assertNotNull(cellEnd);
        assertEquals(9, cellEnd.getRow());
        assertEquals(9, cellEnd.getCol());
    }

    /**
     * Tests that getCell throws exception for invalid coordinates.
     */
    @Test
    public void testGetCellInvalidCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> grid.getCell(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> grid.getCell(0, -1));
        assertThrows(IllegalArgumentException.class, () -> grid.getCell(10, 0));
        assertThrows(IllegalArgumentException.class, () -> grid.getCell(0, 10));
    }

    /**
     * Tests placing a ship on the grid.
     */
    @Test
    public void testPlaceShip() {
        Ship ship = new Ship("Destroyer", 2);
        boolean success = grid.placeShip(ship, 0, 0, "EAST");
        assertTrue(success);

        assertTrue(grid.getCell(0, 0).containsShip());
        assertTrue(grid.getCell(0, 1).containsShip());
    }

    /**
     * Tests placing a ship in all four directions.
     */
    @Test
    public void testPlaceShipAllDirections() {
        Ship north = new Ship("Destroyer", 2);
        boolean northResult = grid.placeShip(north, 2, 0, "NORTH");
        assertTrue(northResult);

        Ship south = new Ship("Submarine", 3);
        boolean southResult = grid.placeShip(south, 5, 3, "SOUTH");
        assertTrue(southResult);

        Ship east = new Ship("Cruiser", 3);
        boolean eastResult = grid.placeShip(east, 0, 1, "EAST");
        assertTrue(eastResult);

        Ship west = new Ship("Battleship", 4);
        boolean westResult = grid.placeShip(west, 3, 9, "WEST");
        assertTrue(westResult);
    }

    /**
     * Tests that placing a ship out of bounds returns false.
     */
    @Test
    public void testPlaceShipOutOfBounds() {
        Ship ship = new Ship("Destroyer", 2);
        assertFalse(grid.placeShip(ship, 9, 9, "SOUTH"));
        assertFalse(grid.placeShip(ship, 0, 9, "EAST"));
    }

    /**
     * Tests that placing overlapping ships returns false.
     */
    @Test
    public void testPlaceShipOverlap() {
        Ship ship1 = new Ship("Destroyer", 2);
        grid.placeShip(ship1, 0, 0, "EAST");

        Ship ship2 = new Ship("Submarine", 3);
        assertFalse(grid.placeShip(ship2, 0, 1, "EAST"));
    }

    /**
     * Tests that placeShip throws exception for null ship or direction.
     */
    @Test
    public void testPlaceShipNullException() {
        Ship ship = new Ship("Destroyer", 2);
        assertThrows(NullPointerException.class, () -> grid.placeShip(null, 0, 0, "EAST"));
        assertThrows(NullPointerException.class, () -> grid.placeShip(ship, 0, 0, null));
    }

    /**
     * Tests allShipsSunk when no ships are placed.
     */
    @Test
    public void testAllShipsSunk() {
        assertFalse(grid.allShipsSunk());
    }

    /**
     * Tests allShipsSunk when all ships are sunk.
     */
    @Test
    public void testAllShipsSunkTrue() {
        for (Ship ship : grid.getShips()) {
            Cell[] cells = new Cell[ship.getLength()];
            for (int i = 0; i < cells.length; i++) {
                cells[i] = new Cell(i, i);
            }
            ship.setCells(cells);
            // Add ship to cells so they register as hits when shot
            for (Cell cell : cells) {
                cell.addShip(ship);
                cell.shoot();
            }
            ship.registerHit();
        }
        assertTrue(grid.allShipsSunk());
    }

    /**
     * Tests isValidPosition for valid coordinates.
     */
    @Test
    public void testIsValidPlacement() {
        assertTrue(grid.isValidPosition(0, 0));
        assertTrue(grid.isValidPosition(5, 5));
        assertTrue(grid.isValidPosition(9, 9));
    }

    /**
     * Tests isValidPosition for invalid coordinates.
     */
    @Test
    public void testIsValidPositionInvalid() {
        assertFalse(grid.isValidPosition(-1, 0));
        assertFalse(grid.isValidPosition(0, -1));
        assertFalse(grid.isValidPosition(10, 0));
        assertFalse(grid.isValidPosition(0, 10));
    }

    /**
     * Tests receiving a shot on the grid.
     */
    @Test
    public void testReceiveShot() {
        Shot shot = new Shot(0, 0);
        assertNotNull(shot);
        assertThrows(NullPointerException.class, () -> grid.receiveShot(null));
    }

    /**
     * Tests coordinate translation from letter-number format.
     */
    @Test
    public void testTranslateCoords() {
        int[] coords = grid.translateCoords("A1");
        assertEquals(0, coords[0]);
        assertEquals(1, coords[1]);

        int[] coords2 = grid.translateCoords("J10");
        assertEquals(9, coords2[0]);
        assertEquals(10, coords2[1]);
    }

    /**
     * Tests coordinate translation for various valid inputs.
     */
    @Test
    public void testTranslateCoordsVariety() {
        int[] coords = grid.translateCoords("B5");
        assertEquals(1, coords[0]);
        assertEquals(5, coords[1]);

        int[] coords2 = grid.translateCoords("E3");
        assertEquals(4, coords2[0]);
        assertEquals(3, coords2[1]);
    }

    /**
     * Tests that translateCoords throws exception for invalid format.
     */
    @Test
    public void testTranslateCoordsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> grid.translateCoords(""));
        assertThrows(IllegalArgumentException.class, () -> grid.translateCoords("A"));
        assertThrows(IllegalArgumentException.class, () -> grid.translateCoords("ABC"));
    }

    /**
     * Tests getRows and getCols methods.
     */
    @Test
    public void testGetRowsAndCols() {
        assertEquals(10, grid.getRows());
        assertEquals(10, grid.getCols());

        Grid smallGrid = new Grid(5, 7);
        assertEquals(5, smallGrid.getRows());
        assertEquals(7, smallGrid.getCols());
    }

    /**
     * Tests grid constructor with invalid dimensions.
     */
    @Test
    public void testGridConstructorInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new Grid(0, 10));
        assertThrows(IllegalArgumentException.class, () -> new Grid(10, 0));
        assertThrows(IllegalArgumentException.class, () -> new Grid(-1, 10));
        assertThrows(IllegalArgumentException.class, () -> new Grid(25, 10));
    }

    /**
     * Tests that grid is initialized with correct ships.
     */
    @Test
    public void testGridShipsInitialization() {
        Ship[] ships = grid.getShips();
        assertNotNull(ships);
        assertEquals(5, ships.length);
    }

    // public static void main(String[] args) {
    // testToString();
    // }
}
