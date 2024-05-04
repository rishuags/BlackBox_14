import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Map;

public class JFXevents {

    //Event to allow the user to select a tile
    public static EventHandler<MouseEvent> tileSelect= (MouseEvent m)->{//Change the color of tile when clicked

        if(!InterfaceCall.eventEnable){//check if events are enabled by the interface
            return;
        }
        //set the color of the selected to aquamarine if it was black or to black if it was aquamarine
        if(((Polygon)(m.getSource())).getFill()== Color.AQUAMARINE){
            ((Polygon)(m.getSource())).setFill(Color.BLACK);
            InterfaceCall.decreaseTileCount(((Polygon)(m.getSource())));
        }
        else if(InterfaceCall.getTilesSelected()<6){
            //Alternative Colors : DARKGOLDENROD, DARK_AQUAMARINE
            ((Polygon)(m.getSource())).setFill(Color.AQUAMARINE);
            InterfaceCall.increaseTileCount(((Polygon)(m.getSource())));
        }
    };

    //event to let the user fire a laser
    public static EventHandler<MouseEvent> laserClick = (MouseEvent m)-> {

        if(!InterfaceCall.eventEnable){//check if events are enabled
            return;
        }

        Group parent = (Group) (((Polygon) (m.getSource())).getParent());
        Polygon laserFX = (Polygon) (m.getSource());


        //if the laser was black -> it had not been fired, perform the firing laser actions
        if(laserFX.getFill()==Color.BLACK){
            //Configuration.initGateMap(); testing*
            InterfaceCall.firedGates.add(laserFX); //add the fired gate to the collection
            InterfaceCall.score++;                  //add 1 point to the score
            Map<Integer,Gate> gateMap = Configuration.getGateMap(); //get the map containing the ids of the gates from backend
            laserFX.setFill(Color.WHITE);
            Laser laser=new Laser(Integer.parseInt(laserFX.getId()));//create a new laser with the id of the fired button
            Integer endGateKey = laser.laserTraversal();    //generate the key where the ray is reflected

            //case for a rebound
            if(Integer.parseInt(laserFX.getId())==endGateKey){
                InterfaceCalculator.generateGateLabel(laserFX, -1);
            }
            //case for a hit
            else if(endGateKey==0){
                InterfaceCalculator.generateGateLabel(laserFX, 0);
            }
            //case for a normal deflection
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
