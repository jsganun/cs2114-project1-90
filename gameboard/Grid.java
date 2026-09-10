package gameboard;

import ships.Ship;

public class Grid {
    private final Cell[][] arr;

    public Grid() {
        arr = new Cell[10][10];
        fillArray();
    }

    private void fillArray() {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = new Cell(i, j);
            }
        }
    }

    public void createShip(int x, int y) {
        arr[x][y].addShip(new Ship(arr[x][y]));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                // TODO
            }
        }
        return sb.toString();
    }

}
