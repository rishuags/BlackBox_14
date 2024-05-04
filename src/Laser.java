import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Laser {

    public ArrayList<String> path = new ArrayList<>();

    private Integer inputGate;
    public Laser(Integer inputGate) {
        if(inputGate > 54|| inputGate < 1){
            throw new IllegalArgumentException("Invalid Gate Input");
        }
        this.inputGate = inputGate;
    }
    public Integer getInputGate() {
        return inputGate;
    }



    /**
     * Traverses laser through the board given an input gate number
     * @return returns output gate number or 0 if laser hits atom
     */
    public Integer laserTraversal() {

        // INITIALIZE FUNCTION PARAMETERS
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

        //REFLECTION FOR ATOM EXISTING AT ADJACENT EDGE TILE
        if(!currentTile.hasAtom()){
            Coordinate outsideBoardCoordinate = PathCalculator.calculate(currentSide, currentCoordinate);

            Direction leftDirection = Configuration.leftDirection(nextSide);
            Direction rightDirection = Configuration.rightDirection(nextSide);

            Coordinate leftCoordinate = PathCalculator.calculate(leftDirection, outsideBoardCoordinate);
            Coordinate rightCoordinate = PathCalculator.calculate(rightDirection, outsideBoardCoordinate);

            boolean leftCoordinateExists = false;
            boolean rightCoordinateExists = false;

            Tile leftTile=null;
            Tile rightTile=null;
            if(Configuration.getCoordTileMap().containsKey(leftCoordinate.getKey())){
                leftCoordinateExists = true;
                leftTile = Configuration.getCoordTileMap().get(leftCoordinate.getKey());
            }
            if(Configuration.getCoordTileMap().containsKey(rightCoordinate.getKey())){
                rightCoordinateExists = true;
                rightTile = Configuration.getCoordTileMap().get(rightCoordinate.getKey());
            }
            if(leftCoordinateExists && leftTile.hasAtom() || rightCoordinateExists && rightTile.hasAtom()){
                return findOutputGate(currentTile.getCoordinate().getKey(),currentSide, gateMap);//reversal
            }

        }

        //TRAVERSAL LOGIC ~ loop while next coordinate in path exists on board
        while (!goesOffBoard(nextCoordinate, currentTile)) {
            //System.out.println("Current Tile Coordinate : " + currentCoordinate);
            path.add(currentCoordinateKey);

            //DEFLECTION/REFLECTION LOGIC
            Direction newNextSide = lookForAtoms(currentTile, nextSide);
            if(newNextSide!=nextSide){ //New next side  caused by atom rerouting
                //System.out.println("Found Atom... Rerouting towards... " + newNextSide);
                Coordinate newNextCoordinate = PathCalculator.calculate(newNextSide, currentCoordinate);
                //System.out.println("Moving to: " + newNextCoordinate.toString() + " \n");

                //prevents laser from going of board because of atom deflection
                if(!boardMap.containsKey(newNextCoordinate.getKey())){
                    //System.out.println("Coordinate out of bounds:  " + "\n" + "CurrentCoordinateKey: " + currentCoordinateKey + "\n" + "Next Side:  "  +  nextSide );
                    Integer outputGate = findOutputGate(currentCoordinateKey, newNextSide, gateMap);
                    //System.out.println("Output gate: " + outputGate);
                    return outputGate;
                }
                nextSide = newNextSide;
                nextCoordinate = newNextCoordinate;
            }

            //ABSORPTION LOGIC
            if (currentTile.hasAtom()) {
                return 0;
            }


            //TRAVERSAL ITERATION
            currentCoordinate = nextCoordinate;
            nextCoordinate = PathCalculator.calculate(nextSide, currentCoordinate);
            currentCoordinateKey = currentCoordinate.getKey();

            currentTile = boardMap.get(currentCoordinateKey);

            currentSide = currentTile.laserMap.get(nextSide);
            nextSide = currentTile.laserMap.get(currentSide);
        }
        //System.out.println("Current Tile Coordinate (Ending Edge Tile Coordinate): " + currentCoordinate); //(Testing)
        //System.out.println("Last Next Side: " + nextSide);

        path.add(currentCoordinateKey); //Adding last tile (edge tile) to path

        if (currentTile.hasAtom()) {
            return 0;
        }

        //Checking surrounding atoms for last tile
        Direction newNextSide = lookForAtoms(currentTile, nextSide);

        Integer outputGate = findOutputGate(currentCoordinateKey, newNextSide, gateMap);


        return outputGate;
    }

    /**
     * Returning output gate based on the current edge tile, and the direction the ray is heading(contra-to gate input direction)
     **/
    public Integer findOutputGate(String currentCoordinateKey, Direction nextSide, Map<Integer, Gate> gateMap ){
        Set<Integer> keys = gateMap.keySet(); //returns set of all gate index numbers (1-54)
        Integer outputGate = -1;
        String temp;

        Direction gateInputDirection;
        Direction gateOutputDirection;

        for(Integer key : keys){
            temp = gateMap.get(key).getCoordinate().getKey(); //stores entry tile of current gate

            gateInputDirection = gateMap.get(key).getDirection(); //stores input direction of a ray at current gate
            gateOutputDirection = Configuration.reverseDirection(gateInputDirection); //reverse input direction (direction needed to exit gate)

            if(temp.equals(currentCoordinateKey) && gateOutputDirection==nextSide){
                outputGate = key;
            }
        }
        return outputGate;
    }


    /**
     * Prevents laser from exiting board by checking if nextCoordinate is within boardMap constraints
     */
    public  boolean goesOffBoard(Coordinate nextCoordinate, Tile currentTile) {
        boolean goesOffBoard = false;
        String nextCoordinateKey = nextCoordinate.getKey();

        if (currentTile.isEdgeTile()) {
            if (!Configuration.getCoordTileMap().containsKey(nextCoordinateKey)) {goesOffBoard = true;} //if coordinate is out of range of map, out of bounds is true
        }

        return goesOffBoard;
    }


    /**
     * Reroutes traversal direction based on atom positions on tiles surrounding currentTile
     *
     *
     */
    public Direction lookForAtoms(Tile currentTile, Direction inputDirection){

        Direction leftDirection  = Configuration.leftDirection(inputDirection);
        Direction rightDirection = Configuration.rightDirection(inputDirection);

        Coordinate leftCoordinate = PathCalculator.calculate(leftDirection, currentTile.getCoordinate());
        Coordinate rightCoordinate = PathCalculator.calculate(rightDirection, currentTile.getCoordinate());
        Coordinate nextCoordinate = PathCalculator.calculate(inputDirection, currentTile.getCoordinate());

        Tile leftTile = null;
        Tile rightTile = null;
        Tile nextTile = null;


        //exists within board map coordinate constraints
        boolean leftCoordinateExists = false;
        boolean rightCoordinateExists = false;
        boolean nextCoordinateExists = false;

        //Checking if left, right, next coordinate of current tile exist on board
        if(Configuration.getCoordTileMap().containsKey(leftCoordinate.getKey())){
            leftCoordinateExists = true;
            leftTile = Configuration.getCoordTileMap().get(leftCoordinate.getKey());
        }
        if(Configuration.getCoordTileMap().containsKey(rightCoordinate.getKey())){
            rightCoordinateExists = true;
            rightTile = Configuration.getCoordTileMap().get(rightCoordinate.getKey());
        }
        if(Configuration.getCoordTileMap().containsKey(nextCoordinate.getKey())){
            nextCoordinateExists = true;
            nextTile = Configuration.getCoordTileMap().get(nextCoordinate.getKey());
        }

        /**deflection cases*/

        Direction newNextSide = inputDirection;

        //Case for non-edge-tile (tiles which have a left, right and next tile)
        if(leftCoordinateExists && rightCoordinateExists && nextCoordinateExists){
            //if leftTile, rightTile and nextTile have atom: new direction = reversed side (180 Deflection)
            if(leftTile.hasAtom() && rightTile.hasAtom() && nextTile.hasAtom()){
                newNextSide = Configuration.reverseDirection(inputDirection);
            }
            //if only leftTile and rightTile have atom: new direction = reversed side  (180 Deflection)
            else if(leftTile.hasAtom() && rightTile.hasAtom() && !nextTile.hasAtom()){
                newNextSide = Configuration.reverseDirection(inputDirection);
            }
            //if only leftTile has atom: new direction = right side of current side    (60 Deflection)
            else if(leftTile.hasAtom() && !rightTile.hasAtom() && !nextTile.hasAtom()){
                newNextSide = Configuration.rightDirection(inputDirection);
            }
            //if only rightTile has atom: new direction = left side of current side    (60 Deflection)
            else if(!leftTile.hasAtom() && rightTile.hasAtom() && !nextTile.hasAtom()){
                newNextSide = Configuration.leftDirection(inputDirection);
            }
            //if both leftTile and nextTile have atom: new direction = (right right) side of current side  (120 Deflection)
            else if(leftTile.hasAtom() && !rightTile.hasAtom() && nextTile.hasAtom()){
                newNextSide = Configuration.rightDirection(Configuration.rightDirection(inputDirection));
            }
            //if both rightTile and nextTile have atom: new direction = (left left) side of current side  (120 Deflection)
            else if(!leftTile.hasAtom() && rightTile.hasAtom() && nextTile.hasAtom()){
                newNextSide = Configuration.leftDirection(Configuration.leftDirection(inputDirection));
            }
            //else keep newNextSide the same; (0 Deflection)
            else{
                newNextSide = inputDirection;
            }
        }

        //Case for edge-tiles (tiles which may either not have a left or a right or a next or )
        if((!nextCoordinateExists || nextCoordinateExists && !nextTile.hasAtom())){
            if(!leftCoordinateExists && rightCoordinateExists){
                if(rightTile.hasAtom()){
                    newNextSide = Configuration.leftDirection(inputDirection);
                }
            } else if(leftCoordinateExists && !rightCoordinateExists){
                if(leftTile.hasAtom()){
                    newNextSide = Configuration.rightDirection(inputDirection);
                }
            }
        } else if(nextCoordinateExists && nextTile.hasAtom()){
            if(!leftCoordinateExists && rightCoordinateExists){
                if(rightTile.hasAtom()){
                    newNextSide = Configuration.leftDirection(Configuration.leftDirection(inputDirection));
                }
            } else if(leftCoordinateExists && !rightCoordinateExists){
                if(leftTile.hasAtom()){
                    newNextSide = Configuration.rightDirection(Configuration.rightDirection(inputDirection));
                }
            }
        }

        return newNextSide;
    }

}