/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EqualsAndHashCode @ToString
public class Player {
    @NonNull @Getter @Setter private String name;
    @Getter private int money;
    @Getter private LinkedList<Tile> hand;
    @NonNull @Getter private Hashtable<Corporation, Integer> stocks = new Hashtable<Corporation, Integer>();
    private static Logger LOGGER = LoggerFactory.getLogger(Player.class);

    /**
     * Constructor for Player
     * @param name  The name of the player being created
     */
    protected Player(String name) {
        this.name = name;
        this.money = 6000;
        this.hand = new LinkedList<Tile>();
        this.stocks = new Hashtable<>();
        // Add inactive corporations
        for (Corporation corp : CorporationList.getInstance().getInactiveCorps()) {
            this.stocks.put(corp, 0);
        }
        // Add active corporations -- should be an empty list ; future proofing
        for (Corporation corp : CorporationList.getInstance().getActiveCorps()) {
            this.stocks.put(corp, 0);
        }
    }

    /**
     * Method to initialize the hint class and allow for hints to be fetched
     * @return  An instance of the Hint class
     */
    protected Hint getHint(){
        return new Hint();
    }

    /**
     * Method to have this player buy a stock in a corporation
     * @param stockName  The name of the stock/corporation to buy from
     * @return  True if the player can afford the stock and purchases successfully; False if they can not afford it
     * @throws NoSuchElementException  If the corporation specified is not activated
     */
    protected boolean buyStock(String stockName) throws NoSuchElementException {
        if (CorporationList.getInstance().checkStatus(stockName)) {
            Corporation corp = CorporationList.getInstance().getCorporation(stockName);
            int cost = corp.getStockPrice();
            if (this.money >= cost) {
                money -= cost;
                corp.stockBought();
                try {
                    int oldStockCount = this.stocks.get(corp);
                    this.stocks.replace(corp, (oldStockCount + 1));
                } catch (NullPointerException ignored) {
                    this.stocks.replace(corp, 1);
                }
                LOGGER.info("Player {} bought stock in corporation: {}", this, stockName);
                return true;
            } else {
                return false;
            }
        } else {
            throw new NoSuchElementException("Corporation " + stockName + " is not active, so stock can not be bought.");
        }
    }

    /**
     * Method that allows a player to trade stock in a defunct corporation for stock in the surviving corporation;
     * Rate of 2 defunct to 1 surviving
     * @param defunctCorp  The corporation that is going defunct from the merger
     * @param survivingCorp  The corporation that is surviving the merger
     * @throws NoSuchElementException  If the player has no stock in the defunct corporation
     */
    protected void tradeInStock(Corporation defunctCorp, Corporation survivingCorp, int amount) throws NoSuchElementException {
        int oldCount = this.stocks.get(defunctCorp);
        if (oldCount == 0) {
            throw new NoSuchElementException("Player " + this.name + " has no stock in company " + defunctCorp);
        } else{
            int newCountDefunct = oldCount - amount;
            int newStockCount = (int) (amount / 2);
            if (amount % 2 > 0) {
                sellDefunctStock(defunctCorp, 1);
            }
            this.stocks.replace(defunctCorp, newCountDefunct);
            this.stocks.replace(survivingCorp, newStockCount);
        }

    }

    /**
     * Method that allows a player to sell stock from a corporation that has gone defunct
     * @param defunctCorp  The corporation that has gone defunct from a merger
     * @param stockCount  The amount of stock the player wants to sell
     * @throws IndexOutOfBoundsException  If the number of stock to be sold is higher than the number of stock the
     *          player currently has in the defunct corporation
     */
    protected void sellDefunctStock(Corporation defunctCorp, int stockCount) throws IndexOutOfBoundsException{
        int currentCount = this.stocks.get(defunctCorp);
        if (currentCount < stockCount) {
            LOGGER.warn("IOB exception thrown for corporation {}", defunctCorp.getName());
            throw new IndexOutOfBoundsException("Player " + this.name + " has " + this.stocks.get(defunctCorp) +
                    " stocks in corporation " + defunctCorp.getName() + " and can not sell " + stockCount + " stocks.");

        }
        int counter = stockCount;
        while (counter > 0) {
            int stockSellValue = CorporationList.getInstance().getStockCost(defunctCorp) / 2;
            this.money += stockSellValue;
            counter--;
        }
        this.stocks.replace(defunctCorp, (currentCount - stockCount) );
    }

    /**
     * Method that allows a player to sell stock from a corporation at full price
     * @param corp  The corporation that has gone defunct from a merger
     * @param stockCount  The amount of stock the player wants to sell
     * @throws IndexOutOfBoundsException  If the number of stock to be sold is higher than the number of stock the
     *          player currently has in the corporation
     */
    protected void sellFullPricedStock(Corporation corp, int stockCount) throws IndexOutOfBoundsException{
        int currentCount = this.stocks.get(corp);
        if (currentCount < stockCount) {
            LOGGER.warn("IOB exception thrown for player {} not having sufficient stocks", this.name );
            throw new IndexOutOfBoundsException("Player " + this.name + " has " + this.stocks.get(corp) +
                    " stocks in corporation " + corp.getName() + " and can not sell " + stockCount + " stocks.");
        }
        int counter = stockCount;
        while (counter > 0) {
            int stockSellValue = CorporationList.getInstance().getStockCost(corp);
            this.money += stockSellValue;
            counter--;
        }
        this.stocks.replace(corp, (currentCount - stockCount) );
    }

    /**
     * Method that adds the founders stock bonus to this player
     * @param newCorp  The newly formed corporation
     */
    protected void addFoundersStock(Corporation newCorp) {
        try {
            int oldStockCount = this.stocks.get(newCorp);
            this.stocks.replace(newCorp, oldStockCount + 1);
        } catch (NullPointerException ignored) {
            this.stocks.replace(newCorp, 1);
        }
        LOGGER.info("Player {} formed corporation {} and they gained 1 stock in this corporation", this.name, newCorp.getName());
    }

    /**
     * Method to remove an unplayable tile from a player's hand
     * @param tile  The tile to be removed from the player's hand
     * @throws NoSuchElementException  If the tile is not in the player's hand
     */
    protected void removeTile(Tile tile) throws NoSuchElementException{
        Iterator<Tile> tiles = hand.iterator();
        while (tiles.hasNext()) {
            Tile nextTile = tiles.next();
            if (tile == nextTile) {
                tiles.remove();
                return;
            }
        }
        throw new NoSuchElementException("Player " + this.name + " does not have the tile specified in their hand.");
    }

    /**
     * Method to remove a tile from this player's hand when it is played to the board
     * @param tile  The tile to be played
     * @return  True if the tile was found and removed; False otherwise
     */
    protected boolean playTile(Tile tile){
        Iterator<Tile> tiles = hand.iterator();
        while (tiles.hasNext()) {
            Tile nextTile = tiles.next();
            if (tile.getSpace().equals(nextTile.getSpace())) {
                tiles.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Method to get number of stocks in a corporation a player has
     * @param corp corporation to check stock count of
     * @return int of corporations held
     */
    protected int getStockCount(String corp) {
        int count;
        count = stocks.get(CorporationList.getInstance().getCorporation(corp));
        return count;
    }

    /**
     * Method to add a tile to this player's hand
     * @param tile  The tile to be added
     */
    protected void addTile(Tile tile){
        hand.add(tile);
        LOGGER.info("Tile {} was added to Player {}'s hand", tile.getSpace(), this.name);
    }

    /**
     * Method that gives bonus money to this player
     * @param bonus  The amount of the bonus
     */
    protected void giveBonusMoney(int bonus) {
        this.money += bonus;
    }
}
