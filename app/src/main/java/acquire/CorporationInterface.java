package acquire;

public interface CorporationInterface {
    public String getName();
    public int getTileCount();
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
