import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class InterfaceCall { //Class containing all functions that create or edit elements in the interface

    public static ArrayList<Integer> previousScores = new ArrayList<>();

    public static boolean eventEnable = true;
    public static final Font mainFont = new Font("Consolas",30);
    public static final Double fxInitialX=340.0 ;
    public static final Double fxInitialY=75.0 ;
    public static Integer LasersFired=0;
    public static Integer score=0;
    public static Circle[] atomsFX = new Circle[6];
    public static Arc[] influenceFX = new Arc[6];
    public static Text scoreDisplayFX=null;
    public static void increaseLasersFired(){LasersFired++;}
    public static Integer getLasersFired(){return LasersFired;}

    //private static int tilesSelected=0;
    public static ArrayList<Polygon> selectedTileList= new ArrayList<>();
    public static ArrayList<Polygon> firedGates = new ArrayList<>();
    public static int getTilesSelected(){
        return selectedTileList.size();
        //return tilesSelected;
    }
    public static void increaseTileCount(Polygon p){
        //tilesSelected++;
        selectedTileList.add(p);
    }
    public static void decreaseTileCount(Polygon p){
        //tilesSelected--;
        selectedTileList.remove(p);
    }
    public static EventHandler<MouseEvent> tileSelect= (MouseEvent m)->{//Change the color of tile when clicked

        if(!eventEnable){
            return;
        }

        if(((Polygon)(m.getSource())).getFill()==Color.AQUAMARINE){
            ((Polygon)(m.getSource())).setFill(Color.BLACK);
            decreaseTileCount(((Polygon)(m.getSource())));
        }
        else if(getTilesSelected()<6){
            //Alternative Colors : DARKGOLDENROD, DARK_AQUAMARINE
            ((Polygon)(m.getSource())).setFill(Color.AQUAMARINE);
            increaseTileCount(((Polygon)(m.getSource())));
        }
        System.out.println(getTilesSelected());
        System.out.println(calculateFinalScore());
    };

    public static EventHandler<MouseEvent> laserClick = (MouseEvent m)-> {

        if(!eventEnable){
            return;
        }

        Group parent = (Group) (((Polygon) (m.getSource())).getParent());
        Polygon laserFX = (Polygon) (m.getSource());

        //System.out.println(laserFX.getId());
        if(laserFX.getFill()==Color.BLACK){
            //Configuration.initGateMap();
            firedGates.add(laserFX);
            score++;
            Map<Integer,Gate> gateMap = Configuration.getGateMap();
            laserFX.setFill(Color.WHITE);
            Laser laser=new Laser(Integer.parseInt(laserFX.getId()));
            Integer endGateKey = laser.laserTraversal();
            //System.out.println("Fire: "+(getLasersFired()+1)+", Input Gate: "+Integer.parseInt(laserFX.getId())+" , Output Gate: "+endGateKey);

            if(Integer.parseInt(laserFX.getId())==endGateKey){
                InterfaceCalculator.generateGateLabel(laserFX, -1);
            }
            else if(endGateKey==0){
                //increaseLasersFired();
                InterfaceCalculator.generateGateLabel(laserFX, 0);
            }
            else{
                increaseLasersFired();
                InterfaceCalculator.generateGateLabel(laserFX, getLasersFired());
                Polygon endLaserFX = (Polygon)(searchNode(parent,endGateKey.toString()));
                endLaserFX.setFill(Color.WHITE);
                firedGates.add(endLaserFX);
                InterfaceCalculator.generateGateLabel(endLaserFX,getLasersFired());
            }

            updateScore();
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
        updateScore();//create the scoreboard
        if(previousScores.isEmpty()){
            root.getChildren().add(scoreDisplayFX);
        }
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

    public static Arc circleInfluence(Double x, Double y, float startAngle, float angleSize){
    //Create visual element that represents the circles of influence around atoms
        Arc arc = new Arc();
        arc.setCenterX(x);
        arc.setCenterY(y);

        arc.setRadiusX(50);
        arc.setRadiusY(50);

        arc.setStartAngle(startAngle);
        arc.setLength(angleSize);
        arc.setType(ArcType.OPEN);

        arc.setStroke(Color.YELLOW);//Alternative Colors: YELLOW, GREEN
        arc.setStrokeWidth(2.0);
        arc.setFill(null);
        return arc;
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
        for(Arc arc : influenceFX){
            arc.setVisible(!arc.isVisible());
        }
    }



    public static void atomsDisplay(Board board, Group root){
        Integer[][] atomCoordArray = board.getAtomTiles();//function that returns a 2d array containing the coordinates of atoms
        Double[][] finalCoords =new Double[6][2];
        float[] influenceSpecification; // array that will hold specifications to generate circles of influence
        for(int i=0;i<6;i++){
            finalCoords[i]=locateAtom(atomCoordArray[i][0],atomCoordArray[i][1],fxInitialX,fxInitialY);
            atomsFX[i]=generateAtom(finalCoords[i][0],finalCoords[i][1]); //generate an atom
            influenceSpecification = InterfaceCalculator.influenceCut(atomCoordArray[i][0],atomCoordArray[i][1], board);
            //generate a circle of influence
            influenceFX[i]=circleInfluence(finalCoords[i][0],finalCoords[i][1],influenceSpecification[0], influenceSpecification[1]);
            root.getChildren().add(influenceFX[i]);// add the circle of influence to the GUI
        }

        for(int i=0;i<6;i++){
            root.getChildren().add(atomsFX[i]);
        }

    }



    public static Polygon generateLaser(Double initX, Double initY, Direction d, String id, Group root){
    //Generates the button that has to be pressed to fire a ray

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

       //Event handler to remove the Shuffle Atoms Button when a laser is clicked
        laserOutline.addEventFilter(MouseEvent.MOUSE_CLICKED,laserClick);

        return laserOutline;
    }

    public static void generateLaserInterface (Group root){//Generate all the gates from where rays are fired
        Double currentHexX = fxInitialX;
        Double currentHexY = fxInitialY;
        /***/
        //set up initial directions of the gates
        Direction direction1=Direction.SOUTH_WEST;//change this to Configuration.InitialDirection
                                                  //before that Configuration.InitialDirection must be set public
        Direction direction2=direction1;
        for (int i=0;i<5;i++){
            direction2=Configuration.leftDirection(direction2);
        }
        for(int i=0;i<4;i++){
            direction1=Configuration.leftDirection(direction1);
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
            direction2=Configuration.leftDirection(direction2);
            direction1=Configuration.leftDirection(direction1);
            //System.out.println(direction2.toString()+" "+direction1.toString());

        }
    }

    public static void updateScore(){
        if (scoreDisplayFX==null){ //if no scoreboard has been built yet, create one
            scoreDisplayFX = new Text("Score: "+ String.valueOf(0));
            scoreDisplayFX.setId("scoreText");
            scoreDisplayFX.setX(fxInitialX+475.0);
            scoreDisplayFX.setY(fxInitialY);
            scoreDisplayFX.setFont(mainFont);
            scoreDisplayFX.setTextAlignment(TextAlignment.JUSTIFY);
            scoreDisplayFX.setStroke(Color.WHITE);
            scoreDisplayFX.setStrokeWidth(1);
            scoreDisplayFX.setVisible(true);
        }
        else{
            scoreDisplayFX.setText("Score: "+ String.valueOf(score));
        }
    }

    public static void updateScoreFinal(){
        //You can only finisht turn
            scoreDisplayFX.setText("Final Score: "+ String.valueOf(calculateFinalScore()));
            //changeAtomVisible(atomsFX);



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

    public static Integer calculateFinalScore(){
        //For each guess that is wrong, 5 points are added to the board
        //return score +5*(6-checkAtomSelect());
        return score +5*(getTilesSelected()-checkAtomSelect());
    }
    public static int checkAtomSelect(){
        Integer rightGuesses=0;
        Path intersect;
        //loop through all atoms and all tiles to check if some of these intersects which means there is a right guess
        for(Circle circle : atomsFX){
            for(Polygon polygon: selectedTileList){
                intersect= (Path) Shape.intersect(polygon,circle); //cast the intersection of atoms and tiles to path to use isEmpty()
                if(!intersect.getElements().isEmpty()){
                    rightGuesses++;
                    break;
                }
            }
        }
        return rightGuesses;
    }

    public static void resetInterface(){
        previousScores.add(calculateFinalScore());
        score = 0;
        LasersFired=0;
        for(Polygon p: selectedTileList){
            p.setFill(Color.BLACK);
            //selectedTileList.remove(p);
        }
        for(Polygon p: firedGates){
            p.setFill(Color.BLACK);
            //selectedTileList.remove(p);
        }
        selectedTileList=new ArrayList<Polygon>();
        updateScore();
    }

    public static void displayFinalResults(Group previousRoot){

        previousScores.add(calculateFinalScore());
        Group finalRoot = new Group();

        for(int i=0;i<Controller.numPlayers;i++){
            Text playerScore = new Text("Player "+(i+1)+" Score: "+ String.valueOf(previousScores.get(i)));
            //playerScore.setId("scoreText");
            playerScore.setX(fxInitialX);
            playerScore.setY(fxInitialY+50*i);
            playerScore.setFont(mainFont);
            playerScore.setTextAlignment(TextAlignment.JUSTIFY);
            playerScore.setStroke(Color.WHITE);
            playerScore.setStrokeWidth(1);
            playerScore.setVisible(true);
            finalRoot.getChildren().add(playerScore);
        }
        Stage finalStage = (Stage)((Scene)(previousRoot.getScene())).getWindow();
        finalStage.setScene(new Scene(finalRoot,1100,600,Color.BLACK));
        finalStage.show();
    }
}
