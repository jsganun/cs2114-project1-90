package main;

import java.util.ArrayList;

public class AuditSystem {
    private ArrayList<String> list;

    public AuditSystem() {
        list = new ArrayList<>();
    }

    public void recordShot(int playerNum, int row, int col, boolean hit) {
        String shotConnectionMsg = hit ? "HIT" : "MISSED";
        list.add("Player " + playerNum + " shot at (" + row + ", " + col + "): " + shotConnectionMsg);
    }

    public void recordSink(int playerNum, int row, int col) {
        list.add("Player " + playerNum + " sank a ship at (" + row + ", " + col + ")");
    }

    public void recordGameStart() {
        list.add("Game started");
    }

    public void recordGameEnd() {
        list.add("Game ended");
    }

    public void printLog() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ". " + list.get(i));
        }
    }

    public void clearLog() {
        list = new ArrayList<>();
    }

}
