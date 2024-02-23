public class Coordinate {

    private Integer x;
    private Integer y;
    private String key;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.key = this.x.toString() + this.y.toString();
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

}
