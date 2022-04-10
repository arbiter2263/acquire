/*
* Copyright (c) arbiter2263 and contributors. All rights reserved.
* Licensed under the MIT license. See LICENSE file in the project root for details.
*/

package acquire

import spock.lang.Specification


class PileTest extends Specification{

    def "Create pile of capacity size"(){
        given:
        def pile = new Pile()

        assert pile.size() == 108

    }

    def "Draw tile test"(){
        given:
        def pile = new Pile()

        when:
        pile.drawTile()

        then:
        pile.size() == 107
    }

    def "Draw tile after pile is empty"(){
        given:
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
        def pile = new Pile()

        char let = 'Z'
        def tile = new Tile(15,  let)

        when:
        pile.returnTile(tile)

        then:
        pile.size() == 109
    }
}
