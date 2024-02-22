import java.util.Map;
import javafx.application.Application;
public class Controller {
    public static void main(String[] args) {
        Application.launch(Interface.class,args);


        Board board = new Board();

        Configuration c = new Configuration();
        //c.initGateMap(board);
        c.initGateDirectionMap(board);
        c.initLeftAndRightGateDirection(board);

        board.createBoard();


    }
}