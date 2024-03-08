import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathCalculatorTest {

    @Test
    void calculate() {
        //basic test
        Coordinate coord = new Coordinate(0,0);
        String strCoord = PathCalculator.calculate(Direction.EAST, coord).toString();
        assertEquals(strCoord, new Coordinate(2, 0).toString());

        //testing algorithm for predicting next coord. based on direction is valid

        //sample list of coords.
        Coordinate[] coordList = {new Coordinate(-2, 0), new Coordinate(-4, 0), new Coordinate(-6, 0), new Coordinate(-4, 1), new Coordinate(-4, 2)};
        //directions which should lead to next coord. in coordList
        Direction[] dirList = {Direction.WEST, Direction.WEST, Direction.NORTH_EAST, Direction.NORTH_WEST};

        int i = 0;
        for (Direction d: dirList) {
            Coordinate next = PathCalculator.calculate(d, coordList[i]);
            assertEquals(next.toString(), coordList[i + 1].toString());
            i++;
        }
    }
}