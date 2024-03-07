import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class InterfaceCalculator {
    //Class made for static components from the interface that provide computations

    public static void generateGateLabel(Polygon laser, Integer laserCount){//Function to generate a label related to a gate from which a ray has been fired

        Group parent = (Group)(laser.getParent());

        Text gateLabel= new Text();
        if(laserCount==0){
            gateLabel.setText("H");
            gateLabel.setFill(Color.RED);
            gateLabel.setStroke(Color.RED);
        }
        else{
            gateLabel.setText(((Integer)(laserCount)).toString());
            gateLabel.setFill(Color.GOLD);
            gateLabel.setStroke(Color.GOLD);
        }
        gateLabel.setStrokeWidth(0.7);
        gateLabel.setFont(Font.font(14));

        if(laser.getPoints().get(0).equals(laser.getPoints().get(2))){
            if(laser.getPoints().get(0) < laser.getPoints().get(4)){
                //Right side
                gateLabel.setX(laser.getPoints().get(4)+2);
                gateLabel.setY(laser.getPoints().get(5)-1.5);
                parent.getChildren().add(gateLabel);
            }
            else{
                //left side
                gateLabel.setX(laser.getPoints().get(4)-16);
                gateLabel.setY(laser.getPoints().get(5)-1.5);
                parent.getChildren().add(gateLabel);
            }
        }
        else{
            if(laser.getPoints().get(1)>(laser.getPoints().get(3)) && laser.getPoints().get(1)>InterfaceCall.fxInitialY && laser.getPoints().get(1)<InterfaceCall.fxInitialY+430){
                if(laser.getPoints().get(0)<laser.getPoints().get(2)){
                    //upper left side
                    gateLabel.setX(laser.getPoints().get(4)-22);
                    gateLabel.setY(laser.getPoints().get(5)+3);
                    parent.getChildren().add(gateLabel);
                }
                else{
                    //upper right side
                    gateLabel.setX(laser.getPoints().get(4)+4);
                    gateLabel.setY(laser.getPoints().get(5)+3);
                    parent.getChildren().add(gateLabel);
                }
            }
            else if(laser.getPoints().get(1)<(laser.getPoints().get(3)) && laser.getPoints().get(1)>InterfaceCall.fxInitialY && laser.getPoints().get(1)<InterfaceCall.fxInitialY+430){
                //Gates oriented South in middle layers
                if(laser.getPoints().get(0)<laser.getPoints().get(2)){
                    //lower left side
                    gateLabel.setX(laser.getPoints().get(4)-18);
                    gateLabel.setY(laser.getPoints().get(5)+5);
                    parent.getChildren().add(gateLabel);
                }
                else{
                    //lower right side
                    gateLabel.setX(laser.getPoints().get(4)+7);
                    gateLabel.setY(laser.getPoints().get(5)+7);
                    parent.getChildren().add(gateLabel);
                }
            }
            else if(laser.getPoints().get(1)<(laser.getPoints().get(3))){
                //Gates oriented South in bottom layer
                if(laser.getPoints().get(0)<laser.getPoints().get(2)){
                    //bottom left oriented gates
                    gateLabel.setX(laser.getPoints().get(4)-14);
                    gateLabel.setY(laser.getPoints().get(5)+12);
                    parent.getChildren().add(gateLabel);
                }
                else{
                    //bottom right oriented gates
                    gateLabel.setX(laser.getPoints().get(4));
                    gateLabel.setY(laser.getPoints().get(5)+12);
                    parent.getChildren().add(gateLabel);
                }
            }
            else{
                //Gates oriented North in the top layer
                if(laser.getPoints().get(0)<laser.getPoints().get(2)){
                    //Top left oriented gates
                    gateLabel.setX(laser.getPoints().get(4)-15);
                    gateLabel.setY(laser.getPoints().get(5)-3);
                    parent.getChildren().add(gateLabel);
                }
                else{
                    //Top right oriented gates
                    gateLabel.setX(laser.getPoints().get(4));
                    gateLabel.setY(laser.getPoints().get(5)-3);
                    parent.getChildren().add(gateLabel);
                }
            }
        }

    }


}
