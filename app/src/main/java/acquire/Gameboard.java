/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Type;
import java.util.LinkedList;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EqualsAndHashCode @ToString
public class Gameboard {
    @Getter private LinkedList<Player> players;
    @Getter private LinkedList<Tile> tilesPlayed;
    @Getter private Tile[][] board;
    @VisibleForTesting
    private static Gameboard INSTANCE = null; // Field to hold singleton instance of class
    @Setter private static String corpName; //Global var for merger call to MergerTieScreen
    private static Logger LOGGER = LoggerFactory.getLogger(Gameboard.class);
    /**
     * Private constructor to enforce only one instance
     */
    private Gameboard(){
        this.players = new LinkedList<Player>();
        this.tilesPlayed = new LinkedList<Tile>();
        this.board = new Tile[12][9];
    }

    /**
     * Method to get a reference to the singleton instance of this class
     * @return  Gameboard  The only instance of this class
     */
    protected static Gameboard getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Gameboard();
        }
        return INSTANCE;
    }

    /**
     * Method to initialize a game, based on the number of players playing
     * @param numOfPlayers  The number of players
     */
    protected void initializeGame(int numOfPlayers){
        for (int i = 0; i < numOfPlayers; i++) {
            getInstance().players.add(new Player("Player " + i));
        }
        LOGGER.info("Game initialized for {} players", numOfPlayers);
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
    protected boolean placeTile(Player player, Tile tile, Stage primaryStage) {
        if(isValidTilePlay(tile)) {
            LinkedList<Integer> indexes = new LinkedList<Integer>();
            if (CorporationList.getInstance().getActiveCorps().size() > 0) {
                for (int i = 0; i < CorporationList.getInstance().getActiveCorps().size(); i++) {
                    if (doesTileTouchCorp(tile, CorporationList.getInstance().getActiveCorps().get(i))) {
                        indexes.add(i);
                    }
                }
            }
            if (indexes.size() > 1) {
                merger(player, tile, indexes, primaryStage);
            } else if (indexes.size() == 1){
                expandCorp(tile, indexes.get(0));
            } else if (isTouchingPlacedTile(tile)[0] > -1){
                makeNewCorp(player, tile, isTouchingPlacedTile(tile), primaryStage);
            } else {
                //just place tile
            }
            addTileToBoard(tile);
            LOGGER.info("Tile {} was added to the gameboard", tile.getSpace());
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

    /**
     * Method to merger corporations together
     * @param tile  The tile that started the merger
     * @param indexes  The indexes for the corporations involved
     */
    private void merger(Player player, Tile tile, LinkedList<Integer> indexes, Stage primaryStage){
        //Will need to cooperate with the merger screen
        // needs user input
        Corporation biggestCorp = new Corporation("fakeCorp");
        for (int index : indexes) {
            Corporation corp = CorporationList.getInstance().getActiveCorps().get(index);
            if (corp.getTileList().size() == biggestCorp.getTileList().size()) {
                //We have a tie
                MergerTieScreen tieScreen = new MergerTieScreen();
                try {
                    Stage test = new Stage();
                    primaryStage.setScene(tieScreen.getScene(test, player, biggestCorp.getName(), corp.getName()));
                    test.showAndWait();
                } catch (FileNotFoundException e) {
                    //it didn't work
                }
                biggestCorp = CorporationList.getInstance().getCorporation(corpName);
                corpName = null;
            } else if (corp.getTileList().size() > biggestCorp.getTileList().size()) {
                biggestCorp = corp;
            }
        }
        for (int index : indexes) {
            if (CorporationList.getInstance().getActiveCorps().get(index) != biggestCorp) {
                for (Tile t : CorporationList.getInstance().getActiveCorps().get(index).getTileList()) {
                    biggestCorp.addTile(t);
                }
            }
        }
        biggestCorp.addTile(tile);
        for (int index : indexes) {
            Corporation c = CorporationList.getInstance().getActiveCorps().get(index);
            CorporationList.getInstance().deactivateCorp(c);
        }
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

        if (rowColumn[0] == 0 && rowColumn[1] == 0) { //top-left corner
            if (board[rowColumn[0]][rowColumn[1]+1] != null) {
                int[] touchingTileRowColumn = {rowColumn[0], rowColumn[1]+1};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]+1][rowColumn[0]] != null) {
                int[] touchingTileRowColumn = {rowColumn[0]+1, rowColumn[1]};
                return touchingTileRowColumn;
            } else {
                int [] badArray = {-1, -1};
                return badArray;
            }
        } else if (rowColumn[0] == 11 && rowColumn[1] == 8) { //bottom-right corner
            if (board[rowColumn[0]][rowColumn[1]-1] != null) {
                int[] touchingTileRowColumn = {rowColumn[0], rowColumn[1]-1};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]-1][rowColumn[1]] != null) {
                int[] touchingTileRowColumn = {rowColumn[0]-1, rowColumn[1]};
                return touchingTileRowColumn;
            } else {
                int [] badArray = {-1, -1};
                return badArray;
            }
        } else if (rowColumn[0] == 0 && rowColumn[1] == 8) { //top-right corner
            if (board[rowColumn[0]][rowColumn[1]-1] != null) {
                int[] touchingTileRowColumn = {rowColumn[0], rowColumn[1]-1};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]+1][rowColumn[1]] != null) {
                int[] touchingTileRowColumn = {rowColumn[0]+1, rowColumn[1]};
                return touchingTileRowColumn;
            } else {
                int [] badArray = {-1, -1};
                return badArray;
            }
        } else if (rowColumn[0] == 11 && rowColumn[1] == 0) { //bottom-left corner
            if (board[rowColumn[0]][rowColumn[1]+1] != null) {
                int[] touchingTileRowColumn = {rowColumn[0], rowColumn[1]+1};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]-1][rowColumn[1]] != null) {
                int[] touchingTileRowColumn = {rowColumn[0]-1, rowColumn[1]};
                return touchingTileRowColumn;
            } else {
                int [] badArray = {-1, -1};
                return badArray;
            }
        } else if (rowColumn[0] == 0) { //top edge
            if (board[rowColumn[0]][rowColumn[1]+1] != null) {
                int[] touchingTileRowColumn = {rowColumn[0], rowColumn[1]+1};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]][rowColumn[1]-1] != null) {
                int[] touchingTileRowColumn = {rowColumn[0], rowColumn[1]-1};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]+1][rowColumn[1]] != null) {
                int[] touchingTileRowColumn = {rowColumn[0]+1, rowColumn[1]};
                return touchingTileRowColumn;
            } else {
                int [] badArray = {-1, -1};
                return badArray;
            }
        } else if (rowColumn[0] == 11) { //bottom edge
            if (board[rowColumn[0]][rowColumn[1]+1] != null) {
                int[] touchingTileRowColumn = {rowColumn[0], rowColumn[1]+1};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]][rowColumn[1]-1] != null) {
                int[] touchingTileRowColumn = {rowColumn[0], rowColumn[1]-1};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]-1][rowColumn[1]] != null) {
                int[] touchingTileRowColumn = {rowColumn[0]-1, rowColumn[1]};
                return touchingTileRowColumn;
            } else {
                int [] badArray = {-1, -1};
                return badArray;
            }
        } else if (rowColumn[1] == 0) { //left edge
            if (board[rowColumn[0]][rowColumn[1]+1] != null) {
                int[] touchingTileRowColumn = {rowColumn[0], rowColumn[1]+1};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]+1][rowColumn[1]] != null) {
                int[] touchingTileRowColumn = {rowColumn[0]+1, rowColumn[1]};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]-1][rowColumn[1]] != null) {
                int[] touchingTileRowColumn = {rowColumn[0]-1, rowColumn[1]};
                return touchingTileRowColumn;
            } else {
                int [] badArray = {-1, -1};
                return badArray;
            }
        } else if (rowColumn[1] == 8) { //right edge
            if (board[rowColumn[0]][rowColumn[1]-1] != null) {
                int[] touchingTileRowColumn = {rowColumn[0], rowColumn[1]-1};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]+1][rowColumn[1]] != null) {
                int[] touchingTileRowColumn = {rowColumn[0]+1, rowColumn[1]};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]-1][rowColumn[1]] != null) {
                int[] touchingTileRowColumn = {rowColumn[0]-1, rowColumn[1]};
                return touchingTileRowColumn;
            } else {
                int [] badArray = {-1, -1};
                return badArray;
            }
        } else { //not on an edge
            if (board[rowColumn[0]][rowColumn[1]+1] != null) {
                int[] touchingTileRowColumn = {rowColumn[0], rowColumn[1]+1};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]][rowColumn[1]-1] != null) {
                int[] touchingTileRowColumn = {rowColumn[0], rowColumn[1]-1};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]+1][rowColumn[1]] != null) {
                int[] touchingTileRowColumn = {rowColumn[0]+1, rowColumn[1]};
                return touchingTileRowColumn;
            } else if (board[rowColumn[0]-1][rowColumn[1]] != null) {
                int[] touchingTileRowColumn = {rowColumn[0]-1, rowColumn[1]};
                return touchingTileRowColumn;
            } else {
                int [] badArray = {-1, -1};
                return badArray;
            }
        }
    }

    /**
     * Method to make a new corporation
     * @param tile  The tile that started the merger
     * @param rowColumnTile2  The row and column location of the previously placed tile
     */
    private void makeNewCorp(Player player, Tile tile, int[] rowColumnTile2, Stage primaryStage) {
        //Will need to cooperate with the make new corporation screen
        // needs user input
        MakeCorporationScreen makeCorpScreen = new MakeCorporationScreen();
        Stage test = new Stage();
        test.setScene(makeCorpScreen.getScene(test, player));
        test.showAndWait();
        Corporation corp = CorporationList.getInstance().getCorporation(corpName);
        CorporationList.getInstance().activateCorp(corp);
        corp.addTile(tile);
        corp.addTile(Gameboard.getInstance().board[rowColumnTile2[0]][rowColumnTile2[1]]);
        LOGGER.info("New corporation was formed");
        player.addFoundersStock(corp);
    }

    /**
     * Method that adds a tile to the board
     * @param tile  The tile to be played down
     */
    private void addTileToBoard(Tile tile) {
        int[] rowColumn = getRowColumnTile(tile);
        board[rowColumn[0]][rowColumn[1]] = tile;
        tilesPlayed.add(tile);
    }

    /**
     * Method that gets the row and column values of a tile
     * @param tile  The tile to get evaluate
     * @return  int[]  The first entry = the row; the second entry = column
     */
    private int[] getRowColumnTile(Tile tile) {
        char letter = tile.getSpace().charAt(1);
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
        int column = Character.getNumericValue(tile.getSpace().charAt(0)) - 1;
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

    /**
     * Resets all fields to a new game value
     */
    protected void newGame(){
        Gameboard.getInstance().tilesPlayed = new LinkedList<Tile>();
        Gameboard.getInstance().players = new LinkedList<Player>();
        Gameboard.getInstance().board = new Tile[12][9];
    }

    /**
     * Write out the current gameboard fields to be saved
     * for later
     * @throws IOException
     */
    protected void saveGameboard() throws IOException {
        Writer writer = new FileWriter("acquire/app/jsonsave/gameboard.json", false);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try{
            gson.toJson(Gameboard.getInstance(), writer); //Not appending to keep file fresh on new save
        }catch(Exception IOE){
            LOGGER.warn("Unable to write game objects to file to save.");
        }
        writer.flush();
        writer.close();
        LOGGER.info("Game was saved");
    }


    /**
     * loadGameboard will load a previous instance of
     * the gameboard including players
     * @throws FileNotFoundException
     */
    protected void loadGameboard() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("acquire/app/jsonsave/gameboard.json");
        Gameboard newGameboard = gson.fromJson(reader, (Type) Gameboard.class);
        Gameboard.getInstance().board = newGameboard.board;
        Gameboard.getInstance().tilesPlayed = newGameboard.tilesPlayed;
        Gameboard.getInstance().players = newGameboard.players;

    }

}
