package acquire;

import java.util.LinkedList;

public interface CorporationInterface {
    public String getName();
    public LinkedList<Tile> getTileList();
    public boolean checkIfSafe();
    public int getStockCount();
    public boolean getStockCapMet();
    public int getStockPrice();
    /*equals and toString methods must be overridden*/
    @Override
    public boolean equals(Object obj);
    @Override
    public String toString();
}
