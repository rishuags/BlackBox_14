import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class ConfigurationTest {

    @org.junit.jupiter.api.Test
    void generateBoard() {
        Configuration config = new Configuration();
        config.generateBoard();

        Map<String, Tile> coordTile = config.getCoordMap();
        String[] coordList = {"04", "-23", "-42", "-61", "-80", "-8-1", "-8-2", "-8-3", "-8-4", "-6-4", "-4-4", "-2-4", "0-4", "2-3", "4-2", "6-1", "80", "81", "82", "83", "84", "64", "44", "24", "03", "-22", "-41", "-60", "-6-1", "-6-2", "-6-3", "-4-3", "-2-3", "0-3", "2-2", "4-1", "60", "61", "62", "63", "43", "23", "02", "-21", "-40", "-4-1", "-4-2", "-2-2", "0-2", "2-1", "40", "41", "42", "22", "01", "-20", "-2-1", "0-1", "20", "21", "00"};
        //size test, 61 is number of tiles on board
        assertEquals(coordTile.size(), 61);

        Set<String> keySet = coordTile.keySet();
        String[] keyArray = keySet.toArray(new String[0]);

        assertArrayEquals(coordList, keyArray);

    }
}