/*
* Copyright (c) arbiter2263 and contributors. All rights reserved.
* Licensed under the MIT license. See LICENSE file in the project root for details.
*/

package acquire

import spock.lang.Specification

class TileTest extends Specification{
    def "create a tile"(){
        given:
        int num = 1
        char ch = 'B'
        def tile = new Tile(num, ch)

        expect:
        tile
    }

    def "Get tile's letter"(){
        given:
        int num = 1
        char ch = 'B'
        def tile = new Tile(num, ch)

        expect:
        tile.getLetter() == 'B'

    }

    def "Get tile's number"(){
        given:
        int num = 1
        char ch = 'B'
        def tile = new Tile(num, ch)

        expect:
        tile.getNumber() == 1
    }

    def "Get tile's space"(){
        given:
        int num = 1
        char ch = 'B'
        def tile = new Tile(num, ch)

        expect:
        tile.space == "1B"
    }

}