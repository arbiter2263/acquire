/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import lombok.*;

@EqualsAndHashCode @ToString
public class GameSystem {
    private static GameSystem INSTANCE = null;
    @Getter private ArrayList<Player> playerList = new ArrayList<>();
    @Getter private ArrayList<Player> mergerPlayerOrder = new ArrayList<>();
    @Getter private int turnCounter= 0;
    @Getter @Setter private boolean isHardMode = false;
    @Getter @Setter private int numOfPlayers = 0;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameSystem.class);

    /**
     * GameSystem When called should start the first scene which prompts the user for difficulty and number of players through the UI
     * This should then initialize either a hard game (no hints, hides player stocks from each other)
     * or Standard mode, which will offer hints and allow players to see how many stocks other players have
     */
    protected GameSystem() {

       // initializeGame(isHardMode, numOfPlayers);
    }

    protected static GameSystem getInstance(){
        if (INSTANCE == null){
            INSTANCE = new GameSystem();
        }
        return INSTANCE;
    }

    /**
     * Method to set up order for game play
     * @param player A player object
     */
    protected void playOrder(Player player){
        playerList.add(player);
    }

    /**
     * Method starts at first spot in the list of players
     * returns them, increments the counter. Once the counter
     * is equal to the number of players (lastPlayer) then it
     * returns the finalPlayer and resets counter to 0 to start
     * at the beginning again.
     * @return returns a player
     */
    protected Player playerTurn(){
        int lastIndex = playerList.size()-1;
        int currentPlayer = turnCounter;

        if(turnCounter < lastIndex) {
            turnCounter++;
            return playerList.get(currentPlayer);
        }else if(turnCounter % lastIndex == 0){
            turnCounter = 0;
            return playerList.get(lastIndex);
        }
        return null;
    }

    /**
     *  Method to initialize all the objects for a new game
     * upon initializing game, system should initialize all require objects
     * to get started: Player objects, Pile object etc.
     */
    protected void initializeGame(boolean isHardMode, int numberOfPlayers){ //numberOfPlayers should come in from UI
        newGame();
        setNumOfPlayers(numberOfPlayers);
        //instantiate the game board and fill it with players
        if (!isHardMode) {
            Gameboard.getInstance().initializeGame(numberOfPlayers);
            //instantiate a pile of tiles
            Pile.getInstance();

            //instantiate the corporations
            CorporationList.getInstance();
            LOGGER.info("Game initialized in easy mode");
        }else{
            //same initialization but without hints and hide player's stocks
            Gameboard.getInstance().initializeGame(numberOfPlayers);
            //instantiate a pile of tiles
            Pile.getInstance();

            //instantiate the corporations
            CorporationList.getInstance();
            LOGGER.info("Game initialized in hard mode");
        }
    }


    /**
     * Resets all values to 0 or null for a new game
     */
    private void newGame() {
        playerList = new ArrayList<>();
        isHardMode = false;
        turnCounter = 0;
        Gameboard.getInstance().newGame();
        CorporationList.getInstance().newGame();
        Pile.getInstance().newGame();
        mergerPlayerOrder = new ArrayList<>();
    }

    /**
     * At the beginning of a players turn they play a tile
     * this method should check that the tile the player chose can be played
     * @param player  The player who has the current turn
     * @param tile    The tile chosen to be played
     * @return return true if successfully played tile
     */
    protected boolean playATile(Player player, Tile tile, Stage stage){

        //checks if placement is valid
        if(Gameboard.getInstance().isValidTilePlay(tile)){
            //places tile on board
            //placeTile should also check for expansion, merger etc.
            Gameboard.getInstance().placeTile(player, tile, stage);
            //remove tile from player hand
            player.playTile(tile);
            LOGGER.info("A tile was played");

            return true;
        }
        return false;
    }

    /**
     * This method for when a merger happens, stocks are traded
     * at a ratio of 2:1
     * USER WILL CURRENTLY ONLY BE ABLE TO TRADE IN ALL STOCKS
     * @param player  The current player wanting to trade
     * @param corp1   This is the defunct corp, the player can trade in stocks for corp2
     * @param corp2   This is the remaining super corporation, the player will get 1 stock in this corp
     * @return return true if stock is traded in
     */
    protected boolean tradeStock(Player player, Corporation corp1, Corporation corp2, int amount) {
        player.tradeInStock(corp1, corp2, amount);
        int count = 0;
        while(count < amount){

            //using stockSold, this will not update any player stockCounts
            corp1.stockSold();
            count++;

            if(count % 2 ==0){
                //using stockBought, this will not update and player stockCounts
                corp2.stockBought();
            }
        }   LOGGER.info("Player {} traded {} stocks from {} for {} stocks in {} .", player.getName(), count, corp1.getName(), count/2, corp2.getName());

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
    protected boolean sellDefunctStock(Player player, Corporation corp, int sellAmount){
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
     * @param amount The amount of stocks the player wants to buy from corp
     *               A player can only purchase up to 3 stocks per turn
     * @return
     * UI event handler calls this method feeding it the params
     */
    protected boolean purchaseStock(Player player, Corporation corp, int amount){
        for(int i = 0; i < amount; i++){

            player.buyStock(corp.getName());
        }
        return true;
    }

    /**
     * Method to dump unplayable tiles at the end of player's turn
     * before they draw tile(s) to end their turn.  This method should
     * check each of the tiles in the players hand to see if any
     * are unplayable (if they merge 2 safe corporations) and removes
     * them from the players hand.
     * @param player the player currently taking their turn
     * @return
     * This method should be checked after every players turn
     */
    protected boolean removeUnplayableTile(Player player){
        for(Tile tile : player.getHand()){
            if(!Gameboard.getInstance().isValidTilePlay(tile)){ //if is not a valid play
                player.removeTile(tile);
            }
        }
        return true;
    }


    /**
     * Method for when a player draws a tile to end their turn
     * This method will check the size of the players hand, draws a tile
     * if it is less than 6, then it will draw a tile and add it to the players hand
     * and check again if there are less than 6 tiles. This will also
     * remove the tile from the pile.
     * @param player  The player drawing to end their turn
     * @return returns true if successfully draws tile from pile
     * UI handler will call this method after user has selected to draw tile
     */
    protected boolean drawTile(Player player) {
        while(player.getHand().size() < 6) { //Players should always have 6 tiles at the end of their turn
            if(Pile.getInstance().size() > 0){
                player.addTile(Pile.getInstance().drawTile());

            }else if(Pile.getInstance().size()==0){
                //no more tiles left to pull, probably close to the end of the game
                break;
            }
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
     * @return returns true if endgame criteria is met else false
     */
    protected boolean endGameCheck(){

        if(CorporationList.getInstance().getActiveCorps().size() < 1) {
            return false;
        }else {
            int safeCounter = 0;
            for (var corp : CorporationList.getInstance().getActiveCorps()) { //cycle through list of active corporations
                if (corp.checkIfSafe()) {
                    safeCounter++;
                }
                if (corp.getTileList().size() >= 41) {
                    return true; //game can end since there is at least 1 corp 41+ in size present end game option
                }
            }
            if ((safeCounter >= 2) && (CorporationList.getInstance().getActiveCorps().size() == safeCounter)) {
                //if all active corporations are safe game can end
                return true; //Present end game option
            }
        }
        return false; //Game cannot be ended yet do not present option to end
    }

    /**
     * Method that ends the game by selling all players' stocks and ordering them according to who has the most money
     * @return  LinkedList<Player>  The ordered list of players
     */
    protected LinkedList<Player> endGame(){
        for (Corporation activeCorp : CorporationList.getInstance().getActiveCorps()) {
            HashMap<Player, Integer> stockCounts = new HashMap<>();
            for (Player player : Gameboard.getInstance().getPlayers()) {
                stockCounts.put(player, player.getStocks().get(activeCorp));
                player.sellFullPricedStock(activeCorp, player.getStocks().get(activeCorp));
            }
            giveMajorityMinorityStockHolder(stockCounts, activeCorp);
        }
        for (Corporation inactiveCorp : CorporationList.getInstance().getInactiveCorps()) {
            HashMap<Player, Integer> stockCounts = new HashMap<>();
            for (Player player : Gameboard.getInstance().getPlayers()) {
                stockCounts.put(player, player.getStocks().get(inactiveCorp));
                player.sellFullPricedStock(inactiveCorp, player.getStocks().get(inactiveCorp));
            }
            giveMajorityMinorityStockHolder(stockCounts, inactiveCorp);
        }
        //Player winningOrder[] = new Player[Gameboard.getInstance().getPlayers().size()];
        LinkedList<Player> winningOrder = new LinkedList<>();
        for (Player player : Gameboard.getInstance().getPlayers()) {
            winningOrder.add(player);
        }
        winningOrder.sort(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                if ( o1.getMoney() > o2.getMoney() ) {
                    return 1;
                } else if (o1.getMoney() == o2.getMoney()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        return winningOrder;
    }

    /**
     * Method that determines the majority and minority stockholders in a corporation and gives them their bonuses
     * @param stockCounts  The hashmap of players and their stock counts in a corporation
     * @param corp  The corporation that the stock belongs to
     */
    private void giveMajorityMinorityStockHolder(HashMap<Player, Integer> stockCounts, Corporation corp) {
        Player majorityHolder = null;
        Player minorityHolder = null;
        boolean tie = false;
        for (Player player : Gameboard.getInstance().getPlayers()) {
            if ( (majorityHolder == null) || stockCounts.get(majorityHolder) < stockCounts.get(player) ) {
                minorityHolder = majorityHolder;

                majorityHolder = player;
            }else if (minorityHolder == null || stockCounts.get(minorityHolder) < stockCounts.get(player) ) {
                minorityHolder = player;
            }
        }
        //tie pays out half of the combined payouts
        if(stockCounts.get(minorityHolder) == stockCounts.get(majorityHolder)){
            majorityHolder.giveBonusMoney( ( (corp.getStockPrice()* 10 + corp.getStockPrice() * 5) ) /2 );
            minorityHolder.giveBonusMoney( ( (corp.getStockPrice()* 10 + corp.getStockPrice() * 5) ) /2 );
        }else {
            majorityHolder.giveBonusMoney(corp.getStockPrice() * 10);
            minorityHolder.giveBonusMoney(corp.getStockPrice() * 5);
        }
    }

    /**
     * Write out the current gamesystem fields to be saved
     * for later also calls all other save methods to be written
     * to be used later
     * @throws IOException
     */
    protected void saveGameSystem() throws IOException {
        Writer writer = new FileWriter("acquire/app/jsonsave/gamesystem.json", false);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        LinkedList<Player> copiedPlayers = Gameboard.getInstance().getPlayers();
        try{

            gson.toJson( GameSystem.getInstance(), writer); //Not appending to keep file fresh on new save

            Pile.getInstance().savePile();
            CorporationList.getInstance().saveCorpList();
            Gameboard.getInstance().saveGameboard();
        }catch(Exception IOE){
            LOGGER.warn("Unable to write game objects to file to save.");
        }
        writer.flush();
        writer.close();
        LOGGER.info("Game was saved");
    }


    /**
     * loadGameSystem will load a previous instance of
     * the gameSystem as well as call the load methods for
     * each of the other required classes for the game
     * @throws FileNotFoundException
     */
    protected void loadGameSystem() throws FileNotFoundException {

        Pile.getInstance().loadPile();
        CorporationList.getInstance().loadCorpList();
        GameSystem.getInstance().loadGameSystemMethod();
        Gameboard.getInstance().loadGameboard();



    }
    protected void loadGameSystemMethod() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("acquire/app/jsonsave/gamesystem.json");
        GameSystem newGameSystem = gson.fromJson(reader,  GameSystem.class);
        isHardMode = newGameSystem.isHardMode;
        GameSystem.getInstance().numOfPlayers = newGameSystem.numOfPlayers;
        GameSystem.getInstance().playerList = newGameSystem.playerList;
        GameSystem.getInstance().turnCounter = newGameSystem.turnCounter;
        GameSystem.getInstance().mergerPlayerOrder = newGameSystem.mergerPlayerOrder;
    }
}
