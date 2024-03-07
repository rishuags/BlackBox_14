import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LaserTest {

    //Traversing default board (Board contains no atoms) (test should be run on its own)
    @Test
    void laserDefaultTraversal() {
        Configuration.initGateMap();
        Configuration.generateBoard();

        /**Testing Laser Paths**/
        testPath();

        /**Testing Output Gate retrieval from Input Gate**/
        testInputOutput();

    }


    void testPath(){
        Laser laser;

        laser = new Laser(1);
        laser.laserTraversal();
        assertEquals("[04, 03, 02, 01, 00, 0-1, 0-2, 0-3, 0-4]" , laser.path.toString());

        laser = new Laser(11);
        laser.laserTraversal(); //Case where laser is traversing all edge tiles
        assertEquals("[-80, -61, -42, -23, 04]" , laser.path.toString());

        laser = new Laser(10);
        laser.laserTraversal();
        assertEquals("[-80, -60, -40, -20, 00, 20, 40, 60, 80]" , laser.path.toString());

        laser = new Laser(19);
        laser.laserTraversal();
        assertEquals("[-8-4, -6-3, -4-2, -2-1, 00, 21, 42, 63, 84]" , laser.path.toString());

        laser = new Laser(28);
        laser.laserTraversal();
        assertEquals("[0-4, 0-3, 0-2, 0-1, 00, 01, 02, 03, 04]" , laser.path.toString());
    }
    void testInputOutput(){
        assertEquals(28, new Laser(1).laserTraversal());
        assertEquals(45, new Laser(2).laserTraversal());
        assertEquals(26, new Laser(3).laserTraversal());
        assertEquals(43, new Laser(4).laserTraversal());
        assertEquals(24, new Laser(5).laserTraversal());
        assertEquals(41, new Laser(6).laserTraversal());
        assertEquals(22, new Laser(7).laserTraversal());
        assertEquals(39, new Laser(8).laserTraversal());
        assertEquals(20, new Laser(9).laserTraversal());
        assertEquals(37, new Laser(10).laserTraversal());
        assertEquals(54, new Laser(11).laserTraversal());
        assertEquals(35, new Laser(12).laserTraversal());
        assertEquals(52, new Laser(13).laserTraversal());
        assertEquals(33, new Laser(14).laserTraversal());
        assertEquals(50, new Laser(15).laserTraversal());
        assertEquals(31, new Laser(16).laserTraversal());
        assertEquals(48, new Laser(17).laserTraversal());
        assertEquals(29, new Laser(18).laserTraversal());
        assertEquals(46, new Laser(19).laserTraversal());
        assertEquals(9, new Laser(20).laserTraversal());
        assertEquals(44, new Laser(21).laserTraversal());
        assertEquals(7, new Laser(22).laserTraversal());
        assertEquals(42, new Laser(23).laserTraversal());
        assertEquals(5, new Laser(24).laserTraversal());
        assertEquals(40, new Laser(25).laserTraversal());
        assertEquals(3, new Laser(26).laserTraversal());
        assertEquals(38, new Laser(27).laserTraversal());


        assertEquals(1, new Laser(28).laserTraversal());
        assertEquals(2, new Laser(45).laserTraversal());
        assertEquals(3, new Laser(26).laserTraversal());
        assertEquals(4, new Laser(43).laserTraversal());
        assertEquals(5, new Laser(24).laserTraversal());
        assertEquals(6, new Laser(41).laserTraversal());
        assertEquals(7, new Laser(22).laserTraversal());
        assertEquals(8, new Laser(39).laserTraversal());
        assertEquals(9, new Laser(20).laserTraversal());
        assertEquals(10, new Laser(37).laserTraversal());
        assertEquals(11, new Laser(54).laserTraversal());
        assertEquals(12, new Laser(35).laserTraversal());
        assertEquals(13, new Laser(52).laserTraversal());
        assertEquals(14, new Laser(33).laserTraversal());
        assertEquals(15, new Laser(50).laserTraversal());
        assertEquals(16, new Laser(31).laserTraversal());
        assertEquals(17, new Laser(48).laserTraversal());
        assertEquals(18, new Laser(29).laserTraversal());
        assertEquals(19, new Laser(46).laserTraversal());
        assertEquals(20, new Laser(9).laserTraversal());
        assertEquals(21, new Laser(44).laserTraversal());
        assertEquals(22, new Laser(7).laserTraversal());
        assertEquals(23, new Laser(42).laserTraversal());
        assertEquals(24, new Laser(5).laserTraversal());
        assertEquals(25, new Laser(40).laserTraversal());
        assertEquals(26, new Laser(3).laserTraversal());
        assertEquals(27, new Laser(38).laserTraversal());
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

        Laser laser = new Laser(1);
        laser.laserTraversal();
        assertEquals("[04, 03, 02, 01, 00, 0-1, 0-2, 0-3, 0-4]" , laser.path.toString());

    }
}