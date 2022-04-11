/*
* Copyright (c) arbiter2263 and contributors. All rights reserved.
* Licensed under the MIT license. See LICENSE file in the project root for details.
*/

package acquire;

import spock.lang.Specification
import spock.lang.Unroll;

class HintTest extends Specification {


    // test for getHint()
    def "get the first hint"() {
        setup:
        def hint = new Hint()

        when:
        def result = hint.getHint()

        then:
        result != null
    }

    //test for getNextHint()
    def "Next hint if greater than size of hints"(){
        setup:
        def hint = new Hint()

        hint.index = 5
        hint.getNextHint()
        assert hint.index == 0
    }

    //test for getNextHint()
    def "get the next hint"() {
        setup:
        def hint = new Hint()
        def firstHint = hint.getHint()

        when:
        def result = hint.getNextHint()

        then:
        result != firstHint
        hint.index == 1
    }
    //test for getPreviousHint()
    def "get the previous hint"() {
        setup:
        def hint = new Hint()
        def firstHint = hint.getHint()
        hint.getNextHint()

        when:
        def result = hint.getPreviousHint()

        then:
        result == firstHint
        hint.index == 0
    }
    //test for getPreviousHint()
    def "get the previous hint loop to back of hints list"() {
        setup:
        def hint = new Hint()
        def firstHint = hint.getHint()

        when:
        hint.getPreviousHint()

        then:

        hint.index == 5
    }
}
