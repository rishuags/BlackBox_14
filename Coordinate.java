public class Coordinate {

    private Integer x;
    private Integer y;
    private String key;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.key = this.x.toString().concat(",").concat(this.y.toString());
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
