package acquire;

import java.util.LinkedList;

public class Corporation implements CorporationInterface{
    private String name;
    private LinkedList<Tile> tileList;
    private int stockCount;
    private int stockCap;
    private boolean stockCapMet;
    private int stockPrice;
    //private ENUM priceThresholds;

    protected Corporation(String name){
        this.name = name;
    }
    protected String getName() {
        return name;
    }
    protected int getTileCount() {
        return tileList.size();
    }
    protected boolean checkIfSafe() {
        return true;
    }
    protected boolean addTile(Tile tile) {
        return tileList.add(tile);
    }
    protected int getStockCount() {
        return stockCount;
    }
    protected boolean getStockCapMet() {
         return stockCapMet;
    }
    protected boolean setStockCount() {
        return true;
    }
    protected int getStockPrice() {
        return stockPrice;
    }
    protected void setStockPrice(int price) {
        this.stockPrice = price;
    }
}
