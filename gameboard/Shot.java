package gameboard;

public class Shot {
    public Shot() {

    }

    public boolean shoot(Cell cell) {
        return cell.containsShip();
    }
}
