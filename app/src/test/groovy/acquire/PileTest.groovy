/*
* Copyright (c) arbiter2263 and contributors. All rights reserved.
* Licensed under the MIT license. See LICENSE file in the project root for details.
*/

package acquire

import spock.lang.Specification


class PileTest extends Specification{

    def "Create pile of capacity size"(){
        given:
        Pile.instance = null

        def pile = new Pile()

        assert pile.size() == 108

    }

    //returnTile()
    //drawing a tile and returning it
    def "Return a tile method"(){
        setup:
        Pile.instance = null
        def pile = new Pile()
        def tile =pile.drawTile()
        pile.returnTile(tile)
        assert pile.size() == 108
    }

    def "Draw tile test"(){
        given:
        Pile.instance = null
        def pile = new Pile()

        when:
        pile.drawTile()

        then:
        pile.size() == 107
    }

    def "Draw tile test for tile"(){
        given:
        Pile.instance = null
        def pile = new Pile()

        when:
        def tile = pile.drawTile()

        then:
        assert tile.getClass()== Tile
    }

    def "Draw tile after pile is empty"(){
        given:
        Pile.instance = null
        def pile = new Pile()
        int size = pile.size()

        when:
        while(pile.size() !=0){
            pile.drawTile()
        }
        pile.drawTile() //109th draw
        then:
        thrown IllegalArgumentException
    }

    def "Returning tile to pile"(){
        given:
        Pile.instance = null
        def pile = new Pile()

        char let = 'Z'
        def tile = new Tile(15,  let)

        when:
        pile.returnTile(tile)

        then:
        pile.size() == 109
    }

    //toString()
    def "To String Method"(){
        setup:
        Pile.instance = null
        def pile = new Pile()

        assert pile.toString().getClass() == String
    }
}
