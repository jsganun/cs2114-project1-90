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

    /**
     * Tests the reveal method with a hit.
     */
    @Test
    public void testRevealHit() {
        cell.reveal(true);
        assertEquals("X", cell.toString());
    }

    /**
     * Tests the reveal method with a miss.
     */
    @Test
    public void testRevealMiss() {
        cell.reveal(false);
        assertEquals("O", cell.toString());
    }

    /**
     * Tests retrieving the ship from a cell.
     */
    @Test
    public void testGetShip() {
        assertNull(cell.getShip());

        cell.addShip(ship);
        assertNotNull(cell.getShip());
        assertEquals(ship, cell.getShip());
    }

    /**
     * Tests the equals method with the same cell coordinates.
     */
    @Test
    public void testEqualsTrue() {
        Cell cell2 = new Cell(2, 3);
        assertTrue(cell.equals(cell2));
    }

    /**
     * Tests the equals method with different cell coordinates.
     */
    @Test
    public void testEqualsFalse() {
        Cell cell2 = new Cell(2, 4);
        assertFalse(cell.equals(cell2));

        Cell cell3 = new Cell(3, 3);
        assertFalse(cell.equals(cell3));
    }

    /**
     * Tests the equals method with null.
     */
    @Test
    public void testEqualsNull() {
        assertFalse(cell.equals(null));
    }

    /**
     * Tests the equals method with a different object type.
     */
    @Test
    public void testEqualsDifferentType() {
        assertFalse(cell.equals("not a cell"));
        assertFalse(cell.equals(5));
    }

    /**
     * Tests the equals method with the same object instance.
     */
    @Test
    public void testEqualsSameInstance() {
        assertTrue(cell.equals(cell));
    }
}
