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
        Double initialX = 340.0; //Coordinates from where the board will be generated
        Double initialY = 75.0;
        InterfaceCall.createBoardInterface(root,initialX,initialY);//add the display of the board


        Circle atomArr[]=new Circle[6]; //Array containing the circles that represent the atoms

        //This code is temporary and is just used to test class functions
        Random rand=new Random();
        int randomX,randomY;
        Double displayCoords[];
        for (int i=0;i<6;i++){
            randomX=rand.nextInt(8)*2-8;
            randomY=rand.nextInt(8)-4;
            System.out.println("x: "+randomX+" y: "+randomY);
            displayCoords=InterfaceCall.locateAtom(randomX,randomY,initialX,initialY);
            atomArr[i]=InterfaceCall.generateAtom(displayCoords[0],displayCoords[1]);
            root.getChildren().add(atomArr[i]);
        }
        /*
        In this zone the board and tiles will be created



        */

        //Create the button to set atoms visible or invisible
        root.getChildren().add(createHideShowButton(atomArr));
        root.getChildren().add(createShuffleAtomsButton(atomArr,root));



        primaryStage.setScene(new Scene(root,900,600,Color.BLACK));
        primaryStage.show();
    }


    private static void main(String[] args){
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
    private static Button createShuffleAtomsButton(Circle[] atomArr,Group root){
        //Button to generate atoms randomly again
        Button changeVisible= new Button("Shuffle Atoms");
        changeVisible.setLayoutX(60);
        changeVisible.setLayoutY(395);
        changeVisible.setPrefWidth(235);
        changeVisible.setPrefWidth(120);
        changeVisible.setOnAction(event->{ //when button clicked, the array of circles taken
            InterfaceCall.RelocateAtoms(atomArr,root);    //as arguments changes its visibility
        });
        return changeVisible;
    }

}
