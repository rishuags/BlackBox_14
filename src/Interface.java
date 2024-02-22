import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Button;
import javafx.scene.text.Text;


public class Interface extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        primaryStage.setTitle("BlackBall");
        primaryStage.setScene(new Scene(root, 900, 600, Color.BLACK));

        




        primaryStage.show();
    }

    private Polygon generateHexagon(Double x, Double y){
        //Function to generate a hexagon from a single point, used to generate the board
        Polygon newHex= new Polygon();
        newHex.getPoints().addAll(new Double[]{
                x,y,
                x+30,y-20,
                x+60,y,
                x+60,y+30,
                x+30,y+50,
                x,y+30
        });
        newHex.setFill(Color.BLACK);
        newHex.setStrokeWidth(3);
        newHex.setStroke(Color.WHITE);
        return newHex;
    }

    private void hideAtom(Circle circ){
        circ.setVisible(false);
    }



}
