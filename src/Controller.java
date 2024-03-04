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
import javafx.scene.Node;

import java.util.Random;

public class Controller extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();          //Create group node that will contain all the visible elements
        primaryStage.setTitle("Black_Box_GRP14");
        Double initialX = InterfaceCall.fxinitialX; //Coordinates from where the board will be generated
        Double initialY = InterfaceCall.fxInitialY;
        InterfaceCall.createBoardInterface(root,initialX,initialY);//add the display of the board




        Configuration config = new Configuration();
        config.generateBoard();

        Board board = new Board();
        board.coordinateTileMap=config.getCoordMap();
        board.GenerateAtoms();
        Circle[] atomArr= new Circle[6];

        InterfaceCall.atomsDisplay(atomArr,board);
        for(int i=0;i<6;i++){
            root.getChildren().add(atomArr[i]);
        }




        //Create the button to set atoms visible or invisible
        root.getChildren().add(createHideShowButton(atomArr));
        root.getChildren().add(createShuffleAtomsButton(atomArr,root,board));



        primaryStage.setScene(new Scene(root,900,600,Color.BLACK));
        primaryStage.show();
    }


    public static void main(String[] args){
    launch(args);
    }

    private static Button createHideShowButton(Circle[] atomArr){
        //Button to cange the visibility of the atoms
        Button changeVisible= new Button("Hide/Show Atoms");
        changeVisible.setLayoutX(60);
        changeVisible.setLayoutY(95);
        changeVisible.setPrefWidth(235);
        changeVisible.setPrefWidth(120);
        changeVisible.setOnAction(event->{ //when button clicked, the array of circles taken
            InterfaceCall.changeAtomVisible(atomArr);    //as arguments changes its visibility
        });
        return changeVisible;
    }

    private static Button createShuffleAtomsButton(Circle[] atomArr,Group root, Board board){
        //Button to generate atoms randomly again

        Button shuffle= new Button("Shuffle Atoms");
        shuffle.setLayoutX(60);
        shuffle.setLayoutY(395);
        shuffle.setPrefWidth(235);
        shuffle.setPrefWidth(120);
        shuffle.setOnAction(event->{ //when button clicked, the array of circles taken
            for(int i=0;i<6;i++){
                //System.out.println(atomArr[i]);
                root.getChildren().remove(atomArr[i]);
                //System.out.println("atom removed");
            }
            board.GenerateAtoms();
            InterfaceCall.atomsDisplay(atomArr,board);
            for(int i=0;i<6;i++){
                root.getChildren().add(atomArr[i]);
            }
        });
        return shuffle;
    }



}
