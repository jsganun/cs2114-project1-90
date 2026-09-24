package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import gameboard.ShotResult;

/**
 * Test class for the ShotResult enum.
 * Verifies enum values and functionality.
 */
public class TestShotResult {

    /**
     * Tests that all enum constants exist and have expected values.
     */
    @Test
    public void testShotResultEnumConstants() {
        assertNotNull(ShotResult.None);
        assertNotNull(ShotResult.Hit);
        assertNotNull(ShotResult.Miss);
        assertNotNull(ShotResult.Sunk);
    }

    /**
     * Tests that enum values can be retrieved by name.
     */
    @Test
    public void testShotResultValueOf() {
        assertEquals(ShotResult.None, ShotResult.valueOf("None"));
        assertEquals(ShotResult.Hit, ShotResult.valueOf("Hit"));
        assertEquals(ShotResult.Miss, ShotResult.valueOf("Miss"));
        assertEquals(ShotResult.Sunk, ShotResult.valueOf("Sunk"));
    }

    /**
     * Tests the default ShotResult is None.
     */
    @Test
    public void testShotResultDefaultIsNone() {
        ShotResult result = ShotResult.None;
        assertEquals(ShotResult.None, result);
    }

    /**
     * Tests that ShotResult enum has the correct number of constants.
     */
    @Test
    public void testShotResultEnumCount() {
        ShotResult[] values = ShotResult.values();
        assertEquals(4, values.length);
    }

    /**
     * Tests the string representation of ShotResult values.
     */
    @Test
    public void testShotResultToString() {
        assertEquals("None", ShotResult.None.toString());
        assertEquals("Hit", ShotResult.Hit.toString());
        assertEquals("Miss", ShotResult.Miss.toString());
        assertEquals("Sunk", ShotResult.Sunk.toString());
    }

    /**
     * Tests that all ShotResult values are distinct.
     */
    @Test
    public void testShotResultDistinct() {
        assertNotEquals(ShotResult.None, ShotResult.Hit);
        assertNotEquals(ShotResult.Hit, ShotResult.Miss);
        assertNotEquals(ShotResult.Miss, ShotResult.Sunk);
        assertNotEquals(ShotResult.None, ShotResult.Sunk);
    }

    /**
     * Tests ShotResult equality.
     */
    @Test
    public void testShotResultEquality() {
        assertEquals(ShotResult.Hit, ShotResult.Hit);
        assertEquals(ShotResult.Miss, ShotResult.Miss);
    }
}
