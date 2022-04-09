package acquire;

import java.io.File;
import java.util.LinkedList;

public class Gameboard {
    static private LinkedList<Player> players;
    private LinkedList<Tile> tilesPlayed;
    private Tile[][] board;
    private static final Gameboard INSTANCE = new Gameboard(); // Field to hold singleton instance of class

    /**
     * Private constructor to enforce only one instance
     */
    private Gameboard(){
        this.players = new LinkedList<Player>();
        this.tilesPlayed = new LinkedList<Tile>();
        this.board = new Tile[12][9];

        /*
        A1 A2 A3 ... A9
        B1 B2 B3 ... B9
        C1 C2 C3 ... C9
        D1 D2 D3 ... D9
        E1 E2 E3 ... E9
        F1 F2 F3 ... F9
        G1 G2 G3 ... G9
        H1 H2 H3 ... H9
        I1 I2 I3 ... I9
        J1 J2 J3 ... J9
        K1 K2 K3 ... K9
        L1 L2 L3 ... L9
         */
    }

    /**
     * Method to get a reference to the singleton instance of this class
     * @return  Gameboard  The only instance of this class
     */
    protected static Gameboard getInstance() {
        return INSTANCE;
    }

    /**
     * Method to initialize a game, based on the number of players playing
     * @param numOfPlayers  The number of players
     */
    protected void initializeGame(int numOfPlayers){
        for (int i = 0; i <= numOfPlayers; i++) {
            getInstance().players.add(new Player("Player " + i));
        }
    }

    /**
     * Simple getter for tilesPLayed
     * @return  LinkedList<Tile>  All the tiles that have been played to the board
     */
    protected LinkedList<Tile> getTilesPlayed() {
        return tilesPlayed;
    }

    /**
     * Simple getter for board
     * @return  Tile[][]  An array representation of the gameboard
     */
    protected Tile[][] getBoard() {
        return this.board;
    }

    /**
     * Method that checks if a player is actually able to play a tile to the board
     * @param tile  The tile trying to be played
     * @return  bool  True if this tile is a valid move; False otherwise
     */
    protected boolean isValidTilePlay(Tile tile) {
        int[] rowColumn = getRowColumnTile(tile);
        LinkedList<Integer> indexes = new LinkedList<Integer>();
        for (int i = 0; i < CorporationList.getInstance().getActiveCorps().size(); i++) {
            if (doesTileTouchCorp(tile, CorporationList.getInstance().getActiveCorps().get(i))) {
                indexes.add(i);
            }
        }
        if (indexes.size() > 1) {
            int safeCorpCount = 0;
            for (int i : indexes) {
                if ( CorporationList.getInstance().getActiveCorps().get(i).checkIfSafe() ) {
                    safeCorpCount++;
                }
            }
            if (safeCorpCount > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method that attempts to play a Player's tile
     * @param player  The player playing the tile
     * @param tile  The tile being played
     * @return  bool  True if the tile is played successfully; False if it can't be played
     */
    protected boolean placeTile(Player player, Tile tile) {
        if(isValidTilePlay(tile)) {
            LinkedList<Integer> indexes = new LinkedList<Integer>();
            for (int i = 0; i < CorporationList.getInstance().getActiveCorps().size(); i++) {
                if (doesTileTouchCorp(tile, CorporationList.getInstance().getActiveCorps().get(i))) {
                    indexes.add(i);
                }
            }
            if (indexes.size() > 1) {
                merger(tile, indexes);
            } else if (indexes.size() == 1){
                expandCorp(tile, indexes.get(0));
            } else if (isTouchingPlacedTile(tile)[0] > -1){
                makeNewCorp(tile, isTouchingPlacedTile(tile));
            } else {
                //just place tile
            }
            addTileToBoard(tile);
            player.playTile(tile);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that checks if a tile is touching the side of any tile within a given corporation
     * @param tile  The tile being played
     * @param corporation  The corporation being checked if it is adjacent to the tile
     * @return  bool  True if the tile is touching the corporation; False otherwise
     */
    private boolean doesTileTouchCorp(Tile tile, Corporation corporation) {
        int[] rowColumn = getRowColumnTile(tile);
        LinkedList<Tile> corpTiles = corporation.getTileList();

        for (Tile tileX : corpTiles) {
            int[] tileXRowColumn = getRowColumnTile(tileX);
            if (tileXRowColumn[0] == rowColumn[0] && ( tileXRowColumn[1] == rowColumn[1]+1 || tileXRowColumn[1] == rowColumn[1]-1) ) {
                return true;
            } else if (tileXRowColumn[1] == rowColumn[1] && ( tileXRowColumn[0] == rowColumn[0]+1 || tileXRowColumn[0] == rowColumn[0]-1) ) {
                return true;
            }
        }
        return false;
    }

    private void merger(Tile tile, LinkedList<Integer> indexes){
        //Will need to cooperate with the merger screen
        // needs user input
    }

    /**
     * Method that expands a corporation with a played tile
     * @param tile  The tile being played
     * @param index  The index of the corporation being expanded
     */
    private void expandCorp(Tile tile, int index){
        CorporationList.getInstance().getActiveCorps().get(index).addTile(tile);
    }

    /**
     * Method that checks if a placed tile is touching a lone tile on the board
     * @param tile  The tile being played
     * @return  int[]  Contains the rowColumn location of the touching tile, if found; int[] = {-1, -1} if none found
     */
    private int[] isTouchingPlacedTile(Tile tile) {
        int[] rowColumn = getRowColumnTile(tile);

        if (board[rowColumn[0]][rowColumn[1]+1] != null) {
            int[] touchingTileRowColumn = {rowColumn[0], rowColumn[1]+1};
            return touchingTileRowColumn;
        } else if (board[rowColumn[0]][rowColumn[1]-1] != null) {
            int[] touchingTileRowColumn = {rowColumn[0], rowColumn[1]-1};
            return touchingTileRowColumn;
        } else if (board[rowColumn[1]][rowColumn[0]+1] != null) {
            int[] touchingTileRowColumn = {rowColumn[1], rowColumn[0]+1};
            return touchingTileRowColumn;
        } else if (board[rowColumn[1]][rowColumn[0]-1] != null) {
            int[] touchingTileRowColumn = {rowColumn[1], rowColumn[0]-1};
            return touchingTileRowColumn;
        } else {
            int [] badArray = {-1, -1};
            return badArray;
        }
    }

    private void makeNewCorp(Tile tile, int[] rowColumnTile2) {
        //Will need to cooperate with the make new corporation screen
        // needs user input
    }

    /**
     * Method that adds a tile to the board
     * @param tile  The tile to be played down
     */
    private void addTileToBoard(Tile tile) {
        int[] rowColumn = getRowColumnTile(tile);
        board[rowColumn[0]][rowColumn[1]] = tile;
    }

    /**
     * Method that gets the row and column values of a tile
     * @param tile  The tile to get evaluate
     * @return  int[]  The first entry = the row; the second entry = column
     */
    private int[] getRowColumnTile(Tile tile) {
        char letter = tile.getSpace().charAt(0);
        int row = -1;
        switch (letter) {
            case 'A' :  row = 0; break;
            case 'B' :  row = 1; break;
            case 'C' :  row = 2; break;
            case 'D' :  row = 3; break;
            case 'E' :  row = 4; break;
            case 'F' :  row = 5; break;
            case 'G' :  row = 6; break;
            case 'H' :  row = 7; break;
            case 'I' :  row = 8; break;
            case 'J' :  row = 9; break;
            case 'K' :  row = 10; break;
            case 'L' :  row = 11; break;
        }
        int column = (int)tile.getSpace().charAt(1);
        int[] rowColumn = {row, column};
        return rowColumn;

        /*
        A1 A2 A3 ... A9
        B1 B2 B3 ... B9
        C1 C2 C3 ... C9
        D1 D2 D3 ... D9
        E1 E2 E3 ... E9
        F1 F2 F3 ... F9
        G1 G2 G3 ... G9
        H1 H2 H3 ... H9
        I1 I2 I3 ... I9
        J1 J2 J3 ... J9
        K1 K2 K3 ... K9
        L1 L2 L3 ... L9
         */
    }

    public static void main(String[] args) {
        Player steve = new Player("Steve");
        players.add(steve);
        Player player = players.getFirst();
        GameboardScreen.main(player);
    }
}
