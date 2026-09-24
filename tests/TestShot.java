package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gameboard.Shot;
import gameboard.ShotResult;

/**
 * Test class for the Shot component.
 * Verifies standard behavior and exception handling for invalid inputs.
 */
public class TestShot {

    private Shot shot;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        shot = new Shot(2, 3);
    }

    /**
     * Tests the Shot constructor.
     */
    @Test
    public void testShot() {
        assertNotNull(shot);
        assertEquals(2, shot.getRow());
        assertEquals(3, shot.getCol());
        assertEquals(ShotResult.None, shot.getResult());
    }

    /**
     * Tests retrieving the shot's row.
     */
    @Test
    public void testGetRow() {
        assertEquals(2, shot.getRow());
    }

    /**
     * Tests retrieving the shot's column.
     */
    @Test
    public void testGetCol() {
        assertEquals(3, shot.getCol());
    }

    /**
     * Tests the default result of a newly created shot.
     */
    @Test
    public void testGetResultDefault() {
        assertEquals(ShotResult.None, shot.getResult());
    }

    /**
     * Tests setting the result of the shot and handles null assignment.
     */
    @Test
    public void testSetResult() {
        shot.setResult(ShotResult.Hit);
        assertEquals(ShotResult.Hit, shot.getResult());

        shot.setResult(ShotResult.Miss);
        assertEquals(ShotResult.Miss, shot.getResult());

        shot.setResult(ShotResult.Sunk);
        assertEquals(ShotResult.Sunk, shot.getResult());

        boolean caughtNullResult = false;
        try {
            shot.setResult(null);
        } catch (NullPointerException e) {
            caughtNullResult = true;
        }
        assertTrue(caughtNullResult);
    }

    /**
     * Tests the string representation of the shot.
     */
    @Test
    public void testToString() {
        assertEquals("Shot(2, 3) -> None", shot.toString());

        shot.setResult(ShotResult.Hit);
        assertEquals("Shot(2, 3) -> Hit", shot.toString());
    }
}
