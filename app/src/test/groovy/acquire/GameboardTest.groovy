/*
* Copyright (c) arbiter2263 and contributors. All rights reserved.
* Licensed under the MIT license. See LICENSE file in the project root for details.
*/

package acquire

import javafx.stage.Stage;
import spock.lang.Specification;

class GameboardTest extends Specification {
    //Set up method
    def setup() {
        GameSystem.INSTANCE = null
        Gameboard.INSTANCE = null
        CorporationList.INSTANCE = null
        Pile.instance = null
    }

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
        board.tilesPlayed.add(tile)

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

    /**
     * Expanding a corporation
     */

    def "Expand an existing corporation"() {
        setup:
        def stage = Mock(Stage)
        Gameboard.INSTANCE = null
        def board = Gameboard.getInstance()
        def player = new Player("Player 1")
        def tile = new Tile(1, 'A' as char)
        def tile2 = new Tile(1, "B" as char)
        def tile3 = new Tile(1, "C" as char)
        def corp = CorporationList.getInstance().getCorporation("America")
        CorporationList.getInstance().activateCorp(corp)
        corp.tileList.add(tile)
        corp.tileList.add(tile2)

        when:
        board.expandCorp(tile3, 0)

        then:
        corp.tileList.size() == 3
    }
    def "get unincorporatedTilesPlayed"() {
        setup:
        Gameboard.INSTANCE = null
        def board = Gameboard.getInstance()

        when:
        def tiles = board.getUnincorporatedTilesPlayed()

        then:
        tiles.isEmpty()
    }
    def "get the game board"() {
        setup:
        Gameboard.INSTANCE = null
        def board = Gameboard.getInstance()

        when:
        def grabbedBoard = board.getBoard()

        then:
        grabbedBoard.length == 12
    }
    def "check if gameboard equals gameboard"() {
        setup:
        Gameboard.INSTANCE = null
        def board = Gameboard.getInstance()

        when:
        def result = Gameboard.getInstance()

        then:
        board == result
    }
    def "try to remove unincorporatedTiles"() {
        setup:
        Gameboard.INSTANCE = null
        CorporationList.INSTANCE = null
        def board = Gameboard.getInstance()
        def corpList = CorporationList.getInstance()
        def corp = corpList.getCorporation("America")
        def tile1 = new Tile(1, 'A' as char)
        def tile2 = new Tile(2, 'A' as char)
        def player = new Player("test")
        def stage = Mock(Stage)
        corpList.activateCorp(corp)
        corp.addTile(tile1)
        corp.addTile(tile2)
        board.placeTile(player, tile1, stage)
        board.placeTile(player, tile2, stage)

        when:
        board.unincorporatedTileCheck()

        then:
        board.getUnincorporatedTilesPlayed().isEmpty()
    }
    def "check if a player has stake in a corporation they don't have stock for"() {
        setup:
        Gameboard.INSTANCE = null
        CorporationList.INSTANCE = null
        def board = Gameboard.getInstance()
        def corpList = CorporationList.getInstance()
        def corp = corpList.getCorporation("America")
        def tile1 = new Tile(1, 'A' as char)
        def tile2 = new Tile(2, 'A' as char)
        corpList.activateCorp(corp)
        corp.addTile(tile1)
        corp.addTile(tile2)
        def player = new Player("test")
        def stage = Mock(Stage)
        def indexes = new LinkedList<Integer>()
        indexes.add(0)

        when:
        def result = board.checkStakes(indexes)

        then:
        result.size() == 0
    }
}
