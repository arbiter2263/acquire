/*
* Copyright (c) arbiter2263 and contributors. All rights reserved.
* Licensed under the MIT license. See LICENSE file in the project root for details.
*/

package acquire

import javafx.stage.Stage;
import spock.lang.Specification;

class GameboardTest extends Specification {
    //test for getInstance()
    def "get an instance"() {
        setup:
        Gameboard.INSTANCE = null

        when:
        def result = Gameboard.getInstance()

        then:
        result != null
    }
    //test for initializeGame()
    def "initialize a game"() {
        setup:
        Gameboard.INSTANCE = null
        def board = Gameboard.getInstance()

        when:
        board.initializeGame(3)

        then:
        board.getPlayers().size() == 3
    }
    //test for getTilesPlayed()
    def "get the tiles that have been played"() {
        setup:
        Gameboard.INSTANCE = null
        def board = Gameboard.getInstance()
        def player = new Player("name")
        def tile = new Tile(1, 'A' as char)
        def stage = Mock(Stage)
        player.addTile(tile)
        board.placeTile(player, tile, stage)

        when:
        def result = board.getTilesPlayed()

        then:
        result.size() == 1
    }
    //test for getBoard()
    def "get the playing board's row count"() {
        setup:
        Gameboard.INSTANCE = null
        def board = Gameboard.getInstance()

        when:
        def result = board.getBoard()

        then:
        result.length == 12
    }
    def "get the playing board's column count"() {
        setup:
        Gameboard.INSTANCE = null
        def board = Gameboard.getInstance()

        when:
        def result = board.getBoard()

        then:
        result[0].length == 9
    }
    //test for isValidTilePlay()
    def "check if a valid move is valid"() {
        setup:
        Gameboard.INSTANCE = null
        def board = Gameboard.getInstance()
        def stage = Mock(Stage)
        board.placeTile(new Player("name"), new Tile(1, 'A' as char), stage)

        when:
        def result = board.isValidTilePlay(new Tile(6, 'C' as char))

        then:
        result == true
    }
    //test for placeTile()
    def "try to place a tile"() {
        setup:
        Gameboard.INSTANCE = null
        def board = Gameboard.getInstance()
        def tile = new Tile(1, 'A' as char)
        def player = new Player("name")
        def stage = Mock(Stage)
        player.addTile(tile)

        when:
        def result = board.placeTile(player, tile, stage)

        then:
        board.getBoard()[0][0] == tile
    }
    //test for doesTileTouchCorp()
    def "check if a tile touches a corporation"() {
        setup:
        Gameboard.INSTANCE = null
        CorporationList.INSTANCE = null
        def board = Gameboard.getInstance()
        def list = CorporationList.getInstance()
        def corp = list.getInactiveCorps().get(0)
        list.activateCorp(corp)
        corp.addTile(new Tile(1, 'A' as char))
        corp.addTile(new Tile(2, 'A' as char))
        def tile = new Tile(3, 'A' as char)

        when:
        def result = board.doesTileTouchCorp(tile, corp)

        then:
        result == true
    }
    //test for merger()


    //test for expandCorp()
    def "try to expand a corporation"() {
        setup:
        Gameboard.INSTANCE = null
        CorporationList.INSTANCE = null
        def board = Gameboard.getInstance()
        def list = CorporationList.getInstance()
        def corp = list.getInactiveCorps().get(0)
        list.activateCorp(corp)
        corp.addTile(new Tile(1, 'A' as char))
        corp.addTile(new Tile(2, 'A' as char))
        def tile = new Tile(3, 'A' as char)
    }
    //test for isTouchingPlacedTile()
    def "is a new tile touching a placed one"() {
        setup:
        Gameboard.INSTANCE = null
        def board = Gameboard.getInstance()
        def tile = new Tile(3, 'A' as char)
        def player = new Player("name")
        def placedTile = new Tile(4, 'A' as char)
        def stage = Mock(Stage)
        player.addTile(placedTile)
        player.addTile(tile)
        board.placeTile(player, placedTile, stage)

        when:
        def result = board.isTouchingPlacedTile(tile)

        then:
        result == [0, 3]
    }
    //test for makeNewCorp()


    //test for addTileToBoard()
    def "try to add a tile to the board"() {
        setup:
        Gameboard.INSTANCE = null
        def board = Gameboard.getInstance()
        def tile = new Tile(3, 'A' as char)

        when:
        board.addTileToBoard(tile)

        then:
        board.getBoard()[0][2] == tile
    }
    //test for getRowColumnTile()
    def "get the row and column of a tile"() {
        setup:
        Gameboard.INSTANCE = null
        def board = Gameboard.getInstance()
        def tile = new Tile(3, 'A' as char)

        when:
        def result = board.getRowColumnTile(tile)

        then:
        result[1] == 2
    }
}
