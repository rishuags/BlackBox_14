public class Gate {
    Integer sNumber; //associated side number of gate
    Direction direction; //initial ray direction of gate
    public Gate (Integer i, Direction d) {
        sNumber = i;
        direction = d;
    }

    public Integer getsNumber() {
        return sNumber;
    }

    public void setsNumber(Integer sNumber) {
        this.sNumber = sNumber;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
