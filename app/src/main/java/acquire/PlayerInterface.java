package acquire;

import java.util.Hashtable;
import java.util.LinkedList;

public interface PlayerInterface {
    public String getName();
    public int showMoney();
    public Hashtable<Corporation, Integer> showStocks();
    public void setName(String name);
}
