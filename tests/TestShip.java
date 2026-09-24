package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ships.Ship;
import gameboard.Cell;

/**
 * Test class for the Ship component.
 * Verifies standard behavior and exception handling for invalid inputs.
 */
public class TestShip {

    private Ship ship;
    private Cell cell1;
    private Cell cell2;
    private Cell[] cells;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        ship = new Ship("Battleship", 4);
        cell1 = new Cell(1, 1);
        cell2 = new Cell(1, 2);
        cells = new Cell[] { cell1, cell2, new Cell(1, 3), new Cell(1, 4) };
    }

    /**
     * Tests the Ship constructor and its exception handling.
     */
    @Test
    public void testShip() {
        assertNotNull(ship);

        boolean caughtNullName = false;
        try {
            new Ship(null, 3);
        } catch (IllegalArgumentException e) {
            caughtNullName = true;
        }
        assertTrue(caughtNullName);

        boolean caughtZeroLength = false;
        try {
            new Ship("Destroyer", 0);
        } catch (IllegalArgumentException e) {
            caughtZeroLength = true;
        }
        assertTrue(caughtZeroLength);
    }

    /**
     * Tests retrieving the ship's name.
     */
    @Test
    public void testGetName() {
        assertEquals("Battleship", ship.getName());
    }

    /**
     * Tests retrieving the ship's length.
     */
    @Test
    public void testGetLength() {
        assertEquals(4, ship.getLength());
    }

    /**
     * Tests whether the ship properly registers being hit.
     */
    @Test
    public void testIsHit() {
        assertFalse(ship.isHit());

        ship.setCells(cells);
        ship.registerHit();
        assertFalse(ship.isHit());
    }

    /**
     * Tests the sunk status of the ship.
     */
    @Test
    public void testIsSunk() {
        assertFalse(ship.isSunk());

        ship.setCells(cells);
        ship.registerHit();
        assertFalse(ship.isSunk());
    }

    /**
     * Tests assigning cells to the ship and handles null assignment.
     */
    @Test
    public void testSetCells() {
        ship.setCells(cells);
        assertNotNull(ship.getCells());

        boolean caughtNullCells = false;
        try {
            ship.setCells(null);
        } catch (IllegalArgumentException e) {
            caughtNullCells = true;
        }
        assertTrue(caughtNullCells);
    }

    /**
     * Tests retrieving the array of cells occupied by the ship.
     */
    @Test
    public void testGetCells() {
        assertEquals(4, ship.getCells().length);
        assertNull(ship.getCells()[0]);

        ship.setCells(cells);
        assertNotNull(ship.getCells()[0]);
    }

    /**
     * Tests if the ship accurately determines whether it occupies a specific cell.
     */
    @Test
    public void testContainsCell() {
        ship.setCells(cells);

        assertTrue(ship.containsCell(cell1));
        assertFalse(ship.containsCell(new Cell(5, 5)));

        boolean caughtNullCell = false;
        try {
            ship.containsCell(null);
        } catch (NullPointerException e) {
            caughtNullCell = true;
        }
        assertTrue(caughtNullCell);
    }

    /**
     * Tests that a hit is successfully registered on the ship.
     */
    @Test
    public void testRegisterHit() {
        ship.setCells(cells);
        ship.registerHit();
        assertFalse(ship.isHit());
    }

    /**
     * Tests the string representation of the ship.
     */
    @Test
    public void testToString() {
        assertEquals("Battleship, 4, 0, false", ship.toString());
    }
}
