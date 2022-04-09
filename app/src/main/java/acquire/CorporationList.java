package acquire;

import java.util.ArrayList;
import java.util.Hashtable;

public class CorporationList {
    //private static Hashtable<String, int> corporations; //Can not use primitive type in hashtable?
    private ArrayList<Corporation> activeCorps;
    private ArrayList<Corporation> inactiveCorps;
    protected CorporationList() {
        //Make a singleton
    }
    protected int getStockCost() {
        return 1;
    }
    protected ArrayList<Corporation> getActiveCorps() {
        return activeCorps;
    }
    protected ArrayList<Corporation> getInactiveCorps() {
        return inactiveCorps;
    }
    protected void activateCorp(Corporation corporation){}
    protected void deactivateCorp(Corporation corporation){}
}
