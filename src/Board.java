import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Board {

    //Gate Number to Tile Map
    private final Map<Integer, Tile> gateTileMap = new HashMap<>();


    //Gate Number to Direction Map
    private final Map<Integer, Direction> gateDirectionMap = new HashMap<>();

    //Direction to left Direction Map
    private final Map<Direction, Direction> leftDirectionMap = new HashMap<>();

    //Direction to right Direction Map
    private final Map<Direction, Direction> rightDirectionMap = new HashMap<>();

    //Board Map
    private final Map<String, Tile> boardMap = new LinkedHashMap<>();

    public final CoordinateCalculator calculator = new CoordinateCalculator();

    public Map<Integer, Tile> getGateTileMap() {
        return gateTileMap;
    }

    public Map<Integer, Direction> getGateDirectionMap() {
        return gateDirectionMap;
    }

    public Map<Direction, Direction> getLeftDirectionMap() {
        return leftDirectionMap;
    }

    public Map<Direction, Direction> getRightDirectionMap() {
        return rightDirectionMap;
    }
    public Map<String, Tile> getBoardMap() {
        return boardMap;
    }


    public void createBoard(){

        Coordinate currentCoordinate = new Coordinate(Configuration.initX, Configuration.initY);

        Tile currentTile = new Tile(currentCoordinate);

        boardMap.put(currentCoordinate.getKey(), currentTile);

        Direction currentDirection = Configuration.initDirection;
        boolean inProgress = true;

        int counter = 1;
        int diff = 4;
        int turningPoint = counter + diff;

        while(inProgress){

            System.out.println("Counter: " + counter );
            if(turningPoint == counter){
                //System.out.println("Found Turning Point, Changing Direction...");
                currentDirection =  getLeftDirectionMap().get(currentDirection);
                //System.out.println("Moving in direction: " + currentDirection);
                turningPoint = turningPoint + diff;
                //System.out.println("Next Turning point: " + turningPoint);

            }

            Coordinate nextCoordinate = calculator.calculate(currentDirection, currentCoordinate);
            Tile nextTile = new Tile(nextCoordinate);


            if(boardMap.containsKey(nextCoordinate.getKey())){
                currentDirection =  getLeftDirectionMap().get(currentDirection);

                //System.out.println("Found Existing Tile, one shell completed, changing direction to: " + currentDirection);

                turningPoint = counter + diff;
                //System.out.println("Recalculated Turning Point: " + turningPoint);
                diff--;
                counter--;
                //System.out.println("Difference " + diff);


            }
            else{
                boardMap.put(nextCoordinate.getKey(), nextTile);
                System.out.println("current coordinate: " +  currentCoordinate);
                currentCoordinate = nextCoordinate;
                System.out.println("next coordinate"  +  currentCoordinate);

            }
            if(currentCoordinate.getY()==0 && currentCoordinate.getX()==0){
                inProgress = false;
            }
            counter++;

        }
    }

}
