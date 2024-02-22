import java.util.Map;

public class Controller {
    public static void main(String[] args) {
        Board board = new Board();

        Configuration c = new Configuration();
        //c.initGateMap(board);
        c.initGateDirectionMap(board);
        c.initLeftAndRightGateDirection(board);

        board.createBoard();

    }
}
