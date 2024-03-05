public class Gate {
    /***/

     private Tile t;
     private Direction d;

    public Gate(Tile t, Direction d) {
        this.t = t;
        this.d = d;
    }
    public Gate(Direction d) {
        this.d = d;
    }


    public Tile getTile() {
        return t;
    }

    public Direction getDirection() {
        return d;
    }

    @Override
    public String toString() {
        return "Gate{" +
                "t=" + t +
                ", d=" + d +
                '}';
    }
}
