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

    public void createShip(int r, int c) {
        arr[r][c].addShip(new Ship(arr[r][c]));
    }

    public void shoot(int r, int c) {
        Cell target = arr[r][c];
        Shot shot = new Shot(target);
        boolean hit = shot.shoot();
        if (hit) {
            target.reveal(hit);
        }
    }

    public void translateCoords(String input) {
        if (input.length() < 2 || input.length() > 3) {
            throw new IllegalArgumentException();
        }
        if (input.length() == 3 && !isNumber(input.substring(1, 2))) {
            throw new IllegalArgumentException();
        }
        String str = input.toUpperCase();
        int row = letterToIndex(str.charAt(0));
        int col;
        if (input.length() == 3) {
            col = Integer.parseInt(str.substring(1, 3)); // 10
        } else {
            col = Integer.parseInt(str.substring(1, 2)); // < 10
        }
    }

    private int letterToIndex(char letter) {
        // char 'A' is 65 -> index 0
        // char 'J' is 74 -> index 9
        return letter - 65;
    }

    private boolean isNumber(String in) {
        try {
            Integer.valueOf(in);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
