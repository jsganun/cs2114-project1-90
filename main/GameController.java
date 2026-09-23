package main;

import gameboard.*;
import java.util.Scanner;

/**
 * Entry point for the game application.
 */
public class GameController {
    private static final String LARGE_WHITESPACE = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    private static void printGrid(Grid grid) {
        System.out.print(grid.toString());
    }

    private static void printMenu(int currentPlayerMove) {
        String menu = """
                0. Quit
                1. Shoot
                2. Print grid
                3. Print log
                """;
        String separator = "--------------------------";
        System.out.println(separator + "\nCurrent move: Player "
                + currentPlayerMove + "\n" + menu + separator);
    }

    private static int parseNumber(String in) {
        int testInt;
        try {
            Double.valueOf(in);
            return -1;
        } catch (NumberFormatException e) {
            // This is good. The user did not input a double.
            // No action needed here.
        }
        try {
            testInt = Integer.parseInt(in);
        } catch (NumberFormatException e) {
            return -1; // This is bad. The user did not input an int.
        }
        return testInt;
    }

    private static void processShotInput(Scanner input, AuditSystem log, Grid grid) {
        String in;
        int row = -1;
        int col = -1;
        while (true) {
            System.out.println("Please enter the row:");
            in = input.nextLine();
            row = parseNumber(in);
            if (row > -1) {
                break;
            }
        }
        while (true) {
            System.out.println("Please enter the column:");
            in = input.nextLine();
            col = parseNumber(in);
            if (col > -1) {
                break;
            }
        }
        boolean hit = true; // temp
        log.recordShot(row, col, hit);
    }

    private static void processUserInput(Scanner input, AuditSystem log, Grid grid) {
        String in = input.nextLine();
        int num = -1;
        while (num == -1 || num > 3) {
            System.out.println("Please enter a number: ");
            num = parseNumber(in);
        }
        switch (num) {
            case 0 -> {
                System.out.println("Quitting...");
                System.exit(0);
            }
            case 1 -> {
                processShotInput(input, log, grid);
            }
        }
    }

    private static void runGame(Scanner input, AuditSystem log) {
        Grid grid = new Grid(10, 10);
        int currentPlayerMove = 1;
        while (true) {
            printGrid(grid);
            printMenu(1);
            processUserInput(input, log, grid);
        }
    }

    /**
     * Starts the game application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        AuditSystem log = new AuditSystem();
        try (Scanner input = new Scanner(System.in)) {
            runGame(input, log);
        }

    }
}
