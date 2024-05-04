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

    /**
     * Function to add randomly allocated atoms to the board and clear any previous ones
     */
    public void GenerateAtoms () {

        //generate random indexes of the tile set to generate atoms
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
        Set<String> keys = coordinateTileMap.keySet();
        int count = 0;
        while(count<5){
            count = 0;
            int count2 = 0;
            //set all tiles to no atom status
            for (String key : keys) {
                coordinateTileMap.get(key).setNoAtom();
            }

            //set randomly selected tiles to has atom status
            for (String key : keys) {
                for (int i = 0; i < 6; i++) {
                    if (count2 == AtomIndexes[i]) {
                        coordinateTileMap.get(key).setAtom();
                        count++;
                    }
                }
                count2++;
            }
        }
    }
    /**
     * Function to get a 2d array with the (backend) coordinates of the atoms
     */
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
    /**
     * Function to take record of the tiles at the edge of the board
     */
    public void setEdgeTiles(){

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
