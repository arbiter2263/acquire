package acquire;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Player {
    private String name;
    private int wallet;
    private LinkedList<Tile> hand;
    private Hashtable<Corporation, Integer> stockCounts;

    /**
     * Constructor for Player
     * @param name  The name of the player being created
     */
    protected Player(String name) {
        this.name = name;
        this.wallet = 6000;
        hand = new LinkedList<Tile>();
        stockCounts = new Hashtable<>();
        // Add inactive corporations
        for (Corporation corp : CorporationList.getInstance().getInactiveCorps()) {
            stockCounts.put(corp, 0);
        }
        // Add active corporations -- should be an empty list ; future proofing
        for (Corporation corp : CorporationList.getInstance().getActiveCorps()) {
            stockCounts.put(corp, 0);
        }
    }

    /**
     * Simple getter for name
     * @return  String  The name of this player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Simple getter for wallet
     * @return  int  The amount of money in this player's wallet
     */
    public int getMoney(){
        return wallet;
    }

    /**
     * Simple getter for a list of Tiles this player has in their hand
     * @return  LinkedList<Tile>  A list of tiles this player has currently
     */
    public LinkedList<Tile> getHand() {
        return this.hand;
    }

    /**
     * Simple getter for stocks
     * @return  HashTable<Corporation, Integer>  A copy of stock counts for each company
     */
    public Hashtable<Corporation, Integer> getStocks() {
        return stockCounts;
    }

    /**
     * Method to set the name of this player to a custom name
     * @param name  The new name of this player
     */
    public void setName(String name) {
        this.name = name;
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
        Iterator<Corporation> corps = CorporationList.getInstance().getActiveCorps().iterator();
        while (corps.hasNext()) {
            Corporation nextCorp = corps.next();
            if (nextCorp.getName() == stockName) {
                int cost = nextCorp.getStockPrice();
                if (this.wallet >= cost) {
                    wallet -= cost;
                    nextCorp.stockBought();
                    int oldStockCount = this.stockCounts.get(nextCorp);
                    this.stockCounts.replace(nextCorp, (oldStockCount + 1) );
                    return true;
                } else {
                    return false;
                }
            }
        }
        throw new NoSuchElementException("The named corporation is not active, so stock can not be bought.");
    }

    /**
     * Method that allows a player to trade stock in a defunct corporation for stock in the surviving corporation;
     * Rate of 2 defunct to 1 surviving
     * @param defunctCorp  The corporation that is going defunct from the merger
     * @param survivingCorp  The corporation that is surviving the merger
     * @throws NoSuchElementException  If the player has no stock in the defunct corporation
     */
    protected void tradeInStock(Corporation defunctCorp, Corporation survivingCorp) throws NoSuchElementException {
        int oldCount = this.stockCounts.get(defunctCorp);
        if (oldCount == 0) {
            throw new NoSuchElementException("Player " + this.name + " has no stock in company " + defunctCorp);
        } else {
            int newStockCount = (int) (oldCount / 2);
            if (oldCount % 2 > 0) {
                sellDefunctStock(defunctCorp, 1);
            }
            this.stockCounts.replace(defunctCorp, 0);
            this.stockCounts.replace(survivingCorp, newStockCount);
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
        int currentCount = this.stockCounts.get(defunctCorp);
        if (currentCount < stockCount) {
            throw new IndexOutOfBoundsException("Player " + this.name + " has " + this.stockCounts.get(defunctCorp) +
                    " stocks in corporation " + defunctCorp.getName() + " and can not sell " + stockCount + " stocks.");
        }
        int counter = stockCount;
        while (counter > 0) {
            int stockSellValue = CorporationList.getInstance().getStockCost(defunctCorp) / 2;
            this.wallet += stockSellValue;
            counter--;
        }
        this.stockCounts.replace(defunctCorp, (currentCount - stockCount) );
    }

    /**
     * Method that allows a player to sell stock from a corporation at full price
     * @param corp  The corporation that has gone defunct from a merger
     * @param stockCount  The amount of stock the player wants to sell
     * @throws IndexOutOfBoundsException  If the number of stock to be sold is higher than the number of stock the
     *          player currently has in the corporation
     */
    protected void sellFullPricedStock(Corporation corp, int stockCount) throws IndexOutOfBoundsException{
        int currentCount = this.stockCounts.get(corp);
        if (currentCount < stockCount) {
            throw new IndexOutOfBoundsException("Player " + this.name + " has " + this.stockCounts.get(corp) +
                    " stocks in corporation " + corp.getName() + " and can not sell " + stockCount + " stocks.");
        }
        int counter = stockCount;
        while (counter > 0) {
            int stockSellValue = CorporationList.getInstance().getStockCost(corp);
            this.wallet += stockSellValue;
            counter--;
        }
        this.stockCounts.replace(corp, (currentCount - stockCount) );
    }

    /**
     * Method that adds the founders stock bonus to this player
     * @param newCorp  The newly formed corporation
     */
    protected void addFoundersStock(Corporation newCorp) {
        int oldStockCount = this.stockCounts.get(newCorp);
        this.stockCounts.replace(newCorp, oldStockCount + 1);
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
            if (tile == nextTile) {
                tiles.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Method to add a tile to this player's hand
     * @param tile  The tile to be added
     */
    protected void addTile(Tile tile){
        hand.add(tile);
    }

    /**
     * Method that gives bonus money to this player
     * @param bonus  The amount of the bonus
     */
    protected void giveBonusMoney(int bonus) {
        this.wallet += bonus;
    }
}
