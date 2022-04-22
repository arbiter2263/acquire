/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;

/**
Pile class holds the required methods to be the un-played tiles.
This class generates all 108 tiles and shuffles them into a list
Tiles can be taken and added back into this "pile"
 */

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import lombok.*;

@EqualsAndHashCode @ToString
public class Pile {
    private static Logger LOGGER = LoggerFactory.getLogger(Pile.class);
    private static Pile instance = null;
    private ArrayList<Tile> pile;

    private static final int CAPACITY = 108;

    //Singleton design attempt
    public static Pile getInstance(){
        if (instance == null){
            instance = new Pile();
        }
        return instance;
    }

    /**
    Constructor
    initiates the pile as arraylist to hold all 108 tiles
    adds those tiles to the list
     */
    private Pile(){

        pile = new ArrayList<Tile>(CAPACITY);

        //char array for tile lettering
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L'};


        //loop to add all 108 tiles to the pile initially listing Letter then number value
        // of the tile
        for(int i = 1; i <= 9; i++){
            for(var letter : letters){
                Tile tile = new Tile(i, letter);
                pile.add(tile);
            }
        //After all are added, shuffle the deck
        } shuffle();
        LOGGER.info("Pile was created");
    }



    /**
    Shuffle method for the pile when initializing it.
    Helps to keep each game a little more random
     */
    public void shuffle(){
        Collections.shuffle(pile);
    }

    /**
    In case a tile needs to be returned to the pile
    @param tile The chosen tile to add back to the pile of tiles
     */
    protected boolean returnTile(Tile tile){

        pile.add(tile);
        return true;
    }

   /**
    method for randomly choosing a tile drawn by the player, method
    removes tile from pile
    @return returns the Tile drawn so it can be placed in players hand
    */
    public Tile drawTile() throws IllegalArgumentException{

        /** Randomly chooses number between 0 and size of pile
           To get a randomly chosen tile.
        */
        Random random = new Random();
        int randomInt = random.nextInt(pile.size());
            if (pile.size() != 0) {
                LOGGER.info("Tile {} was drawn and removed from the pile.", pile.get(randomInt).getSpace());
                return pile.remove(randomInt);
                //Print out remaining number of tiles?

            } else if (pile.size() < 1) {
                LOGGER.info("Pile was empty when player tried to draw");
                    throw new IllegalArgumentException("The pile is empty, cannot draw more tiles");

            }
            return null;
       }


    /**
     Pile size check method
     helps to maintain integrity of pile size when
     removing tiles from
     @return returns number of tiles left in the pile.
     */
    protected int size(){
        return pile.size();
    }


    /**
     *
     * @throws IOException
     */

    protected void savePile() throws IOException {
        Writer writer = new FileWriter("acquire/app/jsonsave/pile.json", false);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try{
            gson.toJson(Pile.getInstance(), writer); //Not appending to keep file fresh on new save
        }catch(Exception IOE){
            LOGGER.warn("Unable to write game objects to file to save.");
        }
        writer.flush();
        writer.close();
        LOGGER.info("Game was saved");
    }


    /**
     * Method to load a saved instance
     * so players can continue playing an
     * instance from before
     * @return
     */
    protected void loadGame() throws FileNotFoundException {
        //Empty old instance pile so it can be replaced with the load instance
        if(pile.size() > 0){
            while(pile.size() > 0){
                pile.remove(0);
            }
        }
        Gson gson = new Gson();
        Reader reader = new FileReader("acquire/app/jsonsave/pile.json");
        Pile newPile = gson.fromJson(reader, (Type) Pile.class);
        Pile.getInstance().pile = newPile.pile;
    }
}
