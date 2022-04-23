/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.*;

@EqualsAndHashCode @ToString
public class CorporationList {
    @Getter private static final String[] CORPORATIONS = {"Sackson", "Zeta", "Hydra", "Fusion", "America", "Phoenix", "Quantum"};
    @VisibleForTesting protected static CorporationList INSTANCE = null; // Field to hold singleton instance of class
    @Getter private ArrayList<Corporation> activeCorps;
    @Getter private ArrayList<Corporation> inactiveCorps;
    private static Logger LOGGER = LoggerFactory.getLogger(CorporationList.class);

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
                    LOGGER.info("Corporation {} was activated", corporation.getName());
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
                    LOGGER.info("Corporation {} was moved to inactive state", corporation.getName());
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
    
    /**
     * Method to check the status of a corporation given. Returns true if active and false if inactive
     * @param corp name of company that's status needs checked
     * @return boolean true or false to indicate status
     */
    protected boolean checkStatus(String corp) {
        return activeCorps.contains(CorporationList.getInstance().getCorporation(corp));
       }


    /**
     * saveCorpList will save the corporation objects in this
     * list to use to load later should the players choose to
     * stop playing for a bit
      * @throws IOException
     */
    protected void saveCorpList() throws IOException {
        Writer writer = new FileWriter("acquire/app/jsonsave/corporationlist.json", false);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try{
            gson.toJson(CorporationList.getInstance(), writer); //Not appending to keep file fresh on new save
        }catch(Exception IOE){
            LOGGER.warn("Unable to write game objects to file to save.");
        }
        writer.flush();
        writer.close();
        LOGGER.info("Game was saved");
    }


    /**
     *  loadCorpList loads in saved corporations from the save
     *  file and replaces the current instance corporations with
     *  the saved ones
     * @throws FileNotFoundException
     */
    protected void loadCorpList() throws FileNotFoundException {

        Gson gson = new Gson();
        Reader reader = new FileReader("acquire/app/jsonsave/corporationlist.json");
        CorporationList newCorpList = gson.fromJson(reader, (Type) CorporationList.class);

        //Now replace current instance with saved instance corporations
        CorporationList.getInstance().activeCorps = newCorpList.activeCorps;
        CorporationList.getInstance().inactiveCorps = newCorpList.inactiveCorps;
    }

    /**
     * newGame will check all current inactive corporations
     * and reset them to default stats with newGame call
     * Then it will check for active corporations and reset their
     * stats to default and deactivate them
     */
    protected void newGame(){

        for(Corporation corp : inactiveCorps){
            corp.newGame();
        }

        for(Corporation corp : activeCorps){
            corp.newGame();
            deactivateCorp(corp);
        }
    }

}
