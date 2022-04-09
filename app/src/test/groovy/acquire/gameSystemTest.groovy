package acquire

import spock.lang.Specification

class gameSystemTest extends Specification {

    // initializeGame()


    // purchaseStock(Player player, Corporation corp, int amount)
    //TODO check that the stock values for the corp and the player
    // are
    def "Purchasing a stock"(){

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

    }


    // tradeStock(Player player, Corporation corp1, Corporation corp2, int tradeInAmount)
    //TODO check that trading is done correctly:
    // check that players stocks and the corps stocks are updated
    def "Trading in stock 2 for 1"(){

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

    }

    // endGameCheck()
    //TODO test for endGame criteria to return true

    // endGame()


}
