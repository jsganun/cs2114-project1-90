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

    /**
     * Tests shot with zero coordinates.
     */
    @Test
    public void testShotZeroCoordinates() {
        Shot zeroShot = new Shot(0, 0);
        assertNotNull(zeroShot);
        assertEquals(0, zeroShot.getRow());
        assertEquals(0, zeroShot.getCol());
        assertEquals(ShotResult.None, zeroShot.getResult());
    }

    /**
     * Tests shot with negative coordinates.
     */
    @Test
    public void testShotNegativeCoordinates() {
        Shot negativeShot = new Shot(-1, -2);
        assertNotNull(negativeShot);
        assertEquals(-1, negativeShot.getRow());
        assertEquals(-2, negativeShot.getCol());
    }

    /**
     * Tests shot with large coordinates.
     */
    @Test
    public void testShotLargeCoordinates() {
        Shot largeShot = new Shot(999, 999);
        assertNotNull(largeShot);
        assertEquals(999, largeShot.getRow());
        assertEquals(999, largeShot.getCol());
    }

    /**
     * Tests multiple result changes on the same shot.
     */
    @Test
    public void testMultipleResultChanges() {
        shot.setResult(ShotResult.Hit);
        assertEquals(ShotResult.Hit, shot.getResult());

        shot.setResult(ShotResult.Miss);
        assertEquals(ShotResult.Miss, shot.getResult());

        shot.setResult(ShotResult.Sunk);
        assertEquals(ShotResult.Sunk, shot.getResult());

        shot.setResult(ShotResult.None);
        assertEquals(ShotResult.None, shot.getResult());
    }

    /**
     * Tests that setResult throws NullPointerException.
     */
    @Test
    public void testSetResultNullThrows() {
        assertThrows(NullPointerException.class, () -> shot.setResult(null));
    }

    /**
     * Tests toString after each result change.
     */
    @Test
    public void testToStringAfterResultChange() {
        shot.setResult(ShotResult.Miss);
        assertEquals("Shot(2, 3) -> Miss", shot.toString());

        shot.setResult(ShotResult.Sunk);
        assertEquals("Shot(2, 3) -> Sunk", shot.toString());
    }
}
