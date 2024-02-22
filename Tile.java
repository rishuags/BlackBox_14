public class Tile {

    private String id;

    Coordinate c;

    private boolean hasAtom = false;

    public Tile(Coordinate c) {
        this.c = c;
        this.id = c.getX().toString() + c.getY().toString();
    }

    @Override
    public String toString() {
        return "Tile{" +
                "id=" + id +
                ", hasAtom=" + hasAtom +
                '}';
    }
}
