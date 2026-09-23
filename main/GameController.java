package main;

import gameboard.*;
import ships.*;
import java.util.Scanner;

/**
 * Entry point for the game application.
 */
public class GameController {
    private static Grid[] playerGrids;
    private static AuditSystem auditSystem;
    private static int currentPlayer;
    private static boolean gameOver;

    /**
     * Starts the game.
     */
    public void startGame() {
        currentPlayer = 1;
        gameOver = false;

        if (auditSystem == null) {
            auditSystem = new AuditSystem();
        }
        else {
            auditSystem.clearLog();
        }
        auditSystem.recordGameStart();

        playerGrids = new Grid[2];
        setupBoards();
    }

    /**
     * Ends the game.
     */
    public void endGame(boolean byForfeit) {
        gameOver = true;
        auditSystem.recordGameEnd(currentPlayer, byForfeit);
    }

    /**
     * Sets up the game boards for both players.
     */
    public void setupBoards() {
        playerGrids[0] = new Grid(10, 10);
        playerGrids[1] = new Grid(10, 10);

        Ship[] player1Ships = new Ship[]{
                new Ship("Destroyer", 2),
                new Ship("Submarine", 3),
                new Ship("Cruiser", 3),
                new Ship("Battleship", 4),
                new Ship("Carrier", 5)
        };

        Ship[] player2Ships = new Ship[]{
                new Ship("Destroyer", 2),
                new Ship("Submarine", 3),
                new Ship("Cruiser", 3),
                new Ship("Battleship", 4),
                new Ship("Carrier", 5)
        };

        // Place ships for player 1
        // TODO: Implement user input for ship placement instead of hardcoding positions
        // This is where you would listen for the player's input to place ships on the grid.
        // For demonstration purposes, we will place ships at predefined positions.
        playerGrids[0].placeShip(player1Ships[0], 0, 0, "EAST");
        auditSystem.recordShipPlacement(1, player1Ships[0]);
        playerGrids[0].placeShip(player1Ships[1], 2, 0, "EAST");
        auditSystem.recordShipPlacement(1, player1Ships[1]);
        playerGrids[0].placeShip(player1Ships[2], 4, 0, "EAST");
        auditSystem.recordShipPlacement(1, player1Ships[2]);
        playerGrids[0].placeShip(player1Ships[3], 6, 0, "EAST");
        auditSystem.recordShipPlacement(1, player1Ships[3]);
        playerGrids[0].placeShip(player1Ships[4], 8, 0, "EAST");
        auditSystem.recordShipPlacement(1, player1Ships[4]);

        // Mark the next turn for player 2 to place their ships
        nextTurn();

        // Place ships for player 2
        playerGrids[1].placeShip(player2Ships[0], 0, 9, "WEST");
        auditSystem.recordShipPlacement(2, player2Ships[0]);
        playerGrids[1].placeShip(player2Ships[1], 2, 9, "WEST");
        auditSystem.recordShipPlacement(2, player2Ships[1]);
        playerGrids[1].placeShip(player2Ships[2], 4, 9, "WEST");
        auditSystem.recordShipPlacement(2, player2Ships[2]);
        playerGrids[1].placeShip(player2Ships[3], 6, 9, "WEST");
        auditSystem.recordShipPlacement(2, player2Ships[3]);
        playerGrids[1].placeShip(player2Ships[4], 8, 9, "WEST");
        auditSystem.recordShipPlacement(2, player2Ships[4]);

        // Mark the next turn for player 1 to start the game
        nextTurn();
    }

    /**
     * Advances the game to the next turn.
     */
    public void nextTurn() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        auditSystem.recordTurnAdvance(currentPlayer);
    }

    /**
     * Checks if the game has been won by either player.
     *
     * @return true if a player has won, false otherwise
     */
    public boolean checkWinCondition() {
        Grid currPlayerGrid = playerGrids[currentPlayer - 1];
        return currPlayerGrid.allShipsSunk();
    }

    /**
     * Processes a command entered by the user.
     *
     * @param command the command to process
     * @return true if the command was processed successfully, false otherwise
     */
    public boolean processCommand(String command) {
        // TODO: Implement command processing logic
        return false;
    }

    /**
     * Places a ship on the specified player's grid.
     * 
     * @param ship the ship to place
     * @param row the row to place the ship
     * @param col the column to place the ship
     * @param direction the direction to place the ship (North, South, East, West)
     * @return true if the ship was placed successfully, false otherwise
     */
    private boolean placeShipCommand(Ship ship, int row, int col, String direction) {
        direction = direction.toUpperCase();

        if (ship == null) {
            return false;
        }
        if (!direction.equals("NORTH") && !direction.equals("SOUTH") && !direction.equals("EAST") && !direction.equals("WEST")) {
            return false;
        }

        Grid currPlayerGrid = playerGrids[currentPlayer - 1];
        return currPlayerGrid.placeShip(ship, row, col, direction);
    }

    /**
     * Processes a shot command for the specified player at the given coordinates.
     * 
     * @param row the row to target
     * @param col the column to target
     * @return true if the shot was processed successfully, false otherwise
     */
    private boolean shotCommand(int row, int col) {

        Grid currPlayerGrid = playerGrids[currentPlayer - 1];
        return currPlayerGrid.receiveShot(new Shot(row, col));
    }

    /**
     * Processes a forfeit command for the specified player.
     * 
     * @param player the player number (1 or 2)
     * @return true if the forfeit was processed successfully, false otherwise
     */
    private boolean forfeitCommand() {
        if (gameOver) {
            return false;
        }

        endGame(true);
        return true;
    }

    /**
     * Processes a start game command.
     * 
     * @return true if the game was started successfully, false otherwise
     */
    private boolean startGameCommand() {
        if (!gameOver) {
            return false;
        }

        startGame();
        return true;
    }

    private static void printGrid(Grid grid) {
        System.out.println(grid.toString());
    }

    private static void printMenu(int currentPlayerMove) {
        String menu = """
                1. Shot
                2. Print grid
                3. Print log
                """;
        System.out.println("Current move: Player"
                + currentPlayerMove + "\n" + menu);
    }

    private static void processUserInput(Scanner input, AuditSystem log) {

    }

    private static void runGame(Scanner input, AuditSystem log) {
        Grid grid = new Grid(10, 10);
        int currentPlayerMove = 1;
        while (true) {
            printGrid(grid);
            printMenu(currentPlayerMove);
            currentPlayerMove = currentPlayerMove == 1 ? 2 : 1;
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
