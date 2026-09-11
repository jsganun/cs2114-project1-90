package ships;

import gameboard.Cell;

public class Ship {
    private final Cell cell;

    public Ship(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }
}
