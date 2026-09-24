package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import main.AuditSystem;

/**
 * Test class for the AuditSystem.
 * Verifies system output, log recording, and chronological formatting.
 */
public class TestAuditLog {
    private AuditSystem auditSystem;
    private ByteArrayOutputStream outputStream;

    /**
     * Sets up the output stream capture before each test.
     */
    @BeforeEach
    public void setUp() {
        auditSystem = new AuditSystem();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Tests standard initialization and log clearing behavior.
     */
    @Test
    public void testConstructor() {
        assertNotNull(auditSystem);
        // Verify it starts empty by recording and clearing
        auditSystem.recordGameStart();
        auditSystem.clearLog();
        // After clear, log should be empty (no output when printing)
        auditSystem.printLog();
        assertEquals("", outputStream.toString());
    }

    /**
     * Tests recording a successful hit.
     */
    @Test
    public void testRecordShotHit() {
        auditSystem.recordShot(1, 5, 7, true);
        auditSystem.printLog();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Player 1 shot at (5, 7): HIT"));
    }

    /**
     * Tests recording a missed shot.
     */
    @Test
    public void testRecordShotMiss() {
        auditSystem.recordShot(2, 3, 4, false);
        auditSystem.printLog();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Player 2 shot at (3, 4): MISSED"));
    }

    /**
     * Tests recording a sunken ship.
     */
    @Test
    public void testRecordSink() {
        auditSystem.recordSink(1, 2, 8);
        auditSystem.printLog();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Player 1 sank a ship at (2, 8)"));
    }

    /**
     * Tests recording the start of the game.
     */
    @Test
    public void testRecordGameStart() {
        auditSystem.recordGameStart();
        auditSystem.printLog();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Game started"));
    }

    /**
     * Tests recording the end of the game.
     */
    @Test
    public void testRecordGameEnd() {
        auditSystem.recordGameEnd();
        auditSystem.printLog();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Game ended"));
    }

    /**
     * Tests recording the winner of the game.
     */
    @Test
    public void testRecordGameWinner() {
        auditSystem.recordGameWinner(1);
        auditSystem.printLog();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Player 1 won the game."));
    }

    /**
     * Tests recording multiple distinct log entries in a sequence.
     */
    @Test
    public void testMultipleRecords() {
        auditSystem.recordGameStart();
        auditSystem.recordShot(1, 0, 0, true);
        auditSystem.recordShot(2, 1, 1, false);
        auditSystem.recordSink(1, 2, 2);
        auditSystem.recordGameEnd();
        auditSystem.recordGameWinner(1);

        auditSystem.printLog();
        String output = outputStream.toString();

        assertTrue(output.contains("Game started"));
        assertTrue(output.contains("Player 1 shot at (0, 0): HIT"));
        assertTrue(output.contains("Player 2 shot at (1, 1): MISSED"));
        assertTrue(output.contains("Player 1 sank a ship at (2, 2)"));
        assertTrue(output.contains("Game ended"));
        assertTrue(output.contains("Player 1 won the game."));
    }

    /**
     * Tests fully clearing all log history.
     */
    @Test
    public void testClearLog() {
        auditSystem.recordGameStart();
        auditSystem.recordShot(1, 5, 5, true);
        auditSystem.recordGameEnd();

        auditSystem.clearLog();

        outputStream.reset();
        auditSystem.printLog();
        assertEquals("", outputStream.toString());
    }

    /**
     * Tests that printed logs maintain sequential numbering.
     */
    @Test
    public void testPrintLogSequence() {
        auditSystem.recordGameStart();
        auditSystem.recordShot(1, 3, 3, true);
        auditSystem.recordShot(1, 3, 4, false);

        auditSystem.printLog();
        String output = outputStream.toString();

        // Check that entries are numbered starting from 0
        assertTrue(output.contains("0. Game started"));
        assertTrue(output.contains("1. Player 1 shot at (3, 3): HIT"));
        assertTrue(output.contains("2. Player 1 shot at (3, 4): MISSED"));
    }

    /**
     * Tests large volumes of records to ensure correct buffer sizing.
     */
    @Test
    public void testLargeGameLog() {
        auditSystem.recordGameStart();

        // Simulate multiple rounds
        for (int i = 0; i < 10; i++) {
            auditSystem.recordShot(1, i, i, i % 2 == 0);
            auditSystem.recordShot(2, i, i + 1, i % 3 == 0);
        }

        auditSystem.recordGameEnd();
        auditSystem.recordGameWinner(1);

        auditSystem.printLog();
        String output = outputStream.toString();

        assertTrue(output.contains("Game started"));
        assertTrue(output.contains("Game ended"));
        assertTrue(output.contains("Player 1 won the game."));
        assertTrue(output.split("\n").length >= 22); // Game start + 20 shots + Game end + Winner
    }
}
