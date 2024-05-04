import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Set;

public class Board {

    static final int MinX=-8;
    static final int MaxX=8;
    static final int MinY=-4;
    static final int MaxY=4;



    Map<String, Tile> coordinateTileMap = new LinkedHashMap<>();
    Tile[] edgeTileArray = new Tile[24];

    public void GenerateAtoms () {


        Random rand = new Random();
        int[] AtomIndexes = new int[6];
        for (int i = 0; i < 6; i++) {
            AtomIndexes[i] = rand.nextInt(61);
            for (int j = 0; j < i; j++) {
                if (AtomIndexes[j] == AtomIndexes[i]) {
                    i--;
                    break;
                }
            }
        }
        //works
        Set<String> keys = coordinateTileMap.keySet();
        int count = 0;
        //System.out.print("Atoms generated on coordinates: ");
        while(count<5){
            count = 0;
            int count2 = 0;
            for (String key : keys) {
                coordinateTileMap.get(key).setNoAtom();
            }
            //Set<String> keys2=coordinateTileMap.keySet();
            for (String key : keys) {
                for (int i = 0; i < 6; i++) {
                    if (count2 == AtomIndexes[i]) {
                        coordinateTileMap.get(key).setAtom();
                        //System.out.print(key + ", ");
                        count++;
                    }
                }
                count2++;
            }
        }
        //System.out.println();
    }

    public Integer[][] getAtomTiles(){

        Set<String> keys=coordinateTileMap.keySet();
        Integer[][] atomTileCoords = new Integer[6][2];
        Tile temp;
        int i=0;
        for (String key:keys){
            temp = coordinateTileMap.get(key);
            if(temp.hasAtom()){
                atomTileCoords[i][0]=temp.c.getX();
                atomTileCoords[i][1]=temp.c.getY();
                i++;
            }
            if(i==6){
                break;
            }
        }
        return atomTileCoords;
    }

    public void updateAtomTiles(){

        Set<String> keys = coordinateTileMap.keySet();
        int i=0;
        for (String key : keys) {
            if(i<24){
                edgeTileArray[i]=coordinateTileMap.get(key);
            }
            else{
                break;
            }
            i++;
        }

    }





}