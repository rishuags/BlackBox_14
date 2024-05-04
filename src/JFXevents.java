import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Map;

public class JFXevents {

    //Event to allow the user to select a tile
    public static EventHandler<MouseEvent> tileSelect= (MouseEvent m)->{//Change the color of tile when clicked

        if(!InterfaceCall.eventEnable){
            return;
        }
        if(((Polygon)(m.getSource())).getFill()== Color.AQUAMARINE){
            ((Polygon)(m.getSource())).setFill(Color.BLACK);
            InterfaceCall.decreaseTileCount(((Polygon)(m.getSource())));
        }
        else if(InterfaceCall.getTilesSelected()<6){
            //Alternative Colors : DARKGOLDENROD, DARK_AQUAMARINE
            ((Polygon)(m.getSource())).setFill(Color.AQUAMARINE);
            InterfaceCall.increaseTileCount(((Polygon)(m.getSource())));
        }
//        System.out.println(InterfaceCall.getTilesSelected());
//        System.out.println(InterfaceCall.calculateFinalScore());
    };

    //event to let the user fire a laser
    public static EventHandler<MouseEvent> laserClick = (MouseEvent m)-> {

        if(!InterfaceCall.eventEnable){
            return;
        }

        Group parent = (Group) (((Polygon) (m.getSource())).getParent());
        Polygon laserFX = (Polygon) (m.getSource());

        //System.out.println(laserFX.getId());
        if(laserFX.getFill()==Color.BLACK){
            //Configuration.initGateMap();
            InterfaceCall.firedGates.add(laserFX);
            InterfaceCall.score++;
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
                InterfaceCall.increaseLasersFired();
                InterfaceCalculator.generateGateLabel(laserFX, InterfaceCall.getLasersFired());
                Polygon endLaserFX = (Polygon)(InterfaceCall.searchNode(parent,endGateKey.toString()));
                endLaserFX.setFill(Color.WHITE);
                InterfaceCall.firedGates.add(endLaserFX);
                InterfaceCalculator.generateGateLabel(endLaserFX,InterfaceCall.getLasersFired());
            }

            InterfaceCall.updateScore();
            parent.getChildren().remove(InterfaceCall.searchNode(parent, "SAButton"));
        }
    };


}