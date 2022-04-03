package acquire;
/*
Tile class holds the pertinent information related to
the tiles in the game. Each tile created will have a letter
and number associated to it, which makes of the space it would
take on the game board
 */


public class Tile implements TileInterface{
    private final String space;
    private final int number;
    private final char letter;

    /*
    Constructor
    Acquire tile pieces have a number and a letter to designate
    where that tile can be placed on the board

    @param number will be any number between 1 and 12 which correlates
    to the columns of the gameboard.

    @param letter will be a letter between A and I which correlates
    to the rows of the gameboard.
     */

    protected Tile(int number, char letter){
        this.number=number;
        this.letter=letter;
        space = String.valueOf(number) + letter;
    }

    /*
    method returns space
    @return returns a string of the tile
    i.e. 11B
     */
    protected String getSpace() {
        return space;
    }

    /*
    method @returns just the number value of the tile
     */
    public int getNumber(){
        return number;
    }
    /*
    method @returns just the letter value of the tile
     */
    public char getLetter(){
        return letter;
    }
}
