public class Gate {
    /***/
    private Direction d;
    private Coordinate c;


    public Gate(Direction d, Coordinate c) {
        this.c = c;
        this.d = d;
    }
    public Gate(Direction d) {
        this.d = d;
    }


    public Coordinate getCoordinate() {
        return c;
    }

    public Direction getDirection() {
        return d;
    }

    @Override
    public String toString() {
        return "Gate{" +
                "d=" + d +
                ", c=" + c +
                '}';
    }

}
