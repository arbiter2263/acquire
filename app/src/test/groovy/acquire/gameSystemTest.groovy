package acquire

import spock.lang.Specification

<<<<<<< HEAD
class gameSystemTest extends Specification {

    // initializeGame()


    // purchaseStock(Player player, Corporation corp, int amount)
    //TODO check that the stock values for the corp and the player
    // are
    def "Purchasing a stock"(){
        setup:
        def player = new Player("Chris")
        def gamesystem = new GameSystem()

        int amount = 5
        def corp = CorporationList.getInstance().getCorporation("America")
        CorporationList.getInstance().activateCorp(corp)

        gamesystem.purchaseStock(player, corp, amount)


        when:

        def result = corp.getStockCount()

        then:
        result == 5




    }

    // newGame()
    //TODO should initialize a game based on player count
    // and difficulty: check that a game is initialized
    def "Make a new game"(){

    }

    // playATile(Player player, Tile tile)
    //TODO check that tiles are played
    //TODO check that tile correctly removed from players hand
    def "Playing a tile"(){
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

    def "Playing a tile to Gameboard"(){
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
    //TODO check that trading is done correctly:
    // check that players stocks and the corps stocks are updated
    def "Trading in stock 2 for 1"(){
        setup:
        CorporationList.INSTANCE = null
        def player = new Player("name")
        def corp1 = CorporationList.getInstance().getCorporation("America")
        CorporationList.getInstance().activateCorp(corp1)
        def corp2 = CorporationList.getInstance().getCorporation("Pheonix")
        CorporationList.getInstance().activateCorp(corp2)
//        int amount = 2

        player.buyStock(corp1.getName())
        player.buyStock(corp1.getName())
        player.buyStock(corp1.getName())

        GameSystem.getInstance().tradeStock(player, corp1, corp2)

        expect:
        player.getStocks().get(corp1) == 0
    }

    // sellDefunctStock(Player player, Corporation corp, int sellAmount)
    //TODO check that defunct corporations stocks are updated
    // and that the players stock in each corp is updated
    def "Selling defunct Corporations stock"(){

    }


    // removeUnplayableTile(Player player)
    //TODO check that unplayable tiles are removed from
    // the players hand.
    def "Removing unplayable tile at end of turn"(){

    }


    // drawTile(Player player)
    //TODO draw tile should appropriately take a tile from
    // the pile and add it to the player's hand
    def "Drawing tile to end turn"(){
        GameSystem.INSTANCE = null

        def pile = new Pile()
        def player = new Player( "name")

        GameSystem.getInstance().drawTile(player)

        expect:
        pile.size() == 107

    }

    // endGameCheck()
    //TODO test for endGame criteria to return true

    // endGame()

=======
class gameSystemTest extends Specification{



>>>>>>> feature/tilepile

}
