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

    //Takes in input gate, returns output gate if laser reaches
    //Path determined based on unique entry-exit tile-side mappings for each tile
    //Returns 0 if laser hits atom

    public Integer laserTraversal() {

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

        /**REFLECTION FOR ATOM EXISTING AT ADJACENT EDGE TILE START*/
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

            if(leftCoordinateExists && leftTile.hasAtom() || rightCoordinateExists&&rightTile.hasAtom()){
                return findOutputGate(currentTile.getCoordinate().getKey(),currentSide, gateMap);
            }

        }

        /**END*/


        while (!goesOffBoard(nextCoordinate, currentTile)) { //loop while next coordinate in path exists on board
            System.out.println("Current Tile Coordinate : " + currentCoordinate);
            path.add(currentCoordinateKey);

            /**DEFLECTION/REFLECTION LOGIC START*/

            Direction newNextSide = lookForAtoms(currentTile, nextSide); /**For current tile, checking up to 4 surrounding tiles (whether they contain atom)**/ /**checks and prints adjacent tiles*/
            if(newNextSide!=nextSide){ //New next side  caused by atom rerouting
                //The Coordinate that the laser should go to based on newNextSide
                System.out.println("Found Atom... Rerouting towards... " + newNextSide);
                Coordinate newNextCoordinate = PathCalculator.calculate(newNextSide, currentCoordinate);
                System.out.println("Moving to: " + newNextCoordinate.toString() + " \n");

                //So that laser does not go off board because of atom deflection logic
                if(!boardMap.containsKey(newNextCoordinate.getKey())){
                    System.out.println("Coordinate out of bounds:  ");
//                System.out.println("CurrentCoordinateKey: " + currentCoordinateKey);
//                System.out.println("Next Side:  "  +  nextSide);
                    Integer outputGate = findOutputGate(currentCoordinateKey, newNextSide, gateMap); //Returning Output Gate based on the current edge tile, and the direction the ray is heading(contra-to gate input direction)
//                System.out.println("Output gate: " + outputGate);
                    return outputGate;
                }
                nextSide = newNextSide;
                nextCoordinate = newNextCoordinate;
            }



            /**DEFLECTION/REFLECTION LOGIC END*/


            /**ABSORPTION LOGIC**/
            if (currentTile.hasAtom()) {
                //break; //break traversal if an atom exists //return 0 because laser gets absorbed (never reaches end gate)
                return 0;
            }


            /** iterate traversal **/
            currentCoordinate = nextCoordinate;
            nextCoordinate = PathCalculator.calculate(nextSide, currentCoordinate);
            currentCoordinateKey = currentCoordinate.getKey();

            currentTile = boardMap.get(currentCoordinateKey);

            currentSide = currentTile.laserMap.get(nextSide);
            nextSide = currentTile.laserMap.get(currentSide);
        }

        System.out.println("Current Tile Coordinate (Ending Edge Tile Coordinate): " + currentCoordinate); //(Testing)
        //System.out.println("Last Next Side: " + nextSide);

        path.add(currentCoordinateKey); //Adding last tile (edge tile) to path

        if (currentTile.hasAtom()) {
            //break; //break traversal if an atom exists //return 0 because laser gets absorbed (never reaches end gate)
            return 0;
        }


        /**
         * Returning Output Gate based on the current edge tile, and the direction the ray is heading(contra-to gate input direction)
         **/
        Integer outputGate = findOutputGate(currentCoordinateKey, nextSide, gateMap);

        //System.out.println(path.size());
        System.out.println("Input Gate: " +  inputGate +  " Output Gate: " + outputGate);
        System.out.println("Path: " + path + "\n");



        return outputGate;
    }


    /**
     * Returning Output Gate based on the current edge tile, and the direction the ray is heading(contra-to gate input direction)
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


    public  boolean goesOffBoard(Coordinate nextCoordinate, Tile currentTile) {
        boolean goesOffBoard = false;
        String nextCoordinateKey = nextCoordinate.getKey();

        if (currentTile.isEdgeTile()) { //if currentTile is not edge, don't run loop (saves on time)
            if (!Configuration.getCoordTileMap().containsKey(nextCoordinateKey)) {goesOffBoard = true;} //if coordinate is out of range of map, out of bounds is true
        }

        return goesOffBoard;
    }



    /**reroutes traversal direction**/ /**checks and prints adjacent tiles*/
    public Direction lookForAtoms(Tile currentTile, Direction inputDirection){
        System.out.println("Input gate: " + inputGate);
        System.out.println("Current Direction: " + inputDirection);


        Direction leftDirection  = Configuration.leftDirection(inputDirection);
        System.out.println("Left Direction: " + leftDirection);
        Direction rightDirection = Configuration.rightDirection(inputDirection);
        System.out.println("Right Direction: " + rightDirection + "\n");


        Coordinate leftCoordinate = PathCalculator.calculate(leftDirection, currentTile.getCoordinate());
        Coordinate rightCoordinate = PathCalculator.calculate(rightDirection, currentTile.getCoordinate());
        Coordinate nextCoordinate = PathCalculator.calculate(inputDirection, currentTile.getCoordinate());

        Tile leftTile = null;
        Tile rightTile = null;
        Tile nextTile = null;


        /***/
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

        //making deflection cases:

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



        //case for edge-tiles (tiles which may either not have a left or a right or a next)
        if((!nextCoordinateExists || nextCoordinateExists && !nextTile.hasAtom())){
            if(!leftCoordinateExists && rightCoordinateExists){
                if(rightTile.hasAtom()){
                    newNextSide = Configuration.leftDirection(inputDirection);
                }
            }
            if(leftCoordinateExists && !rightCoordinateExists){
                if(leftTile.hasAtom()){
                    newNextSide = Configuration.rightDirection(inputDirection);
                }
            }
        } else if(nextCoordinateExists && nextTile.hasAtom()){
            if(!leftCoordinateExists && rightCoordinateExists){
                if(rightTile.hasAtom()){
                    newNextSide = Configuration.leftDirection(Configuration.leftDirection(inputDirection));
                }
            }
            if(leftCoordinateExists && !rightCoordinateExists){
                if(leftTile.hasAtom()){
                    newNextSide = Configuration.rightDirection(Configuration.rightDirection(inputDirection));
                }
            }
        }



        /***/

        return newNextSide;

    }
}