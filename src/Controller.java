import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
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


    public static final Integer numPlayers = 2;
    public static Integer turnCount = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();          //Create group node that will contain all the visible elements
        primaryStage.setTitle("Black_Box_GRP14");
        InterfaceCall.createBoardInterface(root);//add the display of the board



        Configuration.initGateMap();    //Initialize the map of the gates-> the variables with the info about the buttons you click
                                        //to fire rays
        Configuration config = new Configuration();//create a configuration object for the board to initialize the rest of the variables
        Configuration.generateBoard();      //create the board itself

        Board board = new Board();
        board.coordinateTileMap=config.getCoordMap();   //make a board variable to contain the information about the board in config
        //board.edgeTileArray=config.getEdgeTileArrayConfig();
        board.setEdgeTiles();      //generate the objects inside the board
        board.GenerateAtoms();

        InterfaceCall.atomsDisplay(board,root); //display the atoms in the interface


        //Gate generation tests:
        InterfaceCall.generateLaserInterface(root); //generate the tiles in the interface


        //Create the button to set atoms visible or invisible
        //root.getChildren().add(JFXbuttons.createHideShowButton());  //add the button to hide or show the atoms (testing only)
        Button mySAbutton=JFXbuttons.createShuffleAtomsButton(root,board);//add button to shuffle the atoms
        root.getChildren().add(mySAbutton);                         //add these buttons to the interface
        root.getChildren().add(JFXbuttons.createStartButton(board));
        //InterfaceCall.setSAbutton(mySAbutton);


        primaryStage.setScene(new Scene(root,1100,600,Color.BLACK));    //start the interface
        primaryStage.show();
    }


    public static void main(String[] args){
    launch(args);
    }


}
