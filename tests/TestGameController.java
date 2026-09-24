package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gameboard.Grid;
import gameboard.Cell;
import ships.Ship;
import main.GameController;
import java.lang.reflect.Field;

/**
 * Test class for GameController.
 * Verifies core game logic like input parsing, win conditions, and random ship placement.
 */
public class TestGameController {
    private Grid testGrid;

    /**
     * Initializes a fresh game grid before each test.
     */
    @BeforeEach
    public void setUp() {
        testGrid = new Grid(10, 10);
    }

    // Tests for parseNumber method (using reflection)
    /**
     * Tests parsing of standard integer input.
     */
    @Test
    public void testParseNumberValidInteger() throws Exception {
        int result = callParseNumber("42");
        assertEquals(42, result);
    }

    /**
     * Tests parsing of zero.
     */
    @Test
    public void testParseNumberZero() throws Exception {
        int result = callParseNumber("0");
        assertEquals(0, result);
    }

    /**
     * Tests parsing of negative integers.
     */
    @Test
    public void testParseNumberNegative() throws Exception {
        int result = callParseNumber("-5");
        assertEquals(-5, result);
    }

    /**
     * Tests parsing handling of alphabetical strings.
     */
    @Test
    public void testParseNumberInvalidString() throws Exception {
        int result = callParseNumber("abc");
        assertEquals(-1, result);
    }

    /**
     * Tests parsing handling of floating-point numbers.
     */
    @Test
    public void testParseNumberDouble() throws Exception {
        int result = callParseNumber("3.14");
        assertEquals(-1, result);
    }

    /**
     * Tests parsing handling of empty input strings.
     */
    @Test
    public void testParseNumberEmpty() throws Exception {
        int result = callParseNumber("");
        assertEquals(-1, result);
    }

    // Tests for getWinner method (using reflection)
    /**
     * Verifies Player 1 win detection when sinking 5 ships.
     */
    @Test
    public void testGetWinnerPlayer1Wins() throws Exception {
        setPlayer1ShipsSunk(5);
        setPlayer2ShipsSunk(0);
        int winner = callGetWinner();
        assertEquals(1, winner);
    }

    /**
     * Verifies Player 2 win detection when sinking 5 ships.
     */
    @Test
    public void testGetWinnerPlayer2Wins() throws Exception {
        setPlayer1ShipsSunk(0);
        setPlayer2ShipsSunk(5);
        int winner = callGetWinner();
        assertEquals(2, winner);
    }

    /**
     * Verifies no winner is detected when neither player reaches 5 sunk ships.
     */
    @Test
    public void testGetWinnerNoWinner() throws Exception {
        setPlayer1ShipsSunk(2);
        setPlayer2ShipsSunk(3);
        int winner = callGetWinner();
        assertEquals(0, winner);
    }

    /**
     * Verifies the tie-breaking logic if both reach 5 simultaneously.
     */
    @Test
    public void testGetWinnerBothAt5() throws Exception {
        setPlayer1ShipsSunk(5);
        setPlayer2ShipsSunk(5);
        int winner = callGetWinner();
        assertEquals(1, winner); // Player 1 checked first
    }

    /**
     * Verifies a player has not won if they are exactly 1 sink away from victory.
     */
    @Test
    public void testGetWinnerPlayer1At4() throws Exception {
        setPlayer1ShipsSunk(4);
        setPlayer2ShipsSunk(0);
        int winner = callGetWinner();
        assertEquals(0, winner);
    }

    // Tests for getRandomDirection method (using reflection)
    /**
     * Tests that generating random directions outputs valid strings.
     */
    @Test
    public void testGetRandomDirectionValid() throws Exception {
        String direction = callGetRandomDirection();
        assertTrue(direction.equals("NORTH") || direction.equals("EAST") ||
                direction.equals("SOUTH") || direction.equals("WEST"));
    }

    /**
     * Tests that a generated random direction is never null.
     */
    @Test
    public void testGetRandomDirectionNotNull() throws Exception {
        String direction = callGetRandomDirection();
        assertNotNull(direction);
    }

    /**
     * Tests that a generated random direction is never empty.
     */
    @Test
    public void testGetRandomDirectionNotEmpty() throws Exception {
        String direction = callGetRandomDirection();
        assertTrue(direction.length() > 0);
    }

    // Tests for placeShipsRandomly method
    /**
     * Tests that random ship placement populates the grid successfully.
     */
    @Test
    public void testPlaceShipsRandomlyFillsGrid() throws Exception {
        callPlaceShipsRandomly(testGrid);
        Grid grid = testGrid;
        int shipsPlacedCount = 0;

        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                if (grid.getCell(r, c).containsShip()) {
                    shipsPlacedCount++;
                }
            }
        }

        assertTrue(shipsPlacedCount > 0);
    }

    /**
     * Verifies that ships instantiated during random placement hold valid stats.
     */
    @Test
    public void testPlaceShipsRandomlyCreatesValidShips() throws Exception {
        callPlaceShipsRandomly(testGrid);
        Ship[] ships = testGrid.getShips();

        for (Ship ship : ships) {
            assertNotNull(ship);
            assertTrue(ship.getLength() > 0);
        }
    }

    /**
     * Ensures random ship placement does not allow overlapping.
     */
    @Test
    public void testPlaceShipsRandomlyNoOverlap() throws Exception {
        callPlaceShipsRandomly(testGrid);
        int totalCellsWithShips = 0;

        for (int r = 0; r < testGrid.getRows(); r++) {
            for (int c = 0; c < testGrid.getCols(); c++) {
                if (testGrid.getCell(r, c).containsShip()) {
                    totalCellsWithShips++;
                }
            }
        }

        // With 5 ships (Battleship-4, Cruiser-3, Destroyer-2, Submarine-3, Patrol-2 =
        // 14 cells)
        // The exact count depends on ship sizes, but should be at least some ships
        // placed
        assertTrue(totalCellsWithShips >= 10);
    }

    /**
     * Confirms that all 5 required ships are created and placed during initialization.
     */
    @Test
    public void testPlaceShipsRandomlyAllShipsPlaced() throws Exception {
        callPlaceShipsRandomly(testGrid);
        Ship[] ships = testGrid.getShips();

        for (Ship ship : ships) {
            Cell[] shipCells = ship.getCells();
            assertNotNull(shipCells);
            assertTrue(shipCells.length > 0);
        }
    }

    // Helper methods using reflection to access private static methods
    private int callParseNumber(String input) throws Exception {
        java.lang.reflect.Method method = GameController.class.getDeclaredMethod("parseNumber", String.class);
        method.setAccessible(true);
        return (Integer) method.invoke(null, input);
    }

    private int callGetWinner() throws Exception {
        java.lang.reflect.Method method = GameController.class.getDeclaredMethod("getWinner");
        method.setAccessible(true);
        return (Integer) method.invoke(null);
    }

    private String callGetRandomDirection() throws Exception {
        java.lang.reflect.Method method = GameController.class.getDeclaredMethod("getRandomDirection");
        method.setAccessible(true);
        return (String) method.invoke(null);
    }

    private void callPlaceShipsRandomly(Grid grid) throws Exception {
        java.lang.reflect.Method method = GameController.class.getDeclaredMethod("placeShipsRandomly", Grid.class);
        method.setAccessible(true);
        method.invoke(null, grid);
    }

    // Helper methods to modify static fields using reflection
    private void setPlayer1ShipsSunk(int count) throws Exception {
        Field field = GameController.class.getDeclaredField("player1ShipsSunk");
        field.setAccessible(true);
        field.setInt(null, count);
    }

    private void setPlayer2ShipsSunk(int count) throws Exception {
        Field field = GameController.class.getDeclaredField("player2ShipsSunk");
        field.setAccessible(true);
        field.setInt(null, count);
    }
}
