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
        InterfaceCall.createBoardInterface(root);//add the display of the board



        Configuration.initGateMap();
        Configuration config = new Configuration();
        Configuration.generateBoard();

        Board board = new Board();
        board.coordinateTileMap=config.getCoordMap();
        //board.edgeTileArray=config.getEdgeTileArrayConfig();
        board.updateAtomTiles();
        board.GenerateAtoms();

        InterfaceCall.atomsDisplay(board,root);


        //Gate generation tests:
        InterfaceCall.generateLaserInterface(root);


        //Create the button to set atoms visible or invisible
        root.getChildren().add(createHideShowButton());
        Button mySAbutton=createShuffleAtomsButton(root,board);
        root.getChildren().add(mySAbutton);
        //InterfaceCall.setSAbutton(mySAbutton);


        primaryStage.setScene(new Scene(root,1100,600,Color.BLACK));
        primaryStage.show();
    }


    public static void main(String[] args){
    launch(args);
    }

    private static Button createHideShowButton(){
        //Button to cange the visibility of the atoms
        Button changeVisible= new Button("Hide/Show Atoms");
        changeVisible.setLayoutX(60);
        changeVisible.setLayoutY(95);
        changeVisible.setPrefWidth(235);
        changeVisible.setPrefWidth(120);
        changeVisible.setOnAction(event->{ //when button clicked, the array of circles taken
            InterfaceCall.changeAtomVisible(InterfaceCall.atomsFX);    //as arguments changes its visibility
        });
        return changeVisible;
    }

    private static Button createShuffleAtomsButton(Group root, Board board){
        //Button to generate atoms randomly again

        Button shuffle= new Button("Shuffle Atoms");
        shuffle.setId("SAButton");
        shuffle.setLayoutX(60);
        shuffle.setLayoutY(395);
        shuffle.setPrefWidth(235);
        shuffle.setPrefWidth(120);
        shuffle.setOnAction(event->{ //when button clicked, the array of circles taken
            for(int i=0;i<6;i++){
                //System.out.println(atomArr[i]);
                root.getChildren().remove(InterfaceCall.atomsFX[i]);
                root.getChildren().remove(InterfaceCall.influenceFX[i]);
                //System.out.println("atom removed");
            }
            board.GenerateAtoms();
            InterfaceCall.atomsDisplay(board,root);
        });
        return shuffle;
    }



}
