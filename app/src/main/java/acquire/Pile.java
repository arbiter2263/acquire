package acquire;

/*
Pile class holds the required methods to be the un-played tiles.
This class generates all 108 tiles and shuffles them into a list
Tiles can be taken and added back into this "pile"
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Pile {
    private static Pile instance = null;
    private final ArrayList<Tile> pile;
    private static final int CAPACITY = 108;
    //Singleton design attempt
    public static Pile getInstance(){
        if (instance == null){
            instance = new Pile();
        }
        return instance;
    }

    /*
    Constructor
    initiates the pile as arraylist to hold all 108 tiles
    adds those tiles to the list
     */
    private Pile(){

        pile = new ArrayList<Tile>(CAPACITY);

        //char array for tile lettering
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L'};

        /*
         loop to add all 108 tiles to the pile initially listing Letter then number value
         of the tile
         */
        for(int i = 1; i <= 9; i++){
            for(var letter : letters){
                Tile tile = new Tile(i, letter);
                pile.add(tile);
            }
        //After all are added, shuffle the deck
        } shuffle();
    }

    /*
    Shuffle method for the pile when initializing it.
    Helps to keep each game a little more random
     */
    public void shuffle(){
        Collections.shuffle(pile);
    }

    /*
    In case a tile needs to be returned to the pile
    @param tile The chosen tile to add back to the pile of tiles
     */
    protected void returnTile(Tile tile){
        pile.add(tile);
    }

    /*
    Pile size check method
    helps to maintain integrity of pile size when
    removing tiles from
     */
    protected int size(){
        return pile.size();
    }
    public String toString() {
        String sb = "";
        for (var tile : pile) {
            sb += tile.getNumber() + " " + tile.getLetter() + "\n";
        }
        return sb;
    }
    /*
    method for randomly choosing a tile drawn by the player, method
    removes tile from pile
    */
    public Tile drawTile(){

        /* Randomly chooses number between 0 and size of pile
           To get a randomly chosen tile.
        */
        Random random = new Random();
        int randomInt = random.nextInt(pile.size());
        if(pile.size() !=0){
            return pile.remove(randomInt);
            //Print out remaining number of tiles?
        }else if(pile.size() < 1){
            // TODO: 4/3/2022  add print statement?
            //print out no tiles left in pile
            System.out.println("No tiles left to draw");
        }
        return null;
    }
}
