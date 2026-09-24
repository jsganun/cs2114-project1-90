package main;

import java.util.ArrayList;

public class AuditSystem {
    private ArrayList<String> list;

    /**
     * Constructs a new AuditSystem with an empty log.
     */
    public AuditSystem() {
        list = new ArrayList<>();
    }

    /**
     * Records a shot attempt by a player at a specific board coordinate.
     *
     * @param playerNum the player number (1 or 2) taking the shot
     * @param row       the row coordinate of the target
     * @param col       the column coordinate of the target
     * @param hit       {@code true} if the shot hit a ship; {@code false} if it
     *                  missed
     */
    public void recordShot(int playerNum, int row, int col, boolean hit) {
        String shotConnectionMsg = hit ? "HIT" : "MISSED";
        list.add("Player " + playerNum + " shot at (" + row + ", " + col + "): " + shotConnectionMsg);
    }

    /**
     * Records when a player sinks an opponent's ship.
     *
     * @param playerNum the player number (1 or 2) who sank the ship
     * @param row       the row coordinate of the sunk ship
     * @param col       the column coordinate of the sunk ship
     */
    public void recordSink(int playerNum, int row, int col) {
        list.add("Player " + playerNum + " sank a ship at (" + row + ", " + col + ")");
    }

    /**
     * Records the start of a new game.
     */
    public void recordGameStart() {
        list.add("Game started");
    }

    /**
     * Records the end of the current game.
     */
    public void recordGameEnd() {
        list.add("Game ended");
    }

    /**
     * Records which player won the game.
     *
     * @param playerNum the player number (1 or 2) who won the game
     */
    public void recordGameWinner(int playerNum) {
        list.add("Player " + playerNum + " won the game.");
    }

    /**
     * Prints the audit log.
     */
    public void printLog() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ". " + list.get(i));
        }
    }

    /**
     * Clears the audit log.
     */
    public void clearLog() {
        list = new ArrayList<>();
    }

}
