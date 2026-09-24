package tests;

import org.junit.Test;

import gameboard.Grid;

public class TestGrid {

    @Test 
    public void testToString() {
        Grid grid = new Grid(10, 10);
        String expected = """
                  1 2 3 4 5 6 7 8 9 10
                A - - - - - - - - - -
                B - - - - - - - - - -
                C - - - - - - - - - -
                D - - - - - - - - - -
                E - - - - - - - - - -
                F - - - - - - - - - -
                G - - - - - - - - - -
                H - - - - - - - - - -
                I - - - - - - - - - -
                J - - - - - - - - - -
                                """;
        if (!grid.toString().equals(expected)) {
            System.out.println("toString() failed");
        }
    }

    @Test 
    public void testGetCell() {
        
    }

    @Test
    public void testPlaceShip() {
        
    }

    @Test
    public void testAllShipsSunk() {

    }

    @Test 
    public void testIsValidPlacement() {
        
    }

    @Test 
    public void testReceiveShot() {
        
    }

    @Test
    public void testTranslateCoords() {
        
    }

    // public static void main(String[] args) {
    //     testToString();
    // }
}
