import java.util.Map;

public class Configuration {

    public static final Integer initX = 0;
    public static final Integer initY = 4;
    public static final Direction initDirection = Direction.SOUTH_WEST;





    public void initGateDirectionMap(Board b) {
        fillGateDirectionMap(b, 1, 9, Direction.SOUTH_EAST, Direction.EAST);
        fillGateDirectionMap(b, 10, 18, Direction.NORTH_EAST, Direction.EAST);
        fillGateDirectionMap(b, 19, 27, Direction.NORTH_EAST, Direction.NORTH_WEST);
        fillGateDirectionMap(b, 28, 36, Direction.WEST, Direction.NORTH_WEST);
        fillGateDirectionMap(b, 37, 45, Direction.WEST, Direction.SOUTH_WEST);
        fillGateDirectionMap(b, 46, 54, Direction.SOUTH_EAST, Direction.SOUTH_WEST);
    }

    public void fillGateDirectionMap(Board b, int startGate, int endGate, Direction odd, Direction even) {
        for (int i = startGate; i <= endGate; i++) {
            if (i % 2 == 1) { //odd
                b.getGateDirectionMap().put(i, odd);
                //Gate gate = new Gate(i, odd);
            } else { //even
                b.getGateDirectionMap().put(i, even);
                //Gate gate = new Gate(i, odd);
            }
        }
    }

    public void initLeftAndRightGateDirection(Board b) {
        //Init Left Direction
        b.getLeftDirectionMap().put(Direction.SOUTH_EAST, Direction.EAST);
        b.getLeftDirectionMap().put(Direction.EAST, Direction.NORTH_EAST);
        b.getLeftDirectionMap().put(Direction.NORTH_EAST, Direction.NORTH_WEST);
        b.getLeftDirectionMap().put(Direction.NORTH_WEST, Direction.WEST);
        b.getLeftDirectionMap().put(Direction.WEST, Direction.SOUTH_WEST);
        b.getLeftDirectionMap().put(Direction.SOUTH_WEST, Direction.SOUTH_EAST);

        //Init Right Direction
        for(Map.Entry<Direction, Direction> ld : b.getLeftDirectionMap().entrySet()){
            b.getRightDirectionMap().put(ld.getValue(), ld.getKey());
        }
    }
}
