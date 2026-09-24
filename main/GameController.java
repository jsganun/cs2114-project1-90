package main;

import gameboard.*;
import java.util.Random;
import java.util.Scanner;
import ships.Ship;

/**
 * Entry point for the game application.
 */
public class GameController {
    private static final String LARGE_WHITESPACE = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    private static final Random RANDOM = new Random();
    private static int player1ShipsSunk = 0;
    private static int player2ShipsSunk = 0;

    /**
     * Prints the game grid to standard output.
     *
     * @param grid the grid to print
     */
    private static void printGrid(Grid grid) {
        System.out.print(grid.toString());
    }

    /**
     * Prints the game menu and current player information to standard output.
     *
     * @param currentPlayerMove the player number (1 or 2) whose turn it is
     */
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

    /**
     * Parses a string input as an integer.
     * Rejects input containing floating-point numbers and returns -1 for invalid
     * input.
     *
     * @param in the string to parse
     * @return the parsed integer, or -1 if the input is invalid or a floating-point
     *         number
     */
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

    /**
     * Processes a player's shot input, validating coordinates and recording the
     * result.
     * Prompts for row and column input, determines if the shot hit, and logs the
     * result.
     * If a ship is sunk, increments the player's sink counter and logs the sunk
     * ship.
     *
     * @param input             the Scanner for reading player input
     * @param log               the AuditSystem for recording the shot
     * @param grid              the game grid to shoot at
     * @param currentPlayerMove the player number (1 or 2) making the shot
     */
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
            if (currentPlayerMove == 1) {
                player1ShipsSunk++;
            } else {
                player2ShipsSunk++;
            }
        }
    }

    /**
     * Processes user menu input during gameplay.
     * Supports options to quit (0), shoot (1), print grid (2), print menu (3), and
     * print log (4).
     *
     * @param input             the Scanner for reading player input
     * @param log               the AuditSystem for recording game events
     * @param grid              the current game grid
     * @param currentPlayerMove the player number (1 or 2) whose turn it is
     */
    private static void processUserInput(Scanner input, AuditSystem log, Grid grid, int currentPlayerMove) {
        String in;
        int num;
        while (true) {
            System.out.println("Please enter a number 1-4:");
            in = input.nextLine();
            num = parseNumber(in);
            if (num < 0 || num > 4) {
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

    /**
     * Determines the game winner based on the number of ships sunk.
     * A player wins by sinking all 5 of their opponent's ships.
     *
     * @return 1 if player 1 won, 2 if player 2 won, or 0 if there is no winner yet
     */
    private static int getWinner() {
        if (player1ShipsSunk >= 5) {
            return 1;
        }
        if (player2ShipsSunk >= 5) {
            return 2;
        }
        return 0;
    }

    /**
     * Generates a random compass direction for ship placement.
     *
     * @return a random direction: "NORTH", "EAST", "SOUTH", or "WEST"
     */
    private static String getRandomDirection() {
        int randNum = RANDOM.nextInt(4);
        return switch (randNum) {
            case 0 -> "NORTH";
            case 1 -> "EAST";
            case 2 -> "SOUTH";
            default -> "WEST";
        };
    }

    /**
     * Randomly places all 5 ships on the given grid.
     * Ships are placed at random locations and directions, ensuring no overlaps.
     *
     * @param grid the grid to place ships on
     */
    private static void placeShipsRandomly(Grid grid) {
        int shipsPlaced = 0;
        while (true) {
            int r = RANDOM.nextInt(grid.getRows());
            int c = RANDOM.nextInt(grid.getCols());
            if (grid.getCell(r, c).containsShip()) {
                continue;
            }
            Ship[] ships = grid.getShips();
            boolean shipWasPlaced = grid.placeShip(ships[shipsPlaced], r, c, getRandomDirection());
            if (!shipWasPlaced) {
                continue;
            }
            shipsPlaced++;
            if (shipsPlaced >= 5) {
                return;
            }
        }
    }

    /**
     * Runs the main game loop, managing turns, player input, and win conditions.
     * Creates two 10x10 grids, places ships randomly, and alternates turns between
     * players
     * until one player sinks all 5 of the opponent's ships.
     *
     * @param input the Scanner for reading player input
     * @param log   the AuditSystem for recording game events
     */
    private static void runGame(Scanner input, AuditSystem log) {
        Grid player1Grid = new Grid(10, 10);
        Grid player2Grid = new Grid(10, 10);
        Grid currentGrid;
        int currentPlayerMove = 1;
        placeShipsRandomly(player1Grid);
        placeShipsRandomly(player2Grid);
        while (true) {
            currentGrid = currentPlayerMove == 1 ? player1Grid : player2Grid;
            System.out.println(LARGE_WHITESPACE);
            printGrid(currentGrid);
            printMenu(currentPlayerMove);
            processUserInput(input, log, currentGrid, currentPlayerMove);
            int winner = getWinner();
            if (winner > 0) {
                System.out.println("Player " + winner + " won the game!");
                log.recordGameWinner(winner);
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
