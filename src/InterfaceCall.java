import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Button;
import javafx.event.EventHandler;


public class InterfaceCall { //Class containing all functions that create or edit elements in the interface

    public static final Double fxinitialX=340.0 ;
    public static final Double fxInitialY=75.0 ;

    private static int tilesSelected=0;
    public static int getTilesSelected(){return tilesSelected;}
    public static void increaseTileCount(){tilesSelected++;}
    public static void decreaseTileCount(){tilesSelected--;}
    public static EventHandler<MouseEvent> tileSelect= (MouseEvent m)->{//Change the color of tile when clicked
        if(((Polygon)(m.getSource())).getFill()==Color.AQUAMARINE){
            //DARKGOLDENROD
            ((Polygon)(m.getSource())).setFill(Color.BLACK);
            decreaseTileCount();
        }
        else if(getTilesSelected()<6){
            ((Polygon)(m.getSource())).setFill(Color.AQUAMARINE);
            increaseTileCount();
        }
    };


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

        newHex.addEventFilter(MouseEvent.MOUSE_CLICKED,tileSelect);

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



    public static void atomsDisplay(Circle[] atomArr,Board board){
        Integer[][] atomCoordArray = board.getAtomTiles();
        Double[][] finalCoords =new Double[6][2];
        for(int i=0;i<6;i++){
            finalCoords[i]=locateAtom(atomCoordArray[i][0],atomCoordArray[i][1],fxinitialX,fxInitialY);
            atomArr[i]=generateAtom(finalCoords[i][0],finalCoords[i][1]);
        }
    }
}
