import java.util.Map;

public class Jar_Main {
    public static void main(String[] args){

        /**TESTING DATA*/

        Configuration config = new Configuration();
        Configuration.initGateMap();
        Configuration.generateBoard();


        for(Map.Entry<Integer, Gate> d : config.getGateMap().entrySet()){
            //System.out.println(d);
        }

        for(Map.Entry<String, Tile> d : config.getCoordMap().entrySet()){
            System.out.println(d.getKey());
        }



        Controller.main(args);
    }
}
