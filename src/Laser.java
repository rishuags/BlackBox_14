import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class Laser {

    public ArrayList<String> path = new ArrayList<>();
    public Laser() {
    }

    public Integer laserTraversal(Integer inputGate) {

        /** initialize function parameters(data) **/
        Map<Integer, Gate> gateMap = Configuration.getGateMap();
        Gate gate = gateMap.get(inputGate);

        Coordinate currentCoordinate = gate.getCoordinate();
        String currentCoordinateKey = currentCoordinate.getKey(); //stores coordinate key of initial tile into current

        Map<String, Tile> boardMap = Configuration.getCoordTileMap(); //stores coordinateKey-Tile map

        Tile currentTile = boardMap.get(currentCoordinateKey);//accessing tile object based on current coordinate key
        Direction initialDirection = gate.getDirection();
        Direction currentSide = Configuration.reverseDirection(initialDirection); //reversing initial direction to get initial side
        Direction nextSide = currentTile.laserMap.get(currentSide); //assigns direction based on current side and tile mappings
        Coordinate nextCoordinate = PathCalculator.calculate(nextSide, currentCoordinate);


        while (!goesOffBoard(nextCoordinate, currentTile)) { //loop while next coordinate in path exists on board
            //System.out.println("Current Tile: " + currentCoordinateKey); (Testing)
            path.add(currentCoordinateKey);
            if (currentTile.hasAtom()) {
                //break; //break traversal if an atom exists //return -1 because laser gets absorbed (never reaches end gate)
                return -1;
            }

            /** iterate traversal **/
            currentCoordinate = nextCoordinate;
            nextCoordinate = PathCalculator.calculate(nextSide, currentCoordinate);
            currentCoordinateKey = currentCoordinate.getKey();

            currentTile = boardMap.get(currentCoordinateKey);

            currentSide = currentTile.laserMap.get(nextSide);
            nextSide = currentTile.laserMap.get(currentSide);
        }

        //System.out.println("Current Tile: " + currentCoordinateKey); (Testing)
        path.add(currentCoordinateKey);
        System.out.println(path.size());
        System.out.println(path);

        return 0;
    }

    public  boolean goesOffBoard(Coordinate nextCoordinate, Tile currentTile) {
        boolean goesOffBoard = false;
        String nextCoordinateKey = nextCoordinate.getKey();

        if (currentTile.isEdgeTile()) { //if currentTile is not edge, don't run loop (saves on time)
            if (!Configuration.getCoordTileMap().containsKey(nextCoordinateKey)) {goesOffBoard = true;} //if coordinate is out of range of map, out of bounds is true
        }

        return goesOffBoard;
    }
}