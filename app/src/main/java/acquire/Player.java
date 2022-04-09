package acquire;

import java.util.LinkedList;

public class Player implements PlayerInterface{
    private String name;
    private int wallet;
    private LinkedList<Tile> hand;
    private LinkedList<Stock> stocks;

    protected Player(String name) {
        this.name = name;
        this.wallet = 6000;
        hand = new LinkedList<Tile>();
        stocks = new LinkedList<Stock>();
    }
    protected void getHint(){};
    protected int showMoney(){
        return wallet;
    }
    protected LinkedList<Stock> showStocks() {
        return stocks;
    }
    protected boolean buyStock(String stockName) {
        return true;
    }
    protected void removeTile(Tile tile){}
    protected boolean playTile(Tile tile){
        return true;
    }
    protected void addTile(Tile tile){
        hand.add(tile);
    }
}
