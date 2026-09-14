package tests;

import gameboard.Grid;

public class TestGrid {
    public static void testToString() {
        Grid grid = new Grid();
        String expected = """
                  1 2 3 4 5 6 7 8 9 10
                A - - - - - - - - - -
                B - - - - - - - - - -
                C - - - - - - - - - -
                D - - - - - - - - - -
                E - - - - - - - - - -
                F - - - - - - - - - -
                G - - - - - - - - - -
                H - - - - - - - - - -
                I - - - - - - - - - -
                J - - - - - - - - - -
                                """;
        if (!grid.toString().equals(expected)) {
            System.out.println("toString() failed");
        }
    }

    public static void main(String[] args) {
        testToString();
    }
}
