package main;

import gameboard.*;
import java.util.Scanner;
import ships.Ship;

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
                3. Print menu
                4. Print log
                """;
        String separator = "--------------------------";
        System.out.println(separator + "\nCurrent move: Player "
                + currentPlayerMove + "\n" + menu + separator);
    }

    private static int parseNumber(String in) {
        int testInt = -1;
        try {
            Double.valueOf(in);
        } catch (NumberFormatException e) {
            // This is good. The user did not input a double.
            // No action needed here.
        }
        try {
            testInt = Integer.parseInt(in);
        } catch (NumberFormatException e) {
            // This is bad. The user did not input an int.
        }
        return testInt;
    }

    private static void processShotInput(Scanner input, AuditSystem log, Grid grid, int currentPlayerMove) {
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
        Cell cell = grid.getCell(row, col);
        boolean hit = cell.containsShip();
        log.recordShot(currentPlayerMove, row, col, hit);
        if (hit) {
            System.out.println("You hit a ship!");
        } else {
            System.out.println("You missed.");
        }
        Ship ship = cell.getShip();
        boolean isSunk = ship != null && ship.isSunk();
        if (isSunk) {
            log.recordSink(currentPlayerMove, row, col);
            System.out.println("You sank a ship!");
        }
    }

    private static void processUserInput(Scanner input, AuditSystem log, Grid grid, int currentPlayerMove) {
        String in;
        int num;
        while (true) {
            System.out.println("Please enter a number:");
            in = input.nextLine();
            num = parseNumber(in);
            if (num == -1) {
                continue;
            }
            switch (num) {
                case 0 -> {
                    System.out.println("Quitting...");
                    log.recordGameEnd();
                    System.exit(0);
                }
                case 1 -> {
                    processShotInput(input, log, grid, currentPlayerMove);
                }
                case 2 -> {
                    printGrid(grid);
                    continue;
                }
                case 3 -> {
                    printMenu(currentPlayerMove);
                    continue;
                }
                case 4 -> {
                    log.printLog();
                    continue;
                }
            }
            break;
        }
    }

    private static void runGame(Scanner input, AuditSystem log) {
        Grid player1Grid = new Grid(10, 10);
        Grid player2Grid = new Grid(10, 10);
        Grid currentGrid;
        int currentPlayerMove = 1;
        boolean quit = false;
        while (true) {
            currentGrid = currentPlayerMove == 1 ? player1Grid : player2Grid;
            printGrid(currentGrid);
            printMenu(currentPlayerMove);
            processUserInput(input, log, currentGrid, currentPlayerMove);
            if (quit) {
                break;
            }
            currentPlayerMove = currentPlayerMove == 1 ? 2 : 1;
        }
        log.recordGameEnd();
    }

    /**
     * Starts the game application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        AuditSystem log = new AuditSystem();
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Press any key to start the game: ");
            input.nextLine();
            log.recordGameStart();
            runGame(input, log);
        }

    }
}
