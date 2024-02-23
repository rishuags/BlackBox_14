import java.util.Map;

public class Configuration {
    private static final Board board = new Board();

    private static final Map<String, Tile> coordTileMap = board.coordinateTileMap;
    private static final Integer initX = 0;
    private static final Integer initY = 4;
    private static final Direction initDirection = Direction.SOUTH_WEST;

    public Map<String, Tile> getCoordMap() {return coordTileMap;}

    public static Direction directionLoop(Direction d) { //returns next direction to traverse in board gen.
        Direction next;
        switch(d){
            case EAST:
                next  = Direction.NORTH_EAST;
                return next;
            case WEST:
                next  = Direction.SOUTH_WEST;
                return next;
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


    public static void generateBoard() //generates board in spiral pattern, starting from top left tile

    {
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
        coordTileMap.put("00", currentTile);
    }
}