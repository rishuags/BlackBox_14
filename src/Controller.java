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
        root.getChildren().add(createStartButton(board));
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
        changeVisible.setId("HSButton");
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

    private static Button createStartButton(Board board){
        //Button to cange the visibility of the atoms
        Button changeVisible= new Button("Start turn");
        changeVisible.setId("Start turn");
        changeVisible.setLayoutX(60);
        changeVisible.setLayoutY(245);
        changeVisible.setPrefWidth(235);
        changeVisible.setPrefWidth(120);
        changeVisible.setOnAction(event->{ //when button clicked, the array of circles taken
            InterfaceCall.changeAtomVisible(InterfaceCall.atomsFX);
            ((Button)(event.getSource())).setVisible(false);
            Group currentRoot=(Group)(((Button)(event.getSource())).getParent());
            ((Group)(((Button)(event.getSource())).getParent())).getChildren().remove(InterfaceCall.searchNode(currentRoot,"SAButton"));
            ((Group)(((Button)(event.getSource())).getParent())).getChildren().remove(InterfaceCall.searchNode(currentRoot,"HSButton"));
            //((Group)(((Button)(event.getSource())).getParent())).getChildren().remove(InterfaceCall.searchNode(currentRoot,"Start turn"));
            ((Button)(event.getSource())).setVisible(false);
            ((Group)(((Button)(event.getSource())).getParent())).getChildren().add(createFinishButton(board));
        });
        return changeVisible;
    }

    private static Button createFinishButton(Board board){
        //Button to cange the visibility of the atoms
        Button changeVisible= new Button("Finish turn");
        changeVisible.setId("Finish turn");
        changeVisible.setLayoutX(60);
        changeVisible.setLayoutY(245);
        changeVisible.setPrefWidth(235);
        changeVisible.setPrefWidth(120);
        changeVisible.setOnAction(event->{ //when button clicked, the array of circles taken
            if(InterfaceCall.getTilesSelected()==6){
                InterfaceCall.eventEnable=false;
                InterfaceCall.changeAtomVisible(InterfaceCall.atomsFX);
                InterfaceCall.updateScoreFinal();
                ((Button)(event.getSource())).setVisible(false);
                turnCount++;
                if(turnCount<numPlayers){
                    ((Group)(((Button)(event.getSource())).getParent())).getChildren().add(createResetButton(board));
                }
                else{
                    ((Group)(((Button)(event.getSource())).getParent())).getChildren().add(createEndGameButton(board));
                }
            }
        });
        return changeVisible;
    }

    private static Button createResetButton(Board board){
        //Button to cange the visibility of the atoms
        Button changeVisible= new Button("Reset");
        changeVisible.setId("Reset");
        changeVisible.setLayoutX(60);
        changeVisible.setLayoutY(245);
        changeVisible.setPrefWidth(235);
        changeVisible.setPrefWidth(120);
        changeVisible.setOnAction(event->{ //when button clicked, the array of circles taken
            InterfaceCall.eventEnable=true;
            Group currentRoot=(Group)(((Button)(event.getSource())).getParent());
            ((Group)(((Button)(event.getSource())).getParent())).getChildren().add(createStartButton(board));
            ((Group)(((Button)(event.getSource())).getParent())).getChildren().add(createShuffleAtomsButton(currentRoot,board));
            ((Group)(((Button)(event.getSource())).getParent())).getChildren().add(createHideShowButton());


            //Reseting backend
            Configuration.initGateMap();
            Configuration config = new Configuration();
            Configuration.generateBoard();

            //board = new Board();
            board.coordinateTileMap=config.getCoordMap();
            //board.edgeTileArray=config.getEdgeTileArrayConfig();
            board.updateAtomTiles();
            board.GenerateAtoms();

            //Resetting the score values
            int j=0;
            InterfaceCall.resetInterface();
            for(Node node: currentRoot.getChildren()){ //delete all previous text
                if(node instanceof Text && node.getId()==null){
                    node.setVisible(false);
                }
            }
            //InterfaceCall.scoreDisplayFX.setVisible(true);
            //InterfaceCall.updateScore();//generate the new score text

            //add a new display of the board
            InterfaceCall.createBoardInterface(currentRoot);//add the display of the board
            //InterfaceCall.generateLaserInterface(currentRoot);
            //reset the atoms calling the same code in shuffle atoms
            for(int i=0;i<6;i++){
                //System.out.println(atomArr[i]);
                currentRoot.getChildren().remove(InterfaceCall.atomsFX[i]);
                currentRoot.getChildren().remove(InterfaceCall.influenceFX[i]);
                //System.out.println("atom removed");
            }
            board.GenerateAtoms();
            InterfaceCall.atomsDisplay(board,currentRoot);

            ((Button)(event.getSource())).setVisible(false);
            //((Group)(((Button)(event.getSource())).getParent())).getChildren().add(createStartButton());
        });
        return changeVisible;
    }

    private static Button createEndGameButton(Board board){
        //Button to cange the visibility of the atoms
        Button changeVisible= new Button("End game");
        changeVisible.setId("End Game");
        changeVisible.setLayoutX(60);
        changeVisible.setLayoutY(245);
        changeVisible.setPrefWidth(235);
        changeVisible.setPrefWidth(120);
        changeVisible.setOnAction(event->{ //when button clicked, the array of circles taken
//                InterfaceCall.eventEnable=false;
//                InterfaceCall.changeAtomVisible(InterfaceCall.atomsFX);
//                InterfaceCall.updateScoreFinal();
//                ((Button)(event.getSource())).setVisible(false);
            Group currentRoot=(Group)(((Button)(event.getSource())).getParent());
            InterfaceCall.displayFinalResults(currentRoot);
        });
        return changeVisible;
    }

}
