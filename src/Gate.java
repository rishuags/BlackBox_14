import javafx.scene.control.skin.TextInputControlSkin;

public class Gate { //class of side edge instances
    int sNumber; //associated side number of side
    Direction direction; //initial direction of ray
    Tile tile; //initial tile of ray

    public Gate (int n, Direction d, Tile t) {
        sNumber = n;
        direction = d;
        tile = t;
    }
}
