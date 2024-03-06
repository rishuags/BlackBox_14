import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


class ConfigurationTest {


    @Test
    public void testInitGateMapCoordinate() {
        Configuration.initGateMap();
        Map<Integer, Gate> gateMap = Configuration.getGateMap();

        //Testing Size
        assertEquals(54, gateMap.size());

        //Testing ID-Initial Coordinate
        assertEquals("04", gateMap.get(1).getCoordinate().getKey());
        assertEquals("04", gateMap.get(2).getCoordinate().getKey());
        assertEquals("-23", gateMap.get(3).getCoordinate().getKey());
        assertEquals("-23", gateMap.get(4).getCoordinate().getKey());
        assertEquals("-42", gateMap.get(5).getCoordinate().getKey());
        assertEquals("-42", gateMap.get(6).getCoordinate().getKey());
        assertEquals("-61", gateMap.get(7).getCoordinate().getKey());
        assertEquals("-61", gateMap.get(8).getCoordinate().getKey());
        assertEquals("-80", gateMap.get(9).getCoordinate().getKey());

        assertEquals("-80", gateMap.get(10).getCoordinate().getKey());
        assertEquals("-80", gateMap.get(11).getCoordinate().getKey());
        assertEquals("-8-1", gateMap.get(12).getCoordinate().getKey());
        assertEquals("-8-1", gateMap.get(13).getCoordinate().getKey());
        assertEquals("-8-2", gateMap.get(14).getCoordinate().getKey());
        assertEquals("-8-2", gateMap.get(15).getCoordinate().getKey());
        assertEquals("-8-3", gateMap.get(16).getCoordinate().getKey());
        assertEquals("-8-3", gateMap.get(17).getCoordinate().getKey());
        assertEquals("-8-4", gateMap.get(18).getCoordinate().getKey());

        assertEquals("-8-4", gateMap.get(19).getCoordinate().getKey());
        assertEquals("-8-4", gateMap.get(20).getCoordinate().getKey());
        assertEquals("-6-4", gateMap.get(21).getCoordinate().getKey());
        assertEquals("-6-4", gateMap.get(22).getCoordinate().getKey());
        assertEquals("-4-4", gateMap.get(23).getCoordinate().getKey());
        assertEquals("-4-4", gateMap.get(24).getCoordinate().getKey());
        assertEquals("-2-4", gateMap.get(25).getCoordinate().getKey());
        assertEquals("-2-4", gateMap.get(26).getCoordinate().getKey());
        assertEquals("0-4", gateMap.get(27).getCoordinate().getKey());

        assertEquals("0-4", gateMap.get(28).getCoordinate().getKey());
        assertEquals("0-4", gateMap.get(29).getCoordinate().getKey());
        assertEquals("2-3", gateMap.get(30).getCoordinate().getKey());
        assertEquals("2-3", gateMap.get(31).getCoordinate().getKey());
        assertEquals("4-2", gateMap.get(32).getCoordinate().getKey());
        assertEquals("4-2", gateMap.get(33).getCoordinate().getKey());
        assertEquals("6-1", gateMap.get(34).getCoordinate().getKey());
        assertEquals("6-1", gateMap.get(35).getCoordinate().getKey());
        assertEquals("80", gateMap.get(36).getCoordinate().getKey());

        assertEquals("80", gateMap.get(37).getCoordinate().getKey());
        assertEquals("80", gateMap.get(38).getCoordinate().getKey());
        assertEquals("81", gateMap.get(39).getCoordinate().getKey());
        assertEquals("81", gateMap.get(40).getCoordinate().getKey());
        assertEquals("82", gateMap.get(41).getCoordinate().getKey());
        assertEquals("82", gateMap.get(42).getCoordinate().getKey());
        assertEquals("83", gateMap.get(43).getCoordinate().getKey());
        assertEquals("83", gateMap.get(44).getCoordinate().getKey());
        assertEquals("84", gateMap.get(45).getCoordinate().getKey());


        assertEquals("84", gateMap.get(46).getCoordinate().getKey());
        assertEquals("84", gateMap.get(47).getCoordinate().getKey());
        assertEquals("64", gateMap.get(48).getCoordinate().getKey());
        assertEquals("64", gateMap.get(49).getCoordinate().getKey());
        assertEquals("44", gateMap.get(50).getCoordinate().getKey());
        assertEquals("44", gateMap.get(51).getCoordinate().getKey());
        assertEquals("24", gateMap.get(52).getCoordinate().getKey());
        assertEquals("24", gateMap.get(53).getCoordinate().getKey());
        assertEquals("04", gateMap.get(54).getCoordinate().getKey());


        assertNotNull(gateMap.get(1)); // Check if the gate with key 1 exists

    }

    @Test
    public void testInitGateMapDirection() {
        Configuration.initGateMap();
        Map<Integer, Gate> gateMap = Configuration.getGateMap();

        //Testing Size
        assertEquals(54, gateMap.size());

        //Testing ID - Initial Direction
        assertEquals(Direction.SOUTH_EAST, gateMap.get(1).getDirection());
        assertEquals(Direction.EAST, gateMap.get(2).getDirection());
        assertEquals(Direction.SOUTH_EAST, gateMap.get(3).getDirection());
        assertEquals(Direction.EAST, gateMap.get(4).getDirection());
        assertEquals(Direction.SOUTH_EAST, gateMap.get(5).getDirection());
        assertEquals(Direction.EAST, gateMap.get(6).getDirection());
        assertEquals(Direction.SOUTH_EAST, gateMap.get(7).getDirection());
        assertEquals(Direction.EAST, gateMap.get(8).getDirection());
        assertEquals(Direction.SOUTH_EAST, gateMap.get(9).getDirection());

        assertEquals(Direction.EAST, gateMap.get(10).getDirection());
        assertEquals(Direction.NORTH_EAST, gateMap.get(11).getDirection());
        assertEquals(Direction.EAST, gateMap.get(12).getDirection());
        assertEquals(Direction.NORTH_EAST, gateMap.get(13).getDirection());
        assertEquals(Direction.EAST, gateMap.get(14).getDirection());
        assertEquals(Direction.NORTH_EAST, gateMap.get(15).getDirection());
        assertEquals(Direction.EAST, gateMap.get(16).getDirection());
        assertEquals(Direction.NORTH_EAST, gateMap.get(17).getDirection());
        assertEquals(Direction.EAST, gateMap.get(18).getDirection());

        assertEquals(Direction.NORTH_EAST, gateMap.get(19).getDirection());
        assertEquals(Direction.NORTH_WEST, gateMap.get(20).getDirection());
        assertEquals(Direction.NORTH_EAST, gateMap.get(21).getDirection());
        assertEquals(Direction.NORTH_WEST, gateMap.get(22).getDirection());
        assertEquals(Direction.NORTH_EAST, gateMap.get(23).getDirection());
        assertEquals(Direction.NORTH_WEST, gateMap.get(24).getDirection());
        assertEquals(Direction.NORTH_EAST, gateMap.get(25).getDirection());
        assertEquals(Direction.NORTH_WEST, gateMap.get(26).getDirection());
        assertEquals(Direction.NORTH_EAST, gateMap.get(27).getDirection());

        assertEquals(Direction.NORTH_WEST, gateMap.get(28).getDirection());
        assertEquals(Direction.WEST, gateMap.get(29).getDirection());
        assertEquals(Direction.NORTH_WEST, gateMap.get(30).getDirection());
        assertEquals(Direction.WEST, gateMap.get(31).getDirection());
        assertEquals(Direction.NORTH_WEST, gateMap.get(32).getDirection());
        assertEquals(Direction.WEST, gateMap.get(33).getDirection());
        assertEquals(Direction.NORTH_WEST, gateMap.get(34).getDirection());
        assertEquals(Direction.WEST, gateMap.get(35).getDirection());
        assertEquals(Direction.NORTH_WEST, gateMap.get(36).getDirection());

        assertEquals(Direction.WEST, gateMap.get(37).getDirection());
        assertEquals(Direction.SOUTH_WEST, gateMap.get(38).getDirection());
        assertEquals(Direction.WEST, gateMap.get(39).getDirection());
        assertEquals(Direction.SOUTH_WEST, gateMap.get(40).getDirection());
        assertEquals(Direction.WEST, gateMap.get(41).getDirection());
        assertEquals(Direction.SOUTH_WEST, gateMap.get(42).getDirection());
        assertEquals(Direction.WEST, gateMap.get(43).getDirection());
        assertEquals(Direction.SOUTH_WEST, gateMap.get(44).getDirection());
        assertEquals(Direction.WEST, gateMap.get(45).getDirection());

        assertEquals(Direction.SOUTH_WEST, gateMap.get(46).getDirection());
        assertEquals(Direction.SOUTH_EAST, gateMap.get(47).getDirection());
        assertEquals(Direction.SOUTH_WEST, gateMap.get(48).getDirection());
        assertEquals(Direction.SOUTH_EAST, gateMap.get(49).getDirection());
        assertEquals(Direction.SOUTH_WEST, gateMap.get(50).getDirection());
        assertEquals(Direction.SOUTH_EAST, gateMap.get(51).getDirection());
        assertEquals(Direction.SOUTH_WEST, gateMap.get(52).getDirection());
        assertEquals(Direction.SOUTH_EAST, gateMap.get(53).getDirection());
        assertEquals(Direction.SOUTH_WEST, gateMap.get(54).getDirection());

        assertNotNull(gateMap.get(1)); // Check if the gate with key 1 exists

    }

    @Test
    public void testGenerateBoard(){
        Configuration newConfig = new Configuration();

        newConfig.generateBoard();

        Map<String, Tile> coordTileMap = newConfig.getCoordMap();

        assertNotNull(coordTileMap);
        assertEquals(61, coordTileMap.size());

    }


}