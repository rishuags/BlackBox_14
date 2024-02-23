import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Button;

import java.util.Random;

public class InterfaceCall { //Class containing all functions that create or edit elements in the interface

    public static final Double fxinitialX=340.0 ;
    public static final Double fxInitialY=75.0 ;

    public static Polygon generateHexagon(Double x, Double y){
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


    public static void createBoardInterface (Group root,Double initialX,Double initialY ){
        for(int i =0;i<9;i++){
            if(i<5) {
                for (int j = 0; j < 5 + (i % 5); j++) {
                    root.getChildren().add(InterfaceCall.generateHexagon(initialX + j * 60.0, initialY));
                }
            }
            else{
                for (int j = 0; j < 8 - (i % 5); j++) {
                    root.getChildren().add(InterfaceCall.generateHexagon(initialX + j * 60.0, initialY));
                }
            }
            initialY=initialY+50.0;
            if(i<4){initialX=initialX-30;}
            else{initialX=initialX+30;}
        }
    }

    public static Circle generateAtom(Double x, Double y){ //Function to generate a circle that will represent an atom
        Circle newCir = new Circle();
        newCir.setCenterX(x);
        newCir.setCenterY(y);
        newCir.setRadius(20.0);
        newCir.setFill(Color.RED);
        return newCir;
    }

    public static Double[] locateAtom(int x, int y, Double initialX, Double initialY){//function to change the coordinates of an atom to javaFX coordinates
        //y: +50 (4 times), +15
        //x: +60 (2 times), +30
        Double ZeroX =initialX+150; //Set the coordinates of the middle tile using the points from
        Double ZeroY =initialY+215; //which the board was generated in javafx

        Double fxCoords[]= new Double[2];
        fxCoords[0]=ZeroX+0.5*x*60-y*30;
        fxCoords[1]=ZeroY-y*50;
        return fxCoords;
    }

    public static void changeAtomVisible(Circle[] atomArr){//Function to make the atoms visible or invisible
        for (Circle circle : atomArr) {
            circle.setVisible(!circle.isVisible());
        }
    }

    public static void RelocateAtoms (Circle[] atomArr, Group n, Board board){
        //Function to give atoms their new locations when desired
        for(int i=0;i<6;i++){
            n.getChildren().remove(atomArr[i]);
        }
        /*
        Random rand=new Random();
        int randomX,randomY;
        Double initialX = 340.0; //Coordinates from where the board will be generated
        Double initialY = 75.0;
        */
        Double displayCoords[];

        Integer atomCoordArr[][]=board.getAtomTiles();
        //Temporary code
        for (int i=0;i<6;i++){

            /*
            randomX=rand.nextInt(8)*2-8;
            randomY=rand.nextInt(8)-4;
            */
            displayCoords=InterfaceCall.locateAtom(atomCoordArr[i][0],atomCoordArr[i][1],fxinitialX,fxInitialY);
            atomArr[i]=InterfaceCall.generateAtom(displayCoords[0],displayCoords[1]);
            n.getChildren().add(atomArr[i]);
        }
    }

    public static Circle[] atomsDisplay(Circle[] atomArr,Board board){


    }

}
