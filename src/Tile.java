public class Tile {

    Coordinate c; //associated coordinate of tile

    private boolean hasAtom = false;

    public Tile(Coordinate c) {
        this.c = c;
    }

    public void setAtom() { //initializes atom in tile
        hasAtom = true;
    }
    public void setNoAtom(){hasAtom = false;}

    public boolean hasAtom(){
        return hasAtom;
    }
    public Coordinate getCoordinate() {
        return c;
    }


}