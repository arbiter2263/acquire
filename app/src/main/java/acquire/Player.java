package acquire;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Player implements PlayerInterface{
    private String name;
    private int wallet;
    private LinkedList<Tile> hand;
    private LinkedList<Stock> stocks;

    /**
     * Constructor for Player
     * @param name  The name of the player being created
     */
    protected Player(String name) {
        this.name = name;
        this.wallet = 6000;
        hand = new LinkedList<Tile>();
        stocks = new LinkedList<Stock>();
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
    public int showMoney(){
        return wallet;
    }

    /**
     * Simple getter for stocks
     * @return  LinkedList<Stock>  A copy of stocks
     */
    public LinkedList<Stock> showStocks() {
        return stocks;
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
                    nextCorp.stockSold();
                    this.stocks.add(new Stock(stockName, cost));
                    return true;
                } else {
                    return false;
                }
            }
        }
        throw new NoSuchElementException("The named corporation is not active, so stock can not be bought.");
    }

    /**
     * Method to remove an unplayable tile from a player's hand and call Pile to destroy the tile
     * @param tile  The tile to be removed and destroyed
     * @throws NoSuchElementException  If the tile is not in the player's hand
     */
    protected void removeTile(Tile tile) throws NoSuchElementException{
        Iterator<Tile> tiles = hand.iterator();
        while (tiles.hasNext()) {
            Tile nextTile = tiles.next();
            if (tile == nextTile) {
                tiles.remove();
                Pile.destroyTile(nextTile);
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
}
