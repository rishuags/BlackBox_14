import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LaserTest {

    @Test
    void laserTraversal() {
        Configuration.initGateMap();
        Configuration.generateBoard();
        Laser.laserTraversal(9);
    }
}