package acquire;

import java.util.LinkedList;

public class Pile {
    private LinkedList<Tile> availableTiles;
    protected Pile(){
        //initialize pile
        //make this class a singleton
    }
    protected void destroyTile(Tile tile){}
    protected Tile drawTile() {
        return availableTiles.remove();
    }
}
