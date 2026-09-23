package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ships.Ship;
import gameboard.Cell;

public class TestShip {

    private Ship ship;
    private Cell cell1;
    private Cell cell2;
    private Cell[] cells;

    @BeforeEach
    public void setUp() {
        ship = new Ship("Battleship", 4);
        cell1 = new Cell(1, 1);
        cell2 = new Cell(1, 2);
        cells = new Cell[] { cell1, cell2, new Cell(1, 3), new Cell(1, 4) };
    }

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

    @Test
    public void testGetName() {
        assertEquals("Battleship", ship.getName());
    }

    @Test
    public void testGetLength() {
        assertEquals(4, ship.getLength());
    }

    @Test
    public void testIsHit() {
        assertFalse(ship.isHit());

        ship.setCells(cells);
        ship.registerHit();
        assertFalse(ship.isHit());
    }

    @Test
    public void testIsSunk() {
        assertFalse(ship.isSunk());

        ship.setCells(cells);
        ship.registerHit();
        assertFalse(ship.isSunk());
    }

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

    @Test
    public void testGetCells() {
        assertEquals(4, ship.getCells().length);
        assertNull(ship.getCells()[0]);

        ship.setCells(cells);
        assertNotNull(ship.getCells()[0]);
    }

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

    @Test
    public void testRegisterHit() {
        ship.setCells(cells);
        ship.registerHit();
        assertFalse(ship.isHit());
    }

    @Test
    public void testToString() {
        assertEquals("Battleship, 4, 0, false", ship.toString());
    }
}
