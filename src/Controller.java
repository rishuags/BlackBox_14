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

public class Controller extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    Group root = new Group();
    primaryStage.setTitle("Black_Box_GRP14");




    



    primaryStage.setScene(new Scene(root,900,600,Color.BLACK));
    }


    private static void main(String[] args){
    launch(args);
    }

}
