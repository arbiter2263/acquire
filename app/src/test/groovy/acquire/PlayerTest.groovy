/*
* Copyright (c) arbiter2263 and contributors. All rights reserved.
* Licensed under the MIT license. See LICENSE file in the project root for details.
*/

package acquire

import javafx.stage.Stage;
import spock.lang.Specification;

class PlayerTest extends Specification {
    //Set up method
    def setup() {
        GameSystem.INSTANCE = null
        Gameboard.INSTANCE = null
        CorporationList.INSTANCE = null
        Pile.instance = null
    }

    //test for getName()
    def "get a player's name"() {
        setup:
        def player = new Player("FunName")

        when:
        def result = player.getName()

        then:
        result.equals("FunName")
    }

    //test for getMoney()
    def "show a player's money"() {
        setup:
        def player = new Player("FunName")

        when:
        def result = player.getMoney()

        then:
        result == 6000
    }

    //test for getHand()
    def "show a player's hand"() {
        setup:
        def player = new Player("FunName")
        player.addTile(new Tile(1, 'A' as char))

        when:
        def result = player.getHand()

        then:
        result.size() == 1
    }

    //test for getStocks()
    def "show a player's stocks"() {
        setup:
        def player = new Player("FunName")

        when:
        def result = player.getStocks()
        def listValues = result.elements().toList()

        then:
        listValues.get(0) == 0 && listValues.get(6) == 0
    }

    //test for setName()
    def "change a player's name"() {
        setup:
        def player = new Player("FunName")

        when:
        player.setName("differentName")
        def result = player.getName()

        then:
        result.equals("differentName")
    }

    //test for getHint()
    def "get a hint class"() {
        setup:
        def player = new Player("FunName")

        when:
        def result = player.getHint()

        then:
        result.getClass() == Hint
    }

    //test for getStocks()
    def "test getting the value for a key in player.stocks"() {
        setup:
        def player = new Player("name")

        when:
        def stockList = player.getStocks()
        def corp = CorporationList.getInstance().getCorporation("Sackson")
        def result = stockList.get(corp.getName())

        then:
        result == 0
    }
    def "try to buy a stock from an active corporation"() {
        setup:
        def list = CorporationList.getInstance()
        def corp = list.getCorporation("Quantum")
        def player = new Player("FunName")
        corp.addTile(new Tile(1, 'A' as char))
        corp.addTile(new Tile(2, 'A' as char))
        list.activateCorp(corp)

        when:
        def result = player.buyStock("Quantum")

        then:
        result == true
    }

    //test for removeTile()
    def "try to remove a tile from hand"() {
        setup:
        def player = new Player("FunName")
        def tile = new Tile(9, 'A' as char)
        player.addTile(tile)

        when:
        player.removeTile(tile)

        then:
        player.getHand().size() == 0
    }

    //test for playTile()
    def "try to play a tile"() {
        setup:
        def player = new Player("FunName")
        def tile = new Tile(1, 'A' as char)
        player.addTile(tile)

        when:
        def result = player.playTile(tile)

        then:
        result == true
    }

    //test for addTile()
    def "try to add a tile"() {
        setup:
        def player = new Player("FunName")

        when:
        player.addTile(new Tile(1, 'A' as char))

        then:
        player.getHand().size() == 1
    }
}
