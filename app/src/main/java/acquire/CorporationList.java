/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;

import com.google.common.annotations.VisibleForTesting;

import java.util.*;

public class CorporationList {
    private static final String[] CORPORATIONS = {"Sackson", "Zeta", "Hydra", "Fusion", "America", "Phoenix", "Quantum"};
    @VisibleForTesting protected static CorporationList INSTANCE = null; // Field to hold singleton instance of class
    private ArrayList<Corporation> activeCorps;
    private ArrayList<Corporation> inactiveCorps;

    /**
     * Private constructor to enforce only one instance
     */
    private CorporationList() {
        this.inactiveCorps = new ArrayList<Corporation>();
        this.activeCorps = new ArrayList<Corporation>();
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
        if (INSTANCE == null) {
            INSTANCE = new CorporationList();
        }
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
     * Method that returns a reference to a specific corporation
     * @param name  The name of the wanted corporation
     * @return  Corporation  The corporation with the same name; null if no corporation has the provided name
     */
    protected Corporation getCorporation(String name) {
        Iterator<Corporation> iterator = this.activeCorps.iterator();
        while(iterator.hasNext()) {
            Corporation corp = iterator.next();
            if (corp.getName().equals(name)) {
                return corp;
            }
        }
        iterator = this.inactiveCorps.iterator();
        while(iterator.hasNext()) {
            Corporation corp = iterator.next();
            if (corp.getName().equals(name)) {
                return corp;
            }
        }
        return null;
    }

    /**
     * Method that removes a corporation from inactiveCorps and adds it to activeCorps
     * @param corporation The corporation to be activated
     * @throws NoSuchElementException  If the corporation is not within the inactive list
     */
    protected void activateCorp(Corporation corporation) throws NoSuchElementException{
        if (isCorpInList(corporation, inactiveCorps) > -1) {
            Iterator<Corporation> iterator = inactiveCorps.iterator();
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
            throw new NoSuchElementException("Corporation " + corporation.getName() + " is not within the inactive list and can not be activated.");
        }
    }

    /**
     * Method that removes a corporation from activeCorps and adds it to inactiveCorps
     * @param corporation The corporation to be deactivated
     * @throws NoSuchElementException  If the corporation is not within the active list
     */
    protected void deactivateCorp(Corporation corporation) throws NoSuchElementException{
        if (isCorpInList(corporation, activeCorps) > -1) {
            Iterator<Corporation> iterator = activeCorps.iterator();
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
            throw new NoSuchElementException("Corporation " + corporation.getName() + " is not within the active list and can not be deactivated.");
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
