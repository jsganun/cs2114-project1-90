package main;

import gameboard.*;
import ships.*;
import java.util.ArrayList;

public class AuditSystem {
    private ArrayList<String> list;

    public AuditSystem() {
        list = new ArrayList<>();
    }

    /**
     * Records a shot in the audit log.
     *
     * @param shot the shot to record
     */
    public void recordShot(int player, Shot shot) {
        String shotConnectionMsg = (shot.getResult() == gameboard.ShotResult.Hit || shot.getResult() == gameboard.ShotResult.Sunk) ? "Hit" : "Missed";
        list.add("Shot by Player " + player + ": " + shotConnectionMsg);
    }

    /**
     * Records the start of the game in the audit log.
     */
    public void recordGameStart() {
        list.add("Game started.");
    }

    /**
     * Records the end of the game in the audit log.
     * 
     * @param winningPlayer the player number (1 or 2) who won the game
     * @param byForfeit true if the game ended by forfeit, false if it ended by all ships being sunk
     */
    public void recordGameEnd(int winningPlayer, boolean byForfeit) {
        if (byForfeit) {
            list.add("Game ended by forfeit. Winning player: " + winningPlayer);
        }
        else {
            list.add("Game ended by all ships sunk. Winning player: " + winningPlayer);
        }
    }

    /**
     * Records the placement of a ship in the audit log.
     *
     * @param player the player number (1 or 2) who placed the ship
     * @param ship the ship that was placed
     */
    public void recordShipPlacement(int player, Ship ship) {
        Cell[] cells = ship.getCells();
        StringBuilder cellPositions = new StringBuilder();

        for (Cell cell : cells) {
            cellPositions.append("(").append(cell.getRow()).append(", ").append(cell.getCol()).append(") ");
        }

        list.add("Player " + player + " placed ship: " + ship.getName() + " of length " + ship.getLength() + " at positions: " + cellPositions.toString());
    }

    /**
     * Records the advancement of a turn in the audit log.
     */
    public void recordTurnAdvance(int player) {
        list.add("Turn advanced to Player " + player);
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
