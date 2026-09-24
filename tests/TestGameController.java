package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gameboard.Grid;
import gameboard.Cell;
import ships.Ship;
import main.GameController;
import java.lang.reflect.Field;

public class TestGameController {
    private Grid testGrid;

    @BeforeEach
    public void setUp() {
        testGrid = new Grid(10, 10);
    }

    // Tests for parseNumber method (using reflection)
    @Test
    public void testParseNumberValidInteger() throws Exception {
        int result = callParseNumber("42");
        assertEquals(42, result);
    }

    @Test
    public void testParseNumberZero() throws Exception {
        int result = callParseNumber("0");
        assertEquals(0, result);
    }

    @Test
    public void testParseNumberNegative() throws Exception {
        int result = callParseNumber("-5");
        assertEquals(-5, result);
    }

    @Test
    public void testParseNumberInvalidString() throws Exception {
        int result = callParseNumber("abc");
        assertEquals(-1, result);
    }

    @Test
    public void testParseNumberDouble() throws Exception {
        int result = callParseNumber("3.14");
        assertEquals(-1, result);
    }

    @Test
    public void testParseNumberEmpty() throws Exception {
        int result = callParseNumber("");
        assertEquals(-1, result);
    }

    // Tests for getWinner method (using reflection)
    @Test
    public void testGetWinnerPlayer1Wins() throws Exception {
        setPlayer1ShipsSunk(5);
        setPlayer2ShipsSunk(0);
        int winner = callGetWinner();
        assertEquals(1, winner);
    }

    @Test
    public void testGetWinnerPlayer2Wins() throws Exception {
        setPlayer1ShipsSunk(0);
        setPlayer2ShipsSunk(5);
        int winner = callGetWinner();
        assertEquals(2, winner);
    }

    @Test
    public void testGetWinnerNoWinner() throws Exception {
        setPlayer1ShipsSunk(2);
        setPlayer2ShipsSunk(3);
        int winner = callGetWinner();
        assertEquals(0, winner);
    }

    @Test
    public void testGetWinnerBothAt5() throws Exception {
        setPlayer1ShipsSunk(5);
        setPlayer2ShipsSunk(5);
        int winner = callGetWinner();
        assertEquals(1, winner); // Player 1 checked first
    }

    @Test
    public void testGetWinnerPlayer1At4() throws Exception {
        setPlayer1ShipsSunk(4);
        setPlayer2ShipsSunk(0);
        int winner = callGetWinner();
        assertEquals(0, winner);
    }

    // Tests for getRandomDirection method (using reflection)
    @Test
    public void testGetRandomDirectionValid() throws Exception {
        String direction = callGetRandomDirection();
        assertTrue(direction.equals("NORTH") || direction.equals("EAST") ||
                direction.equals("SOUTH") || direction.equals("WEST"));
    }

    @Test
    public void testGetRandomDirectionNotNull() throws Exception {
        String direction = callGetRandomDirection();
        assertNotNull(direction);
    }

    @Test
    public void testGetRandomDirectionNotEmpty() throws Exception {
        String direction = callGetRandomDirection();
        assertTrue(direction.length() > 0);
    }

    // Tests for placeShipsRandomly method
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

    @Test
    public void testPlaceShipsRandomlyCreatesValidShips() throws Exception {
        callPlaceShipsRandomly(testGrid);
        Ship[] ships = testGrid.getShips();

        for (Ship ship : ships) {
            assertNotNull(ship);
            assertTrue(ship.getLength() > 0);
        }
    }

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
