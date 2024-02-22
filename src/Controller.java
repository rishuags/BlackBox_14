import java.util.Map;
import javafx.application.Application;
public class Controller {
    public static void main(String[] args) {

        Board board = new Board();

        Configuration c = new Configuration();
        //c.initGateMap(board);
        c.initGateDirectionMap(board);
        c.initLeftAndRightGateDirection(board);

        board.createBoard();



        for(Tile t: board.getGateTileMap().values()){
            //System.out.println(t);
        }
        //System.out.println("*************************");
        for(Map.Entry<Integer, Direction> d : board.getGateDirectionMap().entrySet()){
            //System.out.println(d);
        }
        System.out.println("*************************");
        for(Map.Entry<Direction, Direction> ld : board.getLeftDirectionMap().entrySet()){
            //System.out.println(ld);
        }
        System.out.println("*************************");
        for(Map.Entry<Direction, Direction> rd : board.getRightDirectionMap().entrySet()){
            //System.out.println(rd);
        }
        Application.launch(Interface.class,args);
        //System.out.println(board.getBoardMap());

    }
}
