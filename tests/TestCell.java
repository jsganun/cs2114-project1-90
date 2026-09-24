package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gameboard.Cell;
import ships.Ship;

/**
 * Test class for the Cell component.
 * Verifies cell states, ship placement, and symbol updates.
 */
public class TestCell {

    private Cell cell;
    private Ship ship;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        cell = new Cell(2, 3);
        ship = new Ship("Destroyer", 2);
    }

    /**
     * Tests retrieving the row of the cell.
     */
    @Test
    public void testGetRow() {
        assertEquals(2, cell.getRow());
    }

    /**
     * Tests retrieving the column of the cell.
     */
    @Test
    public void testGetCol() {
        assertEquals(3, cell.getCol());
    }

    /**
     * Tests shooting the cell and verifying symbol updates.
     */
    @Test
    public void testShoot() {
        assertFalse(cell.isShot());

        cell.shoot();
        assertTrue(cell.isShot());
        assertEquals("O", cell.toString());

        Cell hitCell = new Cell(0, 0);
        hitCell.addShip(ship);
        hitCell.shoot();
        assertTrue(hitCell.isShot());
        assertEquals("X", hitCell.toString());
    }

    /**
     * Tests whether the cell accurately tracks if it was shot.
     */
    @Test
    public void testIsShot() {
        assertFalse(cell.isShot());
        cell.shoot();
        assertTrue(cell.isShot());
    }

    /**
     * Tests adding a ship to the cell and handles edge cases.
     */
    @Test
    public void testAddShip() {
        assertFalse(cell.containsShip());

        boolean success = cell.addShip(ship);
        assertTrue(success);
        assertTrue(cell.containsShip());

        Ship ship2 = new Ship("Submarine", 3);
        boolean success2 = cell.addShip(ship2);
        assertFalse(success2);

        boolean caughtNullShip = false;
        try {
            cell.addShip(null);
        } catch (NullPointerException e) {
            caughtNullShip = true;
        }
        assertTrue(caughtNullShip);
    }

    /**
     * Tests whether the cell correctly reports holding a ship.
     */
    @Test
    public void testContainsShip() {
        assertFalse(cell.containsShip());
        cell.addShip(ship);
        assertTrue(cell.containsShip());
    }

    /**
     * Tests the string representation of the cell.
     */
    @Test
    public void testToString() {
        assertEquals("-", cell.toString());

        cell.shoot();
        assertEquals("O", cell.toString());
    }
}
