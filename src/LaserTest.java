import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LaserTest {

    //Traversing default board (Board contains no atoms) (test should be run on its own)
    @Test
    void laserDefaultTraversal() {
        Configuration.initGateMap();
        Configuration.generateBoard();

        testDefaultPath();
        testDefaultInputOutput();
    }

    /**Testing Default Laser Paths**/
    void testDefaultPath(){
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
    /**Testing Default Output Gate retrieval from Input Gate**/
    void testDefaultInputOutput(){
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
        assertEquals(18, new Laser(29).laserTraversal());
        assertEquals(53, new Laser(30).laserTraversal());
        assertEquals(16, new Laser(31).laserTraversal());
        assertEquals(51, new Laser(32).laserTraversal());
        assertEquals(14, new Laser(33).laserTraversal());
        assertEquals(49, new Laser(34).laserTraversal());
        assertEquals(12, new Laser(35).laserTraversal());
        assertEquals(47, new Laser(36).laserTraversal());
        assertEquals(10, new Laser(37).laserTraversal());
        assertEquals(27, new Laser(38).laserTraversal());
        assertEquals(8, new Laser(39).laserTraversal());
        assertEquals(25, new Laser(40).laserTraversal());
        assertEquals(6, new Laser(41).laserTraversal());
        assertEquals(23, new Laser(42).laserTraversal());
        assertEquals(4, new Laser(43).laserTraversal());
        assertEquals(21, new Laser(44).laserTraversal());
        assertEquals(2, new Laser(45).laserTraversal());
        assertEquals(19, new Laser(46).laserTraversal());
        assertEquals(36, new Laser(47).laserTraversal());
        assertEquals(17, new Laser(48).laserTraversal());
        assertEquals(34, new Laser(49).laserTraversal());
        assertEquals(15, new Laser(50).laserTraversal());
        assertEquals(32, new Laser(51).laserTraversal());
        assertEquals(13, new Laser(52).laserTraversal());
        assertEquals(30, new Laser(53).laserTraversal());
        assertEquals(11, new Laser(54).laserTraversal());

    }

    @Test
    void laserAbsorption(){
        Configuration.generateBoard();
        Configuration.initGateMap();

        Map<String, Tile> coordTileMap = Configuration.getCoordTileMap();

        //Placing atom on Tile
        Coordinate atomCoordinate = new Coordinate(-4, 0);
        String atomCoordinateKey = atomCoordinate.getKey();
        Tile atomTile = coordTileMap.get(atomCoordinateKey);
        atomTile.setAtom();

        Laser laser = new Laser(10);
        Integer outputGate = laser.laserTraversal();
//        System.out.println("Output Gate: " + outputGate);
//        System.out.println("Laser Path:" + laser.path);
        assertEquals(0, outputGate);
    }

    @Test
    void laser60Deflection(){
        Configuration.generateBoard();
        Configuration.initGateMap();

        Map<String, Tile> coordTileMap = Configuration.getCoordTileMap();

        Coordinate atomCoordinate = new Coordinate(2,1);
        String atomCoordinateKey = atomCoordinate.getKey();
        Tile atomTile = coordTileMap.get(atomCoordinateKey);
        atomTile.setAtom();

        Laser laser = new Laser(44);
        Integer outputGate = laser.laserTraversal();
//        System.out.println("Output Gate: " + outputGate);
//        System.out.println("Laser Path:" + laser.path);

        assertEquals(32, outputGate);

    }

    @Test
    void laser120Deflection(){
        Configuration.generateBoard();
        Configuration.initGateMap();

        Map<String, Tile> coordTileMap = Configuration.getCoordTileMap();

        //Placing atom on first tile
        Coordinate atomCoordinate = new Coordinate(-4,0);
        String atomCoordinateKey = atomCoordinate.getKey();
        Tile atomTile = coordTileMap.get(atomCoordinateKey);
        atomTile.setAtom();

        //Placing atom on second tile
        atomCoordinate = new Coordinate(-2, 0);
        atomCoordinateKey = atomCoordinate.getKey();
        atomTile = coordTileMap.get(atomCoordinateKey);
        atomTile.setAtom();


        Laser laser = new Laser(17);
        Integer outputGate = laser.laserTraversal();
//        System.out.println("Output Gate: " + outputGate);
//        System.out.println("Laser Path:" + laser.path);

        assertEquals(24, outputGate);

    }

    @Test
    void laserReflectionCase1(){
        Configuration.generateBoard();
        Configuration.initGateMap();

        Map<String, Tile> coordTileMap = Configuration.getCoordTileMap();

        //Placing atom on first tile
        Coordinate atomCoordinate = new Coordinate(-2,2);
        String atomCoordinateKey = atomCoordinate.getKey();
        Tile atomTile = coordTileMap.get(atomCoordinateKey);
        atomTile.setAtom();

        //Placing atom on second tile
        atomCoordinate = new Coordinate(-4, 0);
        atomCoordinateKey = atomCoordinate.getKey();
        atomTile = coordTileMap.get(atomCoordinateKey);
        atomTile.setAtom();


        Laser laser = new Laser(8);
        Integer outputGate = laser.laserTraversal();
//        System.out.println("Output Gate: " + outputGate);
//        System.out.println("Laser Path:" + laser.path);

        assertEquals(8, outputGate);

    }

    @Test
    void laserReflectionCase2(){
        Configuration.generateBoard();
        Configuration.initGateMap();

        Map<String, Tile> coordTileMap = Configuration.getCoordTileMap();

        //Placing atom on first tile
        Coordinate atomCoordinate = new Coordinate(-2,0);
        String atomCoordinateKey = atomCoordinate.getKey();
        Tile atomTile = coordTileMap.get(atomCoordinateKey);
        atomTile.setAtom();

        //Placing atom on second tile
        atomCoordinate = new Coordinate(0, 1);
        atomCoordinateKey = atomCoordinate.getKey();
        atomTile = coordTileMap.get(atomCoordinateKey);
        atomTile.setAtom();

        //Placing atom on third tile
        atomCoordinate = new Coordinate(2, 1);
        atomCoordinateKey = atomCoordinate.getKey();
        atomTile = coordTileMap.get(atomCoordinateKey);
        atomTile.setAtom();


        Laser laser = new Laser(28);
        Integer outputGate = laser.laserTraversal();
//        System.out.println("Output Gate: " + outputGate);
//        System.out.println("Laser Path:" + laser.path);

        assertEquals(28, outputGate);

    }


    @Test
    void laserComplexPath(){
        Configuration.generateBoard();
        Configuration.initGateMap();

        Map<String, Tile> coordTileMap = Configuration.getCoordTileMap();

        //Placing 6 atoms - according to complex path document
        setAtoms(0,2, coordTileMap);
        setAtoms(4,2, coordTileMap);
        setAtoms(2,0, coordTileMap);
        setAtoms(4,0, coordTileMap);
        setAtoms(-6,-3, coordTileMap);
        setAtoms(-2,-4, coordTileMap);

        assertEquals(48, fireLaser(53));
        assertEquals(14, fireLaser(14));
        assertEquals(0, fireLaser(30));
        //assertEquals("[-8-2, -6-2, -4-1, -20, 01, 21, 01, -20, -4-1, -6-2, -8-2]",fireLaserGetPath(14));

    }
    @Test
    void laserAtomAtBoxEdge1(){
        Configuration.generateBoard();
        Configuration.initGateMap();

        Map<String, Tile> coordTileMap = Configuration.getCoordTileMap();

        setAtoms(8,1, coordTileMap);

        assertEquals(41, fireLaser(41));

    }


    @Test
    void laserAtomAtBoxEdge2(){
        Configuration.generateBoard();
        Configuration.initGateMap();

        Map<String, Tile> coordTileMap = Configuration.getCoordTileMap();

        setAtoms(8,2, coordTileMap);
        setAtoms(4,4, coordTileMap);

        assertEquals(48, fireLaser(21));

//        System.out.println(fireLaser(48));

    }



    @Test
    void specialCases(){
        Configuration.generateBoard();
        Configuration.initGateMap();

        Map<String, Tile> coordTileMap = Configuration.getCoordTileMap();

        setAtoms(6,4, coordTileMap);
        setAtoms(-2,3, coordTileMap);
        setAtoms(0,2, coordTileMap);
        setAtoms(0,-2, coordTileMap);
        setAtoms(-4,-3, coordTileMap);
        setAtoms(0,-4, coordTileMap);

        assertEquals(25, fireLaser(25));

//        System.out.println(fireLaser(48));

    }




    //places atom on specific tile (For laser path Testing)
    void setAtoms(int x, int y, Map<String, Tile> coordTileMap){
        Coordinate atomCoordinate = new Coordinate(x,y);
        String atomCoordinateKey = atomCoordinate.getKey();
        Tile atomTile = coordTileMap.get(atomCoordinateKey);
        atomTile.setAtom();
    }

    //fire laser, get ouput gate
    Integer fireLaser(Integer inputGate){
        Laser laser = new Laser(inputGate);
        Integer outputGate = laser.laserTraversal();
//        System.out.println("Output Gate: " + outputGate);
//        System.out.println("Laser Path:" + laser.path);
        return outputGate;
    }

    //fire laser, get path
    String fireLaserGetPath(Integer inputGate){
        Laser laser = new Laser(inputGate);
        Integer outputGate = laser.laserTraversal();
//        System.out.println("Output Gate: " + outputGate);
//        System.out.println("Laser Path:" + laser.path);
        return laser.path.toString();
    }

}