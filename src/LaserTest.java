import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LaserTest {

    //Traversing default board (Board contains no atoms) (test should be run on its own)
    @Test
    void laserDefaultTraversal() {
        Configuration.initGateMap();
        Configuration.generateBoard();

        Laser laser = new Laser();
        laser.laserTraversal(1);
        assertEquals("[04, 03, 02, 01, 00, 0-1, 0-2, 0-3, 0-4]" , laser.path.toString());


        laser = new Laser();
        laser.laserTraversal(10);
        assertEquals("[-80, -60, -40, -20, 00, 20, 40, 60, 80]" , laser.path.toString());


        laser = new Laser();
        laser.laserTraversal(19);
        assertEquals("[-8-4, -6-3, -4-2, -2-1, 00, 21, 42, 63, 84]" , laser.path.toString());


        laser = new Laser();
        laser.laserTraversal(28);
        assertEquals("[0-4, 0-3, 0-2, 0-1, 00, 01, 02, 03, 04]" , laser.path.toString());


    }

    //Traversing a board with random atom allocation (test may fail if, atom is in path, by design)
    @Test
    void laserTraversal() {
        Configuration config = new Configuration();
        Configuration.initGateMap();
        Configuration.generateBoard();

        Board board = new Board();
        board.coordinateTileMap = config.getCoordMap();
        board.GenerateAtoms();

        Laser laser = new Laser();
        laser.laserTraversal(1);
        assertEquals("[04, 03, 02, 01, 00, 0-1, 0-2, 0-3, 0-4]" , laser.path.toString());

    }
}