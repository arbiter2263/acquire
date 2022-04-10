package acquire;

import org.json.JSONObject;

import java.util.*;

public class GameSystem {
    private static GameSystem INSTANCE = new GameSystem();


    public static GameSystem getInstance(){
        if (INSTANCE == null){
            INSTANCE = new GameSystem();
        }
        return INSTANCE;
    }

    /**
     * GameSystem When called should start the first scene which prompts the user for difficulty and number of players through the UI
     * This should then initialize either a hard game (no hints, hides player stocks from each other)
     * or Standard mode, which will offer hints and allow players to see how many stocks other players have
     */
    protected GameSystem() {
        //start UI and ask for new game or load old game
        //if new game get difficulty and number of players
        //UI();
        //TODO

        initializeGame(false, 1);

//        if (!ui.getload()) { //if UI return that this is not a loaded game
//
//            newGame(ui.getIsHardMode, ui.getPlayerCount); //should set up gameboard, the corporations, the players, and the pile of tiles
//
//        } else {initializeGame(saveFile); //use to instantiate a loadGame()but has the game file values
//        }

            //start method should start the game loop which will cycle through turns until someone decides to end game
        //start(); // endGameCriteria will be what kicks the game out of the player turn loops, and move to tally up and print winner


    }


    /**
     *  Method to initialize all of the objects for a new game
     * upon initializing game, system should initialize all require objects
     * to get started: Player objects, Pile object etc.
     */
    private void initializeGame(boolean isHardMode, int numberOfPlayers){ //numberOfPlayers should come in from UI

        //instantiate gameboard and fill it with players
        if (!isHardMode) {

            Gameboard.getInstance().initializeGame(numberOfPlayers);
            //instantiate a pile of tiles
            Pile.getInstance();

            //instantiate the corporations
            CorporationList.getInstance();
        }else{
            //same initialization but without hints and hide player's stocks
        }
    }


    /**
     * UI Requests player count and difficulty, this gets fed into
     * this method, which then requests the names of the players
     * and loops through creating the player objects for the game
     * @param isHardMode if true, hid players stocks etc.
     * @param playerCount Number of players
     */
    private void newGame(boolean isHardMode, int playerCount){
        initializeGame(isHardMode, playerCount);
    }


    /**
     * Initializing a saved game going to need some work on this one
     * @param saveFile
     */
    //TODO
//    private void initializeGame(JSONObject saveFile){
//        loadGame(saveFile);
//    }





    /**
     * At the beginning of a players turn they play a tile
     * this method should check that the tile the player chose can be played
     * @param player  The player who has the current turn
     * @param tile    The tile chosen to be played
     * @return
     */
    boolean playATile(Player player, Tile tile){

        //checks if placement is valid
        if(Gameboard.getInstance().isValidTilePlay(tile)){
            //places tile on board
            //placeTile should also check for expansion, merger etc.
            Gameboard.getInstance().placeTile(player, tile);
            //remove tile from player hand
            player.playTile(tile);



            return true;
        }
        return false;
    }

    /**
     * This method for when a merger happens, stocks are traded
     * at a ratio of 2:1
     * @param player  The current player wanting to trade
     * @param corp1   This is the defunct corp, the player can trade in stocks for corp2
     * @param corp2   This is the remaining super corporation, the player will get 1 stock in this corp
     * @return
     */
    boolean tradeStock(Player player, Corporation corp1, Corporation corp2) {
        player.tradeInStock(corp1, corp2); //we may want to add a parameter here that takes in the amount
                                           //since the player does not have to trade in all the defunct corp stocks
        return true;
    }


    /**
     * Method used during the sell/trade/hold section of a merger
     * @param player The player who is currently in their turn
     * @param corp   The defunct corporation from the merger
     * @param sellAmount the amount the user requests to sell
     * @return
     * UI event handler calls this method feeding it the params
     */
    private boolean sellDefunctStock(Player player, Corporation corp, int sellAmount){
        player.sellDefunctStock(corp, sellAmount);
        for(int i = 0; i < sellAmount; i++){
            corp.stockSold();
        }
        return true;
    }

    /**
     * Method can be used during
     * @param player Current player who is taking their turn
     * @param corp   The corporation the player has chosen to buy stocks from
     * @amount       The amount of stocks the player wants to buy from corp
     *               A player can only purchase up to 3 stocks per turn
     * @return
     * UI event handler calls this method feeding it the params
     */
     boolean purchaseStock(Player player, Corporation corp, int amount){
        for(int i = 0; i < amount; i++){
            player.buyStock(corp.getName());
            corp.stockBought();
        }
        return true;
    }

    /**
     * Method to dump unplayable tiles at the end of players turn
     * before they draw tile(s) to end their turn.  This method should
     * check each of the tiles in the players hand to see if any
     * are unplayable (if they merge 2 safe corporations) and removes
     * them from the players hand.
     * @param player
     * @return
     * This method should be checked after every players turn
     */
    private boolean removeUnplayableTile(Player player){
        for(var tile : player.getHand()){
            if(!Gameboard.getInstance().isValidTilePlay(tile)){ //if is not a valid play
                player.removeTile(tile);
            }
        }
        return true;
    }


    /**
     * Method for when a player draws a tile to end their turn
     * This method will check the size of the players hand, draws a tile which
     * if it is less than 6, then it will draw a tile and add it to the players hand
     * and check again if there are less than 6 tiles. This will also
     * remove the tile from the pile.
      * @param player  The player drawing to end their turn
     * @return
     * UI handler will call this method after user has selected to draw tile
     */
    boolean drawTile(Player player) {
        while(player.getHand().size() < 6) { //Players should always have 6 tiles at the end of their turn
            player.addTile(Pile.getInstance().drawTile());
            //end go next player turn
            }
        return true;
    }

    /**
     * This method should be called after every turn to check
     * for "can game end" criteria.  Note that even if the
     * criteria are met, players do NOT have to end the game
     * Criteria: either 1 corporation has 41 tiles and/or all active corporations are safe
     * end game option might just be an addition button that when clicked activates
     * the end game procedure
     * @return
     */
    private boolean endGameCheck(){
        int safeCounter = 0;

        for(var corp : CorporationList.getInstance().getActiveCorps()) { //cycle through list of active corporations
            if (corp.checkIfSafe()) {
                safeCounter++;
            }
            if (corp.getTileList().size() >= 41) {
                return true; //game can end since there is at least 1 corp 41+ in size present end game option
            }
         }
            if (CorporationList.getInstance().getActiveCorps().size() == safeCounter) {
                //if all active corporations are safe game can end
                return true; //Present end game option
        }
        return false; //Game cannot be ended yet do not present option to end
    }

    /**
     * Method provides end of game steps: Majority and minority
     * shareholders' bonuses are paid out for all active corporations,
     * and all stocks are sold back to the stock market bank at current prices
     *
     */


    private void endGame(){
        TreeMap<Integer, Player> tableOfStockScores = new TreeMap<>();


        //this was what I came up with for as a way to pay out the minority/majority stockholders per
        //corporation.  This grabs a corp from the corplist, then cycles through all of the players,
        //getting their stock value for said corp. the method then adds the stock amount and the player
        //that owns it to a treemap which is sorted by the value of the stock from lowest to highest
        //after it adds each player, it will sell all of their stock in the current corp.
        //after it gets all players and their stock amount added, it pays out the majority owner
        //which should be the last entry in the treemap.  It removes the last entry and then pays out the minority
        //stockholder bonus.  THIS DOES NOT YET TAKE INTO ACCOUNT ANY TIES YET
        for(var corp : CorporationList.getInstance().getActiveCorps()){
            for (var player : Gameboard.getInstance().getPlayers()){
                tableOfStockScores.put(player.getStocks().get(corp), player); //getStocks may need a parameter to get a specific stock
                player.sellDefunctStock(corp, player.getStocks().get(corp)); //get the player's stock amount for the corporation and sell it all.
            }

            //probable methods in corporation
//            majorityPayout(tableOfStockScores.lastEntry().getKey(), tableOfStockScores.lastEntry().getValue());  //first param stock total, second is the player
//            tableOfStockScores.remove(tableOfStockScores.lastEntry().getKey());
//            minorityPayout(tableOfStockScores.lastEntry().getKey(), tableOfStockScores.lastEntry().getValue());
        }
    }


    //TODO SAVE AND LOAD GAME
    //not sure we need a pause game as I'm not sure what would qualify as a paused game in this instance
//    private void pauseGame(){}
//
//    private void saveGame(){}
//
//    private void loadGame(){}


}
