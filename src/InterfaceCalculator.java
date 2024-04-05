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

        //Search for the initial gate that was clicked
        if(laserCount==-1){
            gateLabel.setText("R");
            gateLabel.setFill(Color.GREENYELLOW);
            gateLabel.setStroke(Color.GREENYELLOW);
        }
        else if(laserCount==0){
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
    /**
     * Copy above
     *
     * */
    public static float[] influenceCut(Integer x, Integer y, Board board){
        //Function to specify the dimensions of the circles of influence so that they only exist above the board

        boolean cut=false;
        for(Tile tile: board.edgeTileArray){
            if(board.coordinateTileMap.get((x).toString()+(y).toString())==tile){
                //System.out.println("Atom located at edge tile found");
                cut =true;
            }
        }

        if(cut){

            float startAngle;
            float angleSize;
            Integer size;
            if(x==-8){
                startAngle=270.0f;
                angleSize=240.0f;
                if(y==-4){
                    startAngle+=60.0f;
                    angleSize-=60;
                }
                else if(y==0){
                    angleSize-=60;
                }
            }
            else if(y==-4){
                startAngle=330.0f;
                angleSize=240.0f;
                if(x==0){
                    startAngle=30.0f;
                    angleSize-=60;
                }
            }
            else if(y<=0){
                startAngle=30.0f;
                angleSize=240.0f;
                if(x==8){
                    angleSize-=60;
                    startAngle+=60;
                }
            }
            else if(x==8){
                startAngle=90.0f;
                angleSize=240.0f;
                if(y==4){
                    angleSize-=60;
                    startAngle+=60;
                }
            }
            else if(y==4){
                startAngle=150.0f;
                angleSize=240.0f;
                if(x==0){
                    angleSize-=60;
                    startAngle+=60;
                }
            }
            else{
                startAngle=210.0f;
                angleSize=240.0f;
            }
            float[] ans = new float[2];
            ans[0]=startAngle;
            ans[1]=angleSize;
            return ans;
        }
        float[] ans = new float[2];
        ans[0]=270.0f;
        ans[1]=360.0f;
        return ans;
    }


}
