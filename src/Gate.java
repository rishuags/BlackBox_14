public class Gate {
    Integer sNumber; //associated side number of gate
    Direction direction; //initial ray direction of gate

    Tile tile; //initial tile of ray
    public Gate (Integer i, Direction d, Tile t) {
        sNumber = i;
        direction = d;
        tile = t;
    }

    public Integer getsNumber() {
        return sNumber;
    }

    public Direction getDirection() {
        return direction;
    }

}
