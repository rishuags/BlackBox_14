public class Gate {
    Integer sNumber; //associated side number of gate
    Direction direction; //initial ray direction of gate

    Tile tile; //initial tile of ray
    public Gate (Integer i, Direction d, Tile t) { //initializes numbering, direction, initial tile
        sNumber = i;
        direction = d;
        tile = t;
    }

    public Integer getsNumber() {
        return sNumber;
    } //returns numbered side

    public Direction getDirection() {
        return direction;
    } //returns initial direction

    public Tile getTile() { //returns initial tile
        return tile;
    }
}
