/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;
/**
Tile class holds the pertinent information related to
the tiles in the game. Each tile created will have a letter
and number associated to it, which makes of the space it would
take on the game board
 */

import lombok.*;

@EqualsAndHashCode @ToString
public class Tile {
    @Getter private final String space;
    @Getter private final int number;
    @Getter private final char letter;

    /**
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
}
