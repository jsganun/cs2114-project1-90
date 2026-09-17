package main;

import gameboard.*;
import java.util.Scanner;

/**
 * Entry point for the game application.
 */
public class GameController {
    private static void printGrid(Grid grid) {
        System.out.println(grid.toString());
    }

    private static void printMenu(Grid grid, int currentPlayerMove) {
        String menu = """
                1. Shot
                2. Print grid
                3. Print log
                """;
        System.out.println("Current move: Player"
                + currentPlayerMove + "\n" + menu);
    }

    private static void runGame(Scanner input) {
        Grid grid = new Grid();
        int currentPlayerMove = 1;
        while (true) {
            printGrid(grid);
            printMenu(grid, currentPlayerMove);
            currentPlayerMove = currentPlayerMove == 1 ? 2 : 1;
        }
    }

    /**
     * Starts the game application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        try (Scanner input = new Scanner(System.in)) {
            runGame(input);
        }

    }
}
