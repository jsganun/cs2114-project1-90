package gameboard;

public class Shot {
    private final Cell targetCell;

    public Shot(Cell targetCell) {
        this.targetCell = targetCell;
    }

    public boolean shoot() {
        return targetCell.containsShip();
    }
}
