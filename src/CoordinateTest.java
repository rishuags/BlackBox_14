import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    void getKey() {
        Coordinate coord = new Coordinate(0, 3);
        assertEquals(coord.getKey(), "03");

        Tile tile = new Tile(new Coordinate(4, 3));
        assertEquals(tile.getCoordinate().getKey(), "43");

    }
}