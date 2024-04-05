import java.util.*;

public class Configuration {
    private static final Board board = new Board();

    private static final Map<String, Tile> coordTileMap = board.coordinateTileMap;
    private static final Tile[] edgeTileArrayConfig = board.edgeTileArray;
    private static final Integer initX = 0;
    private static final Integer initY = 4;
    private static final Direction initDirection = Direction.SOUTH_WEST;

    /***/
    private static Integer gateID;
    private static final Map<Integer, Gate> gateMap = new LinkedHashMap<>();

    public static Map<String, Tile> getCoordTileMap() {
        return coordTileMap;
    }
    public Tile[] getEdgeTileArrayConfig(){
        return edgeTileArrayConfig;
    }

    //utility function for reversing direction
    public static Direction reverseDirection(Direction d){
        Direction reverse;
        switch(d){
            case EAST:
                reverse = Direction.WEST;
                break;
            case WEST:
                reverse = Direction.EAST;
                break;
            case NORTH_EAST:
                reverse = Direction.SOUTH_WEST;
                break;
            case SOUTH_WEST:
                reverse = Direction.NORTH_EAST;
                break;
            case NORTH_WEST:
                reverse = Direction.SOUTH_EAST;
                break;
            case SOUTH_EAST:
                reverse = Direction.NORTH_WEST;
                break;
            default:
                throw new IllegalArgumentException("Invalid Input");

        }

        return reverse;
    }
    /***
     implementing algorithm


     ***/

    public static void fillGateMap( Integer startGate, Integer endGate, Direction odd, Direction even, Direction movingDirection, Coordinate startingCoordinate) {
        Coordinate currentCoordinate = startingCoordinate;
        int flag = 0; //Switching. Every second gate

        //System.out.println(currentCoordinate);
        for (Integer i = startGate; i <= endGate; i++) {
            if (i % 2 == 1) {//odd
                gateMap.put(i, new Gate(odd, currentCoordinate));
            } else {
                gateMap.put(i, new Gate(even, currentCoordinate));
            }
            flag++;
            if(flag==2){
                currentCoordinate = PathCalculator.calculate(movingDirection, currentCoordinate);
                flag=0;
            }
        }
    }


    public static void initGateMap() {
        //54 Total Inputs/Gates
        fillGateMap(1, 9, Direction.SOUTH_EAST, Direction.EAST, Direction.SOUTH_WEST, new Coordinate(0,4));
        fillGateMap(10, 18, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, new Coordinate(-8,0 ));
        fillGateMap(19, 27, Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.EAST, new Coordinate(-8,-4));
        fillGateMap(28, 36, Direction.WEST, Direction.NORTH_WEST, Direction.NORTH_EAST, new Coordinate(0, -4));
        fillGateMap(37, 45, Direction.WEST, Direction.SOUTH_WEST, Direction.NORTH_WEST, new Coordinate(8,0 ));
        fillGateMap(46, 54, Direction.SOUTH_EAST, Direction.SOUTH_WEST, Direction.WEST, new Coordinate(8, 4));
    }


    //returns left adjacent direction to traverse in board gen.
    public static Direction leftDirection(Direction d) {
        Direction next;
        switch(d){
            case EAST:
                next  = Direction.NORTH_EAST;
                break;
            case WEST:
                next  = Direction.SOUTH_WEST;
                break;
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

    public static Direction rightDirection(Direction d) {
        Direction next;
        switch(d){
            case EAST:
                next  = Direction.SOUTH_EAST;
                break;
            case WEST:
                next  = Direction.NORTH_WEST;
                break;
            case NORTH_EAST:
                next  = Direction.EAST;
                break;
            case NORTH_WEST:
                next  = Direction.NORTH_EAST;
                break;
            case SOUTH_EAST:
                next  = Direction.SOUTH_WEST;
                break;
            case SOUTH_WEST:
                next  = Direction.WEST;
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

        int tileCounter = 1;
        int rings = 5;
        int hops;
        Direction nextRingDirection = Direction.SOUTH_EAST; //direction to go from end tile of upper ring to start tile of lower ring
        for (int i = 1; i < rings; i++){ //indexes per ring of pattern traversed
            currentDirection = initDirection;
            hops = rings - i;
            for (int j = 6; 0 < j; j--) { //indexes number of turns traversed in a ring
                for (int z = 0; z < hops; z++) { //indexes number of hops traversed in a turn
                    currentTile = new Tile(currentCoordinate);
                    if (tileCounter <= 24) { //if currentTile is an edge tile
                        currentTile.setEdgeTile();
                        edgeTileArrayConfig[i] = currentTile;
                        //System.out.println(edgeTileArrayConfig[i].getCoordinate());
                    }
                    coordTileMap.put(currentCoordinate.getKey(), currentTile);

                    currentCoordinate = PathCalculator.calculate(currentDirection, currentCoordinate);
                    tileCounter++;
                }
                currentDirection = leftDirection(currentDirection);
            }
            currentCoordinate = PathCalculator.calculate(nextRingDirection, currentCoordinate);
        }
        currentTile = new Tile(new Coordinate(0, 0));
        coordTileMap.put(currentCoordinate.getKey(), currentTile);// bruhhhhh
    }

    public static Map<Integer, Gate> getGateMap() {
        return gateMap;
    }

    /***/


    public Map<String, Tile> getCoordMap() {return coordTileMap;}

    public static void main(String[] args) {
        generateBoard();
    }
}