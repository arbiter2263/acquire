package acquire;

import org.json.JSONObject;

public class GameSystem {
    protected GameSystem(){
        //Make singleton
    }
    private void initializeGame(){}
    private void initializeGame(JSONObject saveFile){}
    private boolean playATile(Player player, Tile tile){
        return true;
    }
    private boolean purchaseStock(Player player, String stockName){
        return true;
    }
    private boolean drawTile() {
        return true;
    }
    private void pauseGame(){}
    private void saveGame(){}
    private void loadGame(){}
    private void newGame(boolean isHardMode, int playerCount){}
}
