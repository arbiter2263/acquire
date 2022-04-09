package acquire;

import java.util.LinkedList;

public class Gameboard {
    private LinkedList<Player> players;
    private LinkedList<Tile> tilesPlayed;
    protected Gameboard(){
        //Make this a singleton
    }
    protected void initializeGame(int numOfPlayers){}
    protected boolean isValidTilePlay(Player player, Tile tile) {
        return true;
    }
    protected boolean placeTile(Player player, Tile tile) {
        return true;
    }
    protected LinkedList<Tile> getTilesPlayed() {
        return tilesPlayed;
    }
    protected boolean checkMerger() {
        return true;
    }
    protected boolean checkNewCorporation() {
        return true;
    }
    protected boolean checkExpandCorporation() {
        return true;
    }
}
