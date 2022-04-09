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
    def "get the next hint"() {
        setup:
        def hint = new Hint()
        def firstHint = hint.getHint()

        when:
        def result = hint.getNextHint()

        then:
        result != firstHint
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
    }
}
