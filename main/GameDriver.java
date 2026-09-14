package main;

import gameboard.*;

/**
 * Entry point for the game application.
 */
public class GameDriver {
    private static void printGrid(Grid grid) {
        System.out.println(grid.toString());
    }

    /**
     * Starts the game application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // TODO
        Grid grid = new Grid();
        printGrid(grid);
    }
}
