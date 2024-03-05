import java.util.Map;

public class Jar_Main {
    public static void main(String[] args){

        /**TESTING DATA*/

        Configuration config = new Configuration();
        Configuration.initGateMap();


        for(Map.Entry<Integer, Gate> d : config.getGateMap().entrySet()){
            System.out.println(d);
        }

        //Controller.main(args);



    }
}
