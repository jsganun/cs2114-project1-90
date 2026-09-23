package main;

import java.util.ArrayList;

public class AuditSystem {
    private ArrayList<String> list;

    public AuditSystem() {
        list = new ArrayList<>();
    }

    public void recordShot(int row, int col, boolean hit) {
        String shotConnectionMsg = hit ? "HIT" : "MISSED";
        list.add("Shot at (" + row + ", " + col + "): " + shotConnectionMsg);
    }

    // public recordHit(shot: Shot): void

    // public recordMiss(shot: Shot): void

    // public recordSink(shot: Shot): void

    // public recordGameStart(): void

    // public recordGameEnd(): void

    public void printLog() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ". " + list.get(i));
        }
    }

    public void clearLog() {
        list = new ArrayList<>();
    }

}
