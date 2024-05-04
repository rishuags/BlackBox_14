import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertEquals;

import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class InterfaceTest {

    @Test
    public void hexagonTest(){

        //test if generateHexagon generates a complete and correct hexagon working as a tile

        //hexagon generated with generateHex
        Polygon functHex = InterfaceCall.generateHexagon(50.0,50.0);
        //hexagon generated manually
        Polygon customHex=new Polygon();
        customHex.getPoints().addAll(new Double[]{50.0,50.0, 80.0,30.0, 110.0,50.0, 110.0,80.0, 80.0,100.0, 50.0,80.0});
        assertEquals(functHex.getPoints(),customHex.getPoints());

        //repeat with different coords
        functHex = InterfaceCall.generateHexagon(60.0,60.0);
        //hexagon generated manually
        customHex=new Polygon();
        customHex.getPoints().addAll(new Double[]{60.0,60.0, 90.0,40.0, 120.0,60.0, 120.0,90.0, 90.0,110.0, 60.0,90.0});
        assertEquals(functHex.getPoints(),customHex.getPoints());

    }

    @Test
    public void atomTest(){
        Circle functCir = InterfaceCall.generateAtom(50.0,50.0);

        Circle customCir = new Circle();
        customCir.setCenterX(50.0);
        customCir.setCenterY(50.0);
        customCir.setRadius(20.0);

        assertEquals((int)functCir.getCenterX(), (int)customCir.getCenterX());
        assertEquals((int)functCir.getCenterY(),(int)customCir.getCenterY());

    }

    @Test
    public void circInfluenceTest(){
        Arc functArc = InterfaceCall.circleInfluence(50.0,50.0,45, 180);

        Arc customArc= new Arc();
        customArc.setCenterX(50);
        customArc.setCenterY(50);

        customArc.setRadiusX(50);
        customArc.setRadiusY(50);

        customArc.setStartAngle(45);
        customArc.setLength(180);
        customArc.setType(ArcType.OPEN);

        assertEquals((int)functArc.getCenterX(), (int)customArc.getCenterX());
        assertEquals((int)functArc.getCenterY(),(int)customArc.getCenterY());

        assertEquals((int)functArc.getCenterX(), (int)customArc.getCenterX());
        assertEquals((int)functArc.getCenterY(), (int)customArc.getCenterY());
    }

    @Test
    public void locateAtomTest(){//Test the function to turn coordinates in ou own system to javaFX coordinates

        Double[] fxCoords=InterfaceCall.locateAtom(2,2,InterfaceCall.fxInitialX,InterfaceCall.fxInitialY);
        assertEquals("[490.0, 140.0]", Arrays.toString(fxCoords));
        fxCoords=InterfaceCall.locateAtom(-6,-2,InterfaceCall.fxInitialX,InterfaceCall.fxInitialY);
        assertEquals("[370.0, 390.0]", Arrays.toString(fxCoords));

    }

    @Test
    public void generateLaserTest(){
        Polygon customGate = new Polygon();
        Double x = InterfaceCall.fxInitialX;
        Double y = InterfaceCall.fxInitialY;
        customGate.getPoints().addAll(x, y+8,x,y+22, x-7.4, y+22, x-7.4, y+8);
        Polygon functGate = InterfaceCall.generateLaser(x,y,Direction.WEST,"1");
        assertEquals(customGate.getPoints(),functGate.getPoints());

        functGate=InterfaceCall.generateLaser(x,y,Direction.NORTH_WEST,"1");
        customGate=new Polygon();
        customGate.getPoints().addAll(x+7.0,y-5.5,x+22,y-14.5,x+22-5,y-14-6.66,x+8.5-5,y-5.0-6.66);
        assertEquals(customGate.getPoints(),functGate.getPoints());
    }



}