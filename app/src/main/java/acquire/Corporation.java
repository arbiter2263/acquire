/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;

import java.util.LinkedList;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EqualsAndHashCode @ToString
public class Corporation {
    @NonNull @Getter @Setter private String name;
    @Getter private LinkedList<Tile> tileList;
    @Getter private int stockCount;
    @Getter private static final int STOCKCAP = 25;
    @Getter private boolean stockCapMet;
    @Getter private int stockPrice;
    private static Logger LOGGER = LoggerFactory.getLogger(Corporation.class);

    /**
     * Constructor for Corporation
     * @param name  The name of the corporation being created
     */
    protected Corporation(String name){
        this.name = name;
        this.stockCount = 0;
        this.stockCapMet = false;
        this.tileList = new LinkedList<Tile>();
        updateStockPrice();
    }

    /**
     * Method that checks if this corporation has enough tiles to be considered safe
     * @return bool  True if it is safe; False if it is not
     */
    protected boolean checkIfSafe() {
        if (this.tileList.size() >= 11) {
            return true;
        }
        return false;
    }

    /**
     * Method that adds a tile to this corporation
     * @param tile  The tile to be added
     * @return bool  True if the tile was added successfully; False otherwise
     */
    protected boolean addTile(Tile tile) {
        boolean didItWork = this.tileList.add(tile);
        updateStockPrice();
        LOGGER.info("Tile {} was added to the {} corporation", tile.getSpace(), this.name);
        return didItWork;
    }

    /**
     * Method that updates this corporation's stockCount after a stock has been bought
     */
    protected void stockBought() {
        this.stockCount++;
        if (this.stockCount == this.STOCKCAP) {
            this.stockCapMet = true;
        }
    }

    /**
     * Method that updates this corporation's stockCount after a stock has been sold
     */
    protected void stockSold() {
        this.stockCount--;
        this.stockCapMet = false;
    }


    /**
     * Method that updates the price of stock for this corporation, based on a set of rules
     * Should be called whenever stockCount is incremented or decremented
     */
    void updateStockPrice() {
        int tiles = this.tileList.size();
        if (this.name.equals("Sackson") || this.name.equals("Zeta")) {
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
        } else if (this.name.equals("Hydra") || this.name.equals("Fusion") || this.name.equals("America")) {
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
        } else if (this.name.equals("Phoenix") || this.name.equals("Quantum")) {
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
        LOGGER.info("Stock price for {} corporation updated", this.name);
    }

    /**
     * newGame will reset the corporations fields back to default
     * No stocks should be bought, size of corp 0, update stock price
     * back to lowest, reset stock cap to false
     */
    protected void newGame(){
        this.tileList = new LinkedList<>();
        this.stockCount = 0;
        this.stockCapMet = false;
        updateStockPrice();
    }

}
