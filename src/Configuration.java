import java.util.Map;

public class Configuration {
    private static final Integer initX = 0;
    private static final Integer initY = 4;
    private static final Direction initDirection = Direction.SOUTH_WEST;



    public Direction directionLoop(Direction d) { //returns next direction to traverse in board gen.
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


    public void generateBoard() //generates board in spiral pattern, starting from top left tile

    {
        //initial parameters for pattern gen.
        Coordinate currentCoordinate = new Coordinate(initX, initY);

        Tile currentTile = new Tile(currentCoordinate);

        Direction currentDirection = initDirection;

        int tileCounter = 1;
        int rings = 5;
        int hops;
        for (int i = 1; i < rings; i++){ //indexes per ring of pattern traversed
            hops = rings - i;
            for (int j = 5; 0 < j; j--) { //indexes number of turns traversed in a ring
                for (int z = 0; z < hops; z++) { //indexes number of hops traversed in a turn
                    tileCounter++;
                }
                Coordinate nextCoordinate = pathCalculator.calculate(currentDirection, currentCoordinate);
                currentDirection = directionLoop(currentDirection);
            }
        }
        System.out.print(tileCounter);
    }

}
