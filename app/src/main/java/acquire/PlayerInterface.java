package acquire;

import java.util.LinkedList;

public interface PlayerInterface {
    public String getName();
    public int showMoney();
    public LinkedList<Stock> showStocks();
    public void setName(String name);
}
