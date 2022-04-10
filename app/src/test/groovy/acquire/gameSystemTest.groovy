/*
* Copyright (c) arbiter2263 and contributors. All rights reserved.
* Licensed under the MIT license. See LICENSE file in the project root for details.
*/

package acquire

import spock.lang.Specification


class gameSystemTest extends Specification {

    // initializeGame()


    // purchaseStock(Player player, Corporation corp, int amount)
    //TODO check that the stock values for the corp and the player
    // are
    def "Purchasing a stock player updating stock"() {
        setup:
        def player = new Player("Chris")
        //CorporationList.INSTANCE = null
        int amount = 8
        def corp = CorporationList.getInstance().getCorporation("America")
        CorporationList.getInstance().activateCorp(corp)

        GameSystem.getInstance().purchaseStock(player, corp, amount)

        when:

        def result = player.getStocks().get(corp)
        then:
        result == 8


    }
    // Check if corporation stock value increments
    def "Purchasing a stock corporation updating stock"() {
        setup:
        GameSystem.INSTANCE  = null
        CorporationList.INSTANCE = null
        def player = new Player("Chris")

        int amount = 1
        def corp = CorporationList.getInstance().getCorporation("America")
        CorporationList.getInstance().activateCorp(corp)

        GameSystem.getInstance().purchaseStock(player, corp, amount)

        when:
        def result1 = CorporationList.getInstance().getCorporation(corp.getName()).getStockCount()

        then:
        result1 == 1

    }

    // newGame()
    //TODO should initialize a game based on player count
    // and difficulty: check that a game is initialized
    def "Make a new game"() {

    }

    // playATile(Player player, Tile tile)
    //TODO check that tiles are played
    //TODO check that tile correctly removed from players hand
    def "Playing a tile"() {
        setup:
        def player = new Player("someString")
        char a = 'A'
        def tile = new Tile(1, a)
        def tile2 = new Tile(2, a)

        player.addTile(tile)
        player.addTile(tile2)
        player.addTile(new Tile(3, a))

        when:
        GameSystem.getInstance().playATile(player, tile)

        then:
        player.getHand().size() == 2

    }

    def "Playing a tile to Gameboard"() {
        setup:
        Gameboard.INSTANCE = null
        def board = Gameboard.getInstance()
        def player = new Player("someString")
        char a = 'A'
        def tile = new Tile(1, 'A' as char)
        player.addTile(tile)


        when:
        GameSystem.getInstance().playATile(player, tile)

        then:
        board.getInstance().getTilesPlayed().size() == 1
    }


    // tradeStock(Player player, Corporation corp1, Corporation corp2, int tradeInAmount)
    // check that trading is done correctly:
    // check that players stocks and the corps stocks are updated
    def "Trading in stock 2 for 1"() {
        setup:
        GameSystem.INSTANCE = null
        CorporationList.INSTANCE = null
        Gameboard.INSTANCE = null
        Pile.instance = null
        def player = new Player("name")
        def corp1 = CorporationList.getInstance().getCorporation("America")
        CorporationList.getInstance().activateCorp(corp1)
        def corp2 = CorporationList.getInstance().getCorporation("Phoenix")
        //CorporationList.getInstance().activateCorp(corp2)
//        int amount = 2

        player.buyStock(corp1.getName())
        player.buyStock(corp1.getName())
        player.buyStock(corp1.getName())

        GameSystem.getInstance().tradeStock(player, corp1, corp2)

        expect:
        player.getStocks().get(corp1) == 0
    }

    // sellDefunctStock(Player player, Corporation corp, int sellAmount)
    // check that defunct corporations stocks are updated
    // and that the players stock in each corp is updated
    def "Selling defunct Corporations stock"() {
        setup:
        CorporationList.INSTANCE = null
        Gameboard.INSTANCE = null
        Pile.instance = null
        GameSystem.INSTANCE = null
        def player = new Player("someString")

        def corp = CorporationList.getInstance().getCorporation("America")
        CorporationList.getInstance().activateCorp(corp)
        player.buyStock(corp.getName())
        player.buyStock(corp.getName())
        player.buyStock(corp.getName())

        when:
        GameSystem.getInstance().sellDefunctStock(player, corp, 1)

        then:
        player.getStocks().get(corp) == 2
        corp.getStockCount() == 2

    }


    // removeUnplayableTile(Player player)
    //TODO check that unplayable tiles are removed from
    // the players hand.
//    def "Removing unplayable tile at end of turn"() {
//        setup:
//        CorporationList.INSTANCE = null
//        Gameboard.INSTANCE = null
//        Pile.instance = null
//        GameSystem.INSTANCE = null
//        def tile = new Tile(1, 'a' as char)
//        Gameboard.getInstance().isValidTilePlay(tile) = false
//
//    }


    // drawTile(Player player)
    // draw tile should appropriately take a tile from
    // the pile and add it to the player's hand, players
    // should have 6 in hand at end of turn
    def "Drawing tile to end turn"() {
        GameSystem.INSTANCE = null

        def pile = new Pile()
        def player = new Player("name")

        GameSystem.getInstance().drawTile(player)

        expect:
        pile.getInstance().size()== 108-6
        player.getHand().size() == 6

    }

    // endGameCheck()
    //TODO test for endGame criteria to return true

    // endGame()

    //playerOrder()
    // Checking that playerOrder() correctly adds
    // players to the playerList
    def "Player turn selection"(){
        setup:
        GameSystem.INSTANCE = null
        def player1 = new Player("name")
        def player2 = new Player("name")
        def player3 = new Player("name")
        GameSystem.getInstance().playOrder(player1)
        GameSystem.getInstance().playOrder(player2)
        GameSystem.getInstance().playOrder(player3)

        when:
        def result = GameSystem.getInstance().playerList.size()

        then:
        result == 3
    }
    //playerTurn()
    // Checks that playerTurn() returns the correct
    // Player after x amount of turns. 2 in this case
    def "Player turn selection check players being added"(){
        setup:
        GameSystem.INSTANCE = null
        def player1 = new Player("Chris")
        def player2 = new Player("Randy")
        def player3 = new Player("name")
        GameSystem.getInstance().playOrder(player1)
        GameSystem.getInstance().playOrder(player2)
        GameSystem.getInstance().playOrder(player3)
        GameSystem.getInstance().playerTurn()
        GameSystem.getInstance().playerTurn()
        when:
        def result = GameSystem.getInstance().playerTurn().getName()

        then:
        result == "name"
    }

    //playerTurn()
    // Check that playerTurn() correctly cycles back to
    // the beginning of the list after reaching the last
    // player in the list
    def "Player turn selection cycle to front of list"() {
        setup:
        GameSystem.INSTANCE = null
        def player1 = new Player("Chris")
        def player2 = new Player("Randy")
        def player3 = new Player("Susan")
        GameSystem.getInstance().playOrder(player1)
        GameSystem.getInstance().playOrder(player2)
        GameSystem.getInstance().playOrder(player3)
        GameSystem.getInstance().playerTurn()
        GameSystem.getInstance().playerTurn()
        GameSystem.getInstance().playerTurn()

        when:
        //4th turn with 3 players
        def result = GameSystem.getInstance().playerTurn().getName()

        then:
        result == "Chris"
    }

    //endGame()
    // End game method, should properly adjust wallets of players and
    // pay out the minority/majority stocks
    def "Majority minority pay method"(){
        setup:
        GameSystem.INSTANCE = null
        CorporationList.INSTANCE = null
        Gameboard.INSTANCE = null
        Pile.instance = null
        Gameboard.getInstance().initializeGame(3)
        def corp = CorporationList.getInstance().getCorporation("America")
        def player1 = Gameboard.getInstance().getPlayers().get(0)
        def player2 = Gameboard.getInstance().getPlayers().get(1)
        CorporationList.getInstance().activateCorp(corp)
        //CorporationList.getInstance().activateCorp(corp1)
        corp.addTile(new Tile(1, 'A' as char))
        corp.addTile(new Tile(1, 'A' as char))
        corp.addTile(new Tile(1, 'A' as char))
        corp.addTile(new Tile(1, 'A' as char))
        corp.addTile(new Tile(1, 'A' as char))


        corp.updateStockPrice()

        for(int i = 0; i < 10; i++){
            player1.buyStock(corp.getName())
           // player2.buyStock(corp1.getName())
        }
        for(int i = 0; i < 3; i++){
           player2.buyStock(corp.getName())
            //player1.buyStock(corp1.getName())
        }

        when:
        GameSystem.getInstance().endGame()

        //issue: stock wallets not being updated for
        then:
        player2.getMoney() == 9000 //6000 - 1800 + 1800 + 3000
        player1.getMoney() == 12000   //6000 - 6000 + 6000 +6000 +3000

    }

    //Checking a more in depth game 2 corps of different value
    //3 players wallets
    /**
     * Testing a tie between player 1 and player 2 with corp
     * player 1 should have 23700 after everything on this setup
     * and player 2 should have
     */
    def "Majority minority pay method for 3 players/2 corporations"(){
        setup:
        GameSystem.INSTANCE = null
        CorporationList.INSTANCE = null
        Gameboard.INSTANCE = null
        Pile.instance = null
        Gameboard.getInstance().initializeGame(3)
        def corp = CorporationList.getInstance().getCorporation("America")
        def corp1 = CorporationList.getInstance().getCorporation("Quantum")
        def player1 = Gameboard.getInstance().getPlayers().get(0)
        def player2 = Gameboard.getInstance().getPlayers().get(1)
        def player3 = Gameboard.getInstance().getPlayers().get(2)
        CorporationList.getInstance().activateCorp(corp)
        CorporationList.getInstance().activateCorp(corp1)

        //setup corp sizes with tiles
        for(int i = 0; i < 5; i++){
            corp.addTile(new Tile(i, 'A' as char))
        }
        //set up full sized quantum corp for max payout
        for(int i = 0; i < 41; i++){
            corp1.addTile(new Tile(i, 'B' as char))
        }
        corp.updateStockPrice()
        corp1.updateStockPrice()
        player1.wallet = 1000000  //enough to buy all the stock
        player2.wallet = 1000000
        player3.wallet = 1000000


        for(int i = 0; i < 12; i++){
            player1.buyStock(corp.getName())
        }
        for(int i = 0; i <5; i++){
            player1.buyStock(corp1.getName())
        }


        for(int i = 0; i < 12; i++){
            player2.buyStock(corp.getName())
        }
        for(int i = 0; i < 7; i++){
            player2.buyStock(corp1.getName())
        }



        for(int i = 0; i < 2; i++){
            player3.buyStock(corp.getName())
        }
        for(int i = 0; i < 2; i++){
            player3.buyStock(corp1.getName())
        }

        player1.wallet = 0 //for easy math check
        player2.wallet = 0
        player3.wallet = 0


        when:
        GameSystem.getInstance().endGame()



        then:
                                            //corp tied bonus    corp1 minority bonus
        player1.getMoney() == 23700  // 0 + (12 * 600 + 4500) + (5 * 1200 + 6000)
                                            //corp tied bonus     corp1 majority bonus
        player2.getMoney() == 32100  // 0 + (12 * 600 + 4500)  +  (7 * 1200 + 12000)

        player3.getMoney() == 3600      // 0 + (2 * 600 + 0) + (2 * 1200 + 0)

    }

}