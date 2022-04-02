package acquire;

import java.util.LinkedList;
import java.util.Objects;

public class Corporation implements CorporationInterface{
    private String name;
    private LinkedList<Tile> tileList;
    private int stockCount;
    private static final int STOCKCAP = 8;
    private boolean stockCapMet;
    private int stockPrice;

    /**
     * Constructor for Corporation
     * @param name  The name of the corporation being created
     */
    protected Corporation(String name){
        this.name = name;
    }

    /**
     * Simple getter for name
     * @return string  The name of this corporation
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get the number of tiles in this corporation
     * @return int  The number of tiles
     */
    public int getTileCount() {
        return tileList.size();
    }

    /**
     * Method that checks if this corporation has enough tiles to be considered safe
     * @return bool  True if it is safe; False if it is not
     */
    public boolean checkIfSafe() {
        //Needs to be implemented
        if (this.tileList.size() >= 11) {
            return true;
        }
        return false;
    }

    /**
     * Simple getter for stockCount
     * @return int  The number of stock issued for this corporation
     */
    public int getStockCount() {
        return stockCount;
    }

    /**
     * Simple getter for stockCapMet
     * @return bool  True if the maximum number of stocks has been sold; False otherwise
     */
    public boolean getStockCapMet() {
        return this.stockCapMet;
    }

    /**
     * Simple getter for stockPrice
     * @return int  The current price of stock in this corporation
     */
    public int getStockPrice() {
        return stockPrice;
    }

    /**
     * Overridden eguals method
     * @param obj The object to be compared against
     * @return bool  True if the names of both corporations match; False otherwise
     */
    @Override
    public boolean equals(Object obj) {
        Corporation corp = (Corporation) obj;
        if (this.getName() == corp.getName()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Overridden toString method
     * @return String  "CorporationName: Size: [numOfTiles] Stocks: [numOfStocksInPlay]"
     */
    @Override
    public String toString() {
        return this.getName() + ": Size: " + this.getTileCount() + " Stocks: " + this.getStockCount();
    }

    /**
     * Method that adds a tile to this corporation
     * @param tile  The tile to be added
     * @return bool  True if the tile was added successfully; False otherwise
     */
    protected boolean addTile(Tile tile) {
        return tileList.add(tile);
    }

    /**
     * Method that updates this corporation's stockCount after a stock has been bought
     */
    protected void stockBought() {
        this.stockCount++;
        this.updateStockPrice();
        if (this.tileList.size() == this.STOCKCAP) {
            this.stockCapMet = true;
        }
    }

    /**
     * Method that updates this corporation's stockCount after a stock has been sold
     */
    protected void stockSold() {
        this.stockCount--;
        this.updateStockPrice();
        this.stockCapMet = false;
    }

    /**
     * Method that updates the price of stock for this corporation, based on a set of rules
     * Should be called whenever stockCount is incremented or decremented
     */
    private void updateStockPrice() {
        int tiles = this.tileList.size();
        if (this.name == "Sackson" || this.name == "Zeta") {
            //Low-priced corporations
            if (tiles == 2) {
                this.stockPrice = 200;
            } else if (tiles == 3) {
                this.stockPrice = 300;
            } else if (tiles == 4) {
                this.stockPrice = 400;
            } else if (tiles == 5) {
                this.stockPrice = 500;
            } else if (tiles >= 6 && tiles <= 10) {
                this.stockPrice = 600;
            } else if (tiles >= 11 && tiles <= 20) {
                this.stockPrice = 700;
            } else if (tiles >= 21 && tiles <= 30) {
                this.stockPrice = 800;
            } else if (tiles >= 31 && tiles <= 40) {
                this.stockPrice = 900;
            } else if (tiles >= 41) {
                this.stockPrice = 1000;
            }
        } else if (this.name == "Hydra" || this.name == "Fusion" || this.name == "America") {
            //Mid-priced corporations
            if (tiles == 2) {
                this.stockPrice = 300;
            } else if (tiles == 3) {
                this.stockPrice = 400;
            } else if (tiles == 4) {
                this.stockPrice = 500;
            } else if (tiles == 5) {
                this.stockPrice = 600;
            } else if (tiles >= 6 && tiles <= 10) {
                this.stockPrice = 700;
            } else if (tiles >= 11 && tiles <= 20) {
                this.stockPrice = 800;
            } else if (tiles >= 21 && tiles <= 30) {
                this.stockPrice = 900;
            } else if (tiles >= 31 && tiles <= 40) {
                this.stockPrice = 1000;
            } else if (tiles >= 41) {
                this.stockPrice = 1100;
            }
        } else if (this.name == "Pheonix" || this.name == "Quantum") {
            //High-priced corporations
            if (tiles == 2) {
                this.stockPrice = 400;
            } else if (tiles == 3) {
                this.stockPrice = 500;
            } else if (tiles == 4) {
                this.stockPrice = 600;
            } else if (tiles == 5) {
                this.stockPrice = 700;
            } else if (tiles >= 6 && tiles <= 10) {
                this.stockPrice = 800;
            } else if (tiles >= 11 && tiles <= 20) {
                this.stockPrice = 900;
            } else if (tiles >= 21 && tiles <= 30) {
                this.stockPrice = 1000;
            } else if (tiles >= 31 && tiles <= 40) {
                this.stockPrice = 1100;
            } else if (tiles >= 41) {
                this.stockPrice = 1200;
            }
        }
    }
}
