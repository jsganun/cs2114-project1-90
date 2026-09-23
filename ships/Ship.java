package ships;

import gameboard.Cell;

public class Ship {
    private String name;
    private int length;
    private Cell[] cells;
    private int hitsTaken;

    public Ship(String name, int length) {
        if (name == null || length <= 0) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.length = length;
        this.cells = new Cell[length];
        this.hitsTaken = 0;
    }

    public boolean isHit() {
        return hitsTaken > 0;
    }

    public boolean isSunk() {
        return hitsTaken >= length;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public void setCells(Cell[] cells) {
        if (cells == null) {
            throw new IllegalArgumentException();
        }
        this.cells = cells;
    }

    public Cell[] getCells() {
        return cells;
    }

    public boolean containsCell(Cell cell) {
        if (cell == null) {
            throw new NullPointerException();
        }
        if (cells != null) {
            for (int i = 0; i < cells.length; i++) {
                if (cells[i] != null && cells[i].equals(cell)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void registerHit() {
        int hits = 0;
        if (cells != null) {
            for (int i = 0; i < cells.length; i++) {
                if (cells[i] != null && cells[i].toString().equals("X")) {
                    hits++;
                }
            }
        }
        this.hitsTaken = hits;
    }

    public String toString() {
        return name + ", " + length + ", " + hitsTaken + ", " + isSunk();
    }
}
