import java.util.LinkedHashMap;
import java.util.Map;

public class Configuration {
    private static final Board board = new Board();

    private static final Map<String, Tile> coordTileMap = board.coordinateTileMap;
    private static final Integer initX = 0;
    private static final Integer initY = 4;
    private static final Direction initDirection = Direction.SOUTH_WEST;

    /***/
    private Integer gateID;
    private static final Map<Integer, Gate> gateMap = new LinkedHashMap<>();


    public static void initGateMap() {
        //54 Total Inputs/Gates
        fillGateMap(1, 9, Direction.SOUTH_EAST, Direction.EAST);
        fillGateMap(10, 18, Direction.NORTH_EAST, Direction.EAST);
        fillGateMap(19, 27, Direction.NORTH_EAST, Direction.NORTH_WEST);
        fillGateMap(28, 36, Direction.WEST, Direction.NORTH_WEST);
        fillGateMap(36, 45, Direction.WEST, Direction.SOUTH_WEST);
        fillGateMap(45, 54, Direction.SOUTH_EAST, Direction.SOUTH_WEST);
    }

    public static void fillGateMap( int startGate, int endGate, Direction odd, Direction even) {
        for (int i = startGate; i <= endGate; i++) {
            if (i % 2 == 1) {//odd
                gateMap.put(i, new Gate(odd));
            } else {
                gateMap.put(i, new Gate(even));
            }
        }
    }

    public static Map<Integer, Gate> getGateMap() {
        return gateMap;
    }

    /***/


    public Map<String, Tile> getCoordMap() {return coordTileMap;}


    //returns left adjacent direction to traverse in board gen.
    public static Direction directionLoop(Direction d) {
        Direction next;
        switch(d){
            case EAST:
                next  = Direction.NORTH_EAST;
                break; /***/
            case WEST:
                next  = Direction.SOUTH_WEST;
                break; /***/
            case NORTH_EAST:
                next  = Direction.NORTH_WEST;
                break;
            case NORTH_WEST:
                next  = Direction.WEST;
                break;
            case SOUTH_EAST:
                next  = Direction.EAST;
                break;
            case SOUTH_WEST:
                next  = Direction.SOUTH_EAST;
                break;
            default: throw new IllegalArgumentException("Invalid Direction");

        }
        return next;
    }

    //generates board in spiral pattern, starting from top left tile
    public static void generateBoard(){
        //initial parameters for pattern gen.
        Coordinate currentCoordinate = new Coordinate(initX, initY);

        Tile currentTile;

        Direction currentDirection;

        int tileCounter = 0;
        int rings = 5;
        int hops;
        Direction nextRingDirection = Direction.SOUTH_EAST; //direction to go from end tile of upper ring to start tile of lower ring
        for (int i = 1; i < rings; i++){ //indexes per ring of pattern traversed
            currentDirection = initDirection;
            hops = rings - i;
            for (int j = 6; 0 < j; j--) { //indexes number of turns traversed in a ring
                for (int z = 0; z < hops; z++) { //indexes number of hops traversed in a turn
                    currentTile = new Tile(currentCoordinate);
                    coordTileMap.put(currentCoordinate.getKey(), currentTile);

                    currentCoordinate = pathCalculator.calculate(currentDirection, currentCoordinate);
                    tileCounter++;
                }
                currentDirection = directionLoop(currentDirection);
            }
            currentCoordinate = pathCalculator.calculate(nextRingDirection, currentCoordinate);
        }
        currentTile = new Tile(new Coordinate(0, 0));
        coordTileMap.put(currentCoordinate.getKey(), currentTile);// bruhhhhh
    }



}