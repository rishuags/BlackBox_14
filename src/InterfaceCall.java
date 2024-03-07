import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Button;
import javafx.event.EventHandler;

import java.util.LinkedHashMap;
import java.util.Map;


public class InterfaceCall { //Class containing all functions that create or edit elements in the interface

    public static final Double fxInitialX=340.0 ;
    public static final Double fxInitialY=75.0 ;
    public static Integer LasersFired=0;
    public static void increaseLasersFired(){LasersFired++;}
    public static Integer getLasersFired(){return LasersFired;}

    private static int tilesSelected=0;

    //public static Button mySAbutton;
    //public static void setSAbutton(Button b){mySAbutton=b;}
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

    public static EventHandler<MouseEvent> laserClick = (MouseEvent m)-> {
        Group parent = (Group) (((Polygon) (m.getSource())).getParent());
        Polygon laserFX = (Polygon) (m.getSource());
        //System.out.println(laserFX.getId());
        if(laserFX.getFill()==Color.BLACK){
            //Configuration.initGateMap();
            Map<Integer,Gate> gateMap = Configuration.getGateMap();
            laserFX.setFill(Color.WHITE);
            Laser laser=new Laser(Integer.parseInt(laserFX.getId()));
            Integer endGateKey = laser.laserTraversal();
            System.out.println("Fire: "+(getLasersFired()+1)+", Input Gate: "+Integer.parseInt(laserFX.getId())+" , Output Gate: "+endGateKey);

            if(endGateKey==0){
                increaseLasersFired();
                InterfaceCalculator.generateGateLabel(laserFX, 0);
            }
            else{
                increaseLasersFired();
                InterfaceCalculator.generateGateLabel(laserFX, getLasersFired());
                Polygon endLaserFX = (Polygon)(searchNode(parent,endGateKey.toString()));
                endLaserFX.setFill(Color.WHITE);
                InterfaceCalculator.generateGateLabel(endLaserFX,getLasersFired());
            }


            parent.getChildren().remove(searchNode(parent, "SAButton"));
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


    public static void createBoardInterface (Group root ){
        Double initialX=fxInitialX;
        Double initialY=fxInitialY;
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
            finalCoords[i]=locateAtom(atomCoordArray[i][0],atomCoordArray[i][1],fxInitialX,fxInitialY);
            atomArr[i]=generateAtom(finalCoords[i][0],finalCoords[i][1]);
        }
    }


    public static Polygon generateLaser(Double initX, Double initY, Direction d, String id, Group root){


        Polygon laserOutline = new Polygon();

        //alpha vector=(8,-6)
        //Beta vector=(-6,-8)
        int horizontalMul=-1;
        int verticalMul=1;
        Double x=initX,y=initY;
        if(d==Direction.NORTH_EAST||d==Direction.SOUTH_EAST||d==Direction.EAST){
            horizontalMul=1;
            x=initX+60;
        }
        //---------------
        if(d==Direction.SOUTH_EAST||d==Direction.SOUTH_WEST){
            verticalMul=-1;
            y=initY+30;
        }

        if(d!=Direction.WEST && d!=Direction.EAST) {
            laserOutline.getPoints().addAll(
                    x - 7.0*horizontalMul, y - 5.5*verticalMul,
                    x - 22.0*horizontalMul, y - 14.5*verticalMul,
                    (x-22.0*horizontalMul)+5*horizontalMul,(y-14.0*verticalMul)-6.66*verticalMul,//y-0.5
                    (x-8.5*horizontalMul)+5*horizontalMul,(y-5.0*verticalMul)-6.66*verticalMul // y-0.5
            );
            //Coordinates for less thick gates
            /*laserOutline.getPoints().addAll(
                    x - 10.0*horizontalMul, y - 7.5*verticalMul,
                    x - 18.0*horizontalMul, y - 12.5*verticalMul,
                    (x-18.0*horizontalMul)+6*horizontalMul,(y-12.5*verticalMul)-8*verticalMul,
                    (x-11.5*horizontalMul)+6*horizontalMul,(y-7.5*verticalMul)-8*verticalMul
            );*/
        }
        //-------
        else {
            laserOutline.getPoints().addAll(
                    x , y + 8,
                    x , y + 22,
                    x + 7.4*horizontalMul, y + 22,
                    x + 7.4*horizontalMul, y + 8
            );
            //Coordinates for less thick gates
            /*laserOutline.getPoints().addAll(
                    x , y + 10,
                    x , y + 20,
                    x + 10*horizontalMul, y + 20,
                    x + 10*horizontalMul, y + 10
            );*/

        }
        laserOutline.setFill(Color.BLACK);
        laserOutline.setStrokeWidth(2);
        laserOutline.setStroke(Color.WHITE);
        laserOutline.setId(id);

       //Event handler to remove the Shuffle Atoms Button when a laser is clicked (Unfinished)
        laserOutline.addEventFilter(MouseEvent.MOUSE_CLICKED,(MouseEvent m)->{
            //System.out.println(root.lookup("SAbutton"));
            //root.getChildren().remove(root.lookup("SAbutton"));
        });



        laserOutline.addEventFilter(MouseEvent.MOUSE_CLICKED,laserClick);

        return laserOutline;
    }

    public static void generateLaserInterface (Group root){
        Double currentHexX = fxInitialX;
        Double currentHexY = fxInitialY;
        /***/
        //set up initial directions of the gates
        Direction direction1=Direction.SOUTH_WEST;//change this to Configuration.InitialDirection
                                                  //before that Configuration.InitialDirection must be set public
        Direction direction2=direction1;
        for (int i=0;i<5;i++){
            direction2=Configuration.directionLoop(direction2);
        }
        for(int i=0;i<4;i++){
            direction1=Configuration.directionLoop(direction1);
        }

        int horizontalMul=1;
        int verticalMul=1;  //multipliers used to direct the pointer of the coords where gates will be generated
        int count=2;

        for(int j=0;j<6;j++) {
            if(j<4&&j>0){horizontalMul=1;}
            else{horizontalMul=-1;}
            if(j<2){verticalMul=1;}
            else if(j<5&&j>2){verticalMul=-1;}
            else{
                verticalMul=0;
                horizontalMul=2*horizontalMul;
                }

            for (int i = 0; i < 5; i++) {
                if (i != 0) {
                    root.getChildren().add(generateLaser(currentHexX, currentHexY, direction1, ((Integer) (count+i-1)).toString(),root));//add to root later
                }
                if(count+i==55){
                    root.getChildren().add(generateLaser(currentHexX, currentHexY, direction2, ((Integer) (1)).toString(),root));//add to root later
                }
                else{
                    root.getChildren().add(generateLaser(currentHexX, currentHexY, direction2, ((Integer) (count+i)).toString(),root));//add to root later
                }
                if(i!=4){
                    currentHexX+=30*horizontalMul;
                    currentHexY+=50*verticalMul;
                }
                count++;
            }
            count+=4;
            direction2=Configuration.directionLoop(direction2);
            direction1=Configuration.directionLoop(direction1);
            //System.out.println(direction2.toString()+" "+direction1.toString());

        }
    }

    public static Node searchNode(Group root, String id){
        for(Node node: root.getChildren()){
            if(node.getId()!=null && node.getId().equals(id)){
                return node;
            }
        }
        //System.out.println("Node not found");
        return null;
    }
}
