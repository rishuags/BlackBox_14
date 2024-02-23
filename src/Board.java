import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Set;

public class Board {
    Map<String, Tile> coordinateTileMap = new LinkedHashMap<>();

    public void GenerateAtoms (){
        Random rand = new Random();
        int AtomIndexes[] = new int[6];
        for (int i=0;i<6;i++){
            AtomIndexes[i]= rand.nextInt(60);
            for (int j=0;j<i;j++) {
                if (AtomIndexes[j] == AtomIndexes[i]) {
                    i--;
                    break;
                }
            }
        }

        Set<String> keys=coordinateTileMap.keySet();
        int count=0;
        for (String key:keys){
                coordinateTileMap.get(key).setNoAtom();
        }

        for (String key:keys){
            if(count==AtomIndexes[count]){
                coordinateTileMap.get(key).setAtom();
                count++;
            }
            if(count==6){
                break;
            }
        }
    }

    public Integer[][] getAtomTiles(){

        Set<String> keys=coordinateTileMap.keySet();
        Integer[][] atomTileCoords = new Integer[6][2];
        int i=0,j;
        for (String key:keys){
            j=0;
            if(coordinateTileMap.get(key).hasAtom()){
                if(key.substring(0,1)=="-"){
                    atomTileCoords[i][0]=-Integer.parseInt(key.substring(1,2));
                    j=2;
                }
                else{
                    atomTileCoords[i][0]=Integer.parseInt(key.substring(0,1));
                    j=1;
                }
                if(key.substring(j,j+1)=="-"){
                    atomTileCoords[i][1]=-Integer.parseInt(key.substring(j+1,j+2));
                }
                else{
                    atomTileCoords[i][1]=Integer.parseInt(key.substring(j,j+1));
                }
                i++;
            }
            if(i==6){
                break;
            }
        }

        return atomTileCoords;
    }




}
