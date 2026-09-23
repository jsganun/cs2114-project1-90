package gameboard;

/**
 * Represents one shot directed at a coordinate on the game board.
 */
public class Shot {
    /** The row that the shot is targeted at. */
    private int row;
    /** The column that the shot is targeted at. */
    private int col;
    /** The result of the shot. */
    private ShotResult result;

    /**
     * Creates a shot targeting the specified coordinate.
     *
     * @param row the row to target
     * @param col the column to target
     */
    public Shot(int row, int col) {
        this.row = row;
        this.col = col;
        this.result = ShotResult.None;
    }

    /**
     * Returns the row that the shot is targeted at.
     *
     * @return the target row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column that the shot is targeted at.
     *
     * @return the target column
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the result of the shot.
     *
     * @return the shot's result
     */
    public ShotResult getResult() {
        return result;
    }

    /**
     * Sets the result of the shot.
     *
     * @param newResult the result to set
     * @throws NullPointerException if {@code newResult} is null
     */
    public void setResult(ShotResult newResult) {
        if (newResult == null) {
            throw new NullPointerException("newResult cannot be null");
        }
        this.result = newResult;
    }

    /**
     * Returns the shot's coordinate and its result.
     *
     * @return a string representation of the shot
     */
    @Override
    public String toString() {
        return "Shot(" + row + ", " + col + ") -> " + result;
    }
}
