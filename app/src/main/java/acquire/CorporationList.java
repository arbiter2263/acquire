package acquire;

import java.util.*;

public class CorporationList {
    private static final String[] CORPORATIONS = {"Sackson", "Zeta", "Hydra", "Fusion", "America", "Pheonix", "Quantum"};
    private static final CorporationList INSTANCE = new CorporationList(); // Field to hold singleton instance of class
    private ArrayList<Corporation> activeCorps;
    private ArrayList<Corporation> inactiveCorps;

    /**
     * Private constructor to enforce only one instance
     */
    private CorporationList() {
        //Initialize all possible corporations as members of inactiveCorps
        for (int i = 0; i < CORPORATIONS.length; i++){
            inactiveCorps.add(new Corporation(CORPORATIONS[i]));
        }
    }

    /**
     * Method to get a reference to the singleton instance of this class
     * @return CorporationList  The only instance of this class
     */
    protected static CorporationList getInstance() {
        return INSTANCE;
    }

    /**
     * Method that gets the cost of stock for a particular corporation
     * @param corporation The corporation that the stock is for
     * @return int  The cost of the stock
     */
    protected int getStockCost(Corporation corporation) {
        return corporation.getStockPrice();
    }

    /**
     * Simple getter for activeCorps
     * @return ArrayList<Corporation>  A copy of activeCorps
     */
    protected ArrayList<Corporation> getActiveCorps() {
        return activeCorps;
    }

    /**
     * Simple getter for inactiveCorps
     * @return ArrayList<Corporation>  A copy of inactiveCorps
     */
    protected ArrayList<Corporation> getInactiveCorps() {
        return inactiveCorps;
    }

    /**
     * Method that removes a corporation from inactiveCorps and adds it to activeCorps
     * @param corporation The corporation to be activated
     */
    protected void activateCorp(Corporation corporation){
        if (isCorpInList(corporation, inactiveCorps) > -1) {
            Iterator<Corporation> iterator = activeCorps.iterator();
            while (iterator.hasNext()) {
                if (corporation == iterator.next()) {
                    //Add corp to activeCorps
                    activeCorps.add(corporation);
                    //Remove corp from inactiveCorps
                    iterator.remove();
                    return;
                }
            }
        } else {
            //Corp is already active
            throw new RuntimeException();
        }
    }

    /**
     * Method that removes a corporation from activeCorps and adds it to inactiveCorps
     * @param corporation The corporation to be deactivated
     */
    protected void deactivateCorp(Corporation corporation){
        if (isCorpInList(corporation, activeCorps) > -1) {
            Iterator<Corporation> iterator = inactiveCorps.iterator();
            while (iterator.hasNext()) {
                if (corporation == iterator.next()) {
                    //Add corp to inactiveCorps
                    inactiveCorps.add(corporation);
                    //Remove corp from activeCorps
                    iterator.remove();
                    return;
                }
            }
        } else {
            //Corp is already inactive
            throw new RuntimeException();
        }
    }

    /**
     * Method that searches a list to see if a corporation is within it
     * @param corporation The corporation to be searched for
     * @param list The list to search within
     * @return int  The index location if the corporation is within the list; -1 otherwise
     */
    private int isCorpInList(Corporation corporation, ArrayList<Corporation> list) {
        ListIterator<Corporation> iterator = list.listIterator();
        while (iterator.hasNext()) {
            //Instead of this, write an 'equals()' method in Corporation and use it here
            if (corporation == iterator.next()) {
                return iterator.nextIndex()-1;
            }
        }
        return -1;
    }
}
