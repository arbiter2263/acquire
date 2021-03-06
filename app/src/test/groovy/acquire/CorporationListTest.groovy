/*
* Copyright (c) arbiter2263 and contributors. All rights reserved.
* Licensed under the MIT license. See LICENSE file in the project root for details.
*/

package acquire


import spock.lang.Specification

class CorporationListTest extends Specification {
    //test for getInstance()
    def "get an instance"() {
        //label for combining setup and when
        when:
        CorporationList.INSTANCE = null
        def list = CorporationList.getInstance()

        then:
        list != null
    }
    //test for getStockCost()
    def "ask for a stock cost"() {
        setup:
        CorporationList.INSTANCE = null
        def list = CorporationList.getInstance()
        def corp = list.getInactiveCorps().get(0)
        list.activateCorp(corp)
        corp.addTile(new Tile(1, 'A' as char))
        corp.addTile(new Tile(2, 'A' as char))

        when:
        def result = list.getStockCost(corp)

        then:
        result == 200
    }

    //test for getCorporation()
    def "getCorporation"(){
        setup:
        CorporationList.INSTANCE = null

        assert CorporationList.getInstance().getCorporation("America").getClass() == Corporation
        def corp = CorporationList.getInstance().getCorporation("America")
        assert corp.getName() == "America"
    }


    //test for getActiveCorps()
    def "get the active corporations"() {
        setup:
        CorporationList.INSTANCE = null
        def list = CorporationList.getInstance()

        when:
        def result = list.getActiveCorps()

        then:
        result.size() == 0
    }
    //test for getInactiveCorps()
    def "get the inactive corporations"() {
        setup:
        CorporationList.INSTANCE = null
        def list = CorporationList.getInstance()

        when:
        def result = list.getInactiveCorps()

        then:
        result.size() == 7
    }
    //test for activateCorp()
    def "try to activate a corporation"() {
        setup:
        CorporationList.INSTANCE = null
        def list = CorporationList.getInstance()

        when:
        def corp = list.getCorporation("Phoenix")
        list.activateCorp(corp)

        then:
        list.getActiveCorps().size() == 1
    }
    //test for deactivateCorp()
    def "try to deactivate a corporation"() {
        setup:
        CorporationList.INSTANCE = null
        def list = CorporationList.getInstance()

        when:
        def corp = list.getCorporation("Phoenix")
        list.activateCorp(corp)
        list.deactivateCorp(corp)

        then:
        list.getActiveCorps().size() == 0
    }

    def "test get status"(){
        setup:
        CorporationList.INSTANCE = null
        def list = CorporationList.getInstance()
        when:
        def corp = list.getCorporation("Sackson")
        def corp1 = list.getCorporation("America")
        def corp2 = list.getCorporation("Hydro")
        list.activateCorp(corp)

        then:
        list.checkStatus(corp.getName()) == true
    }

    //test for newGame()
    def "try to reset the CorporationList for a new game"() {
        setup:
        CorporationList.INSTANCE = null;
        def corpList = CorporationList.getInstance()
        def corp = corpList.getCorporation("America")
        corpList.activateCorp(corp)

        when:
        corpList.newGame()

        then:
        corpList.getActiveCorps().size() == 0
    }

}
