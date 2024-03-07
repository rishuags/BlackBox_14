import java.util.HashMap;
public class Tile {

    Coordinate c; //associated coordinate of tile

    public HashMap<Direction, Direction> laserMap = new HashMap<>();

    private boolean hasAtom = false;
    private boolean isEdgeTile = false;

    public Tile(Coordinate c) {
        this.c = c;
        assignDefaultMap();
    }
    public void assignDefaultMap() {
        laserMap.put(Direction.EAST, Direction.WEST);
        laserMap.put(Direction.WEST, Direction.EAST);

        laserMap.put(Direction.NORTH_EAST, Direction.SOUTH_WEST);
        laserMap.put(Direction.SOUTH_WEST, Direction.NORTH_EAST);

        laserMap.put(Direction.NORTH_WEST, Direction.SOUTH_EAST);
        laserMap.put(Direction.SOUTH_EAST, Direction.NORTH_WEST);
    }
    public void setAtom() { //initializes atom in tile
        hasAtom = true;
    }
    public void setNoAtom(){hasAtom = false;}

    public boolean hasAtom(){
        return hasAtom;
    }
    public boolean isEdgeTile() {
        return isEdgeTile;
    }
    public void setEdgeTile() {
        isEdgeTile = true;
    }

    /***/
    public Coordinate getCoordinate() {
        return c;
    }
}