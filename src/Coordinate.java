public class Coordinate {

    private Integer x;
    private Integer y;
    private String key;

    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.key = x.toString() + y.toString();
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
        return "Coordinate{" + getX() + ", " + getY() + "}";
    }
}