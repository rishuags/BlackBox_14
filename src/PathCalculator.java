public class PathCalculator {

    public static Coordinate calculate(Direction d, Coordinate current){
        Coordinate next = null;
        switch(d){
            case EAST:
                next  = new Coordinate(current.getX()+2, current.getY());
                break;
            case WEST:
                next  = new Coordinate(current.getX()-2, current.getY());
                break;
            case NORTH_EAST:
                next  = new Coordinate(current.getX()+2, current.getY()+1);

                break;
            case NORTH_WEST:
                next  = new Coordinate(current.getX(), current.getY()+1);
                break;
            case SOUTH_EAST:
                next  = new Coordinate(current.getX(), current.getY()-1);
                break;
            case SOUTH_WEST:
                next  = new Coordinate(current.getX()-2, current.getY()-1);
                break;
            case ATOM:
                throw new IllegalArgumentException("Atom"); //case in development
            default: throw new IllegalArgumentException("Invalid Direction");

        }
        return next;
    }
}