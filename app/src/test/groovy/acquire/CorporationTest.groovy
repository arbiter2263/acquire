/*
* Copyright (c) arbiter2263 and contributors. All rights reserved.
* Licensed under the MIT license. See LICENSE file in the project root for details.
*/

package acquire

import spock.lang.Specification

class CorporationTest extends Specification {
    //test for getName()
    def "get a corporation's name"() {
        setup:
        def corp = new Corporation("Phoenix")

        when:
        def result = corp.getName()

        then:
        result == "Phoenix"
    }


    //tests for getTileList()
    def "get an empty list of tiles"() {
        setup:
        def corp = new Corporation("Phoenix")

        when:
        def result = corp.getTileList()

        then:
        result.size() == 0
    }


    def "get a list of tiles"() {
        setup:
        def corp = new Corporation("Phoenix")
        corp.addTile(new Tile(1, 'A' as char))
        corp.addTile(new Tile(2, 'B' as char))
        corp.addTile(new Tile(3, 'C' as char))

        when:
        def result = corp.getTileList()

        then:
        result.size() == 3
    }

    //tests for checkIfSafe()
    def "check a safe corporation"() {
        setup:
        def corp = new Corporation("Phoenix")
        corp.addTile(new Tile(1, 'A' as char))
        corp.addTile(new Tile(2, 'A' as char))
        corp.addTile(new Tile(3, 'A' as char))
        corp.addTile(new Tile(4, 'A' as char))
        corp.addTile(new Tile(5, 'A' as char))
        corp.addTile(new Tile(6, 'A' as char))
        corp.addTile(new Tile(7, 'A' as char))
        corp.addTile(new Tile(8, 'A' as char))
        corp.addTile(new Tile(9, 'A' as char))
        corp.addTile(new Tile(1, 'B' as char))
        corp.addTile(new Tile(2, 'B' as char))

        when:
        def result = corp.checkIfSafe()

        then:
        result == true
    }
    def "check a no-safe corporation"() {
        setup:
        def corp = new Corporation("Phoenix")
        corp.addTile(new Tile(1, 'A' as char))
        corp.addTile(new Tile(2, 'A' as char))
        corp.addTile(new Tile(3, 'A' as char))
        corp.addTile(new Tile(4, 'A' as char))

        when:
        def result = corp.checkIfSafe()

        then:
        result == false
    }

    //test for equals
    def "Corp1 equals corp2 true"(){
        setup:
        CorporationList.INSTANCE = null
        def corp1 = new Corporation("America")
        def corp2 = new Corporation("America")

        assert corp1.equals(corp2) == true
    }
    def "Corp1 equals corp2 false"(){
        setup:
        CorporationList.INSTANCE = null
        def corp1 = new Corporation("America")
        def corp2 = new Corporation("Phoenix")

        assert corp1.equals(corp2) == false
    }

    //tests for toString()
    def "To String method"(){
        setup:
        def corp1 = new Corporation("America")
        corp1.stockBought()

        assert corp1.toString() == "Corporation(name=America, tileList=[], stockCount=1, stockCapMet=false, stockPrice=0)"
    }

    //tests for getStockCount()
    def "get the stock count after buying"() {
        setup:
        def corp = new Corporation("Phoenix")
        corp.stockBought()
        corp.stockBought()

        when:
        def result = corp.getStockCount()

        then:
        result == 2
    }
    def "get the stock count after buying and selling"() {
        setup:
        def corp = new Corporation("Phoenix")
        corp.stockBought()
        corp.stockBought()
        corp.stockSold()

        when:
        def result = corp.getStockCount()

        then:
        result == 1
    }

    //tests for getStockCapMet()
    def "check if the stock cap met when it hasn't been"() {
        setup:
        def corp = new Corporation("Phoenix")
        corp.stockBought()
        corp.stockBought()

        when:
        def result = corp.isStockCapMet()

        then:
        result == false
    }
    def "check if stock cap met when it has been"() {
        setup:
        def corp = new Corporation("Phoenix")
        for (int i = 0; i <= 25; i++) {
            corp.stockBought()
        }

        when:
        def result = corp.isStockCapMet()

        then:
        result == true
    }

    //tests for getStockPrice()
    def "check stock price for high-priced corporation"() {
        setup:
        def corp = new Corporation("Phoenix")
        corp.addTile(new Tile(1, 'A' as char))
        corp.addTile(new Tile(2, 'A' as char))

        when:
        def result = corp.getStockPrice()

        then:
        result == 400
    }
    def "check stock price for mid-priced corporation"() {
        setup:
        def corp = new Corporation("Hydra")
        corp.addTile(new Tile(1, 'A' as char))
        corp.addTile(new Tile(2, 'A' as char))

        when:
        def result = corp.getStockPrice()

        then:
        result == 300
    }
    def "check stock price after adding tiles"() {
        setup:
        def corp = new Corporation("Phoenix")
        corp.addTile(new Tile(1, 'A' as char))
        corp.addTile(new Tile(2, 'A' as char))
        corp.addTile(new Tile(2, 'A' as char))

        when:
        def result = corp.getStockPrice()

        then:
        result == 500
    }

    //tests for addTile()
    def "add just one tile"() {
        setup:
        def corp = new Corporation("Phoenix")

        when:
        def result = corp.addTile(new Tile(1, 'A' as char))

        then:
        result == true  && corp.getTileList().size() == 1
    }
    def "add three tile"() {
        setup:
        def corp = new Corporation("Phoenix")

        when:
        corp.addTile(new Tile(3, 'A' as char))
        corp.addTile(new Tile(2, 'A' as char))
        def result = corp.addTile(new Tile(1, 'A' as char))

        then:
        result == true  && corp.getTileList().size() == 3
    }

    //test for stockBought()
    def "buy a stock"() {
        setup:
        def corp = new Corporation("Phoenix")

        when:
        corp.stockBought()

        then:
        corp.getStockCount() == 1
    }

    //stockSold()
    def "sell a stock"() {
        setup:
        def corp = new Corporation("Phoenix")

        when:
        corp.stockBought()
        corp.stockSold()

        then:
        corp.getStockCount() == 0
        assert corp.stockCapMet == false
    }
    def "sell a stock at stock cap"() {
        setup:
        def corp = new Corporation("Phoenix")

        when:
        corp.stockCount = 25
        corp.stockSold()

        then:
        corp.getStockCount() == 24
        assert corp.stockCapMet == false
    }

    //updateStockPrice()
    def "check stock price after 4 tiles are added"() {
        setup:
        def corp = new Corporation("Phoenix")
        for (int i = 0; i < 10; i++) {
            corp.addTile(new Tile(i, 'A' as char))
        }

        when:
        def result = corp.getStockPrice()

        then:
        result == 800
    }
}
