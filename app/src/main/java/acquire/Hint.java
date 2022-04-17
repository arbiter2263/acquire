/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;

import lombok.*;

@EqualsAndHashCode @ToString @NoArgsConstructor
public class Hint {
    private static String[] possibleHits = {
            "Until a corporation contains at least 11 tiles, it can be merged into another corporation.",
            "Stock in any corporation is always cheaper when the corporation is smaller. Buying at this time is a great way to maximize your profits",
            "No matter how much you paid for a stock, its price is always determined by how many tiles are in the corporation at the time of sale.",
            "In the event of a tie in a merger, the player who played the tile gets to decide which company survives",
            "Once a corporation becomes defunct through a merger, it can be reestablished when another corporation is formed. All previously sold stock is still valid for this 'new' corporation",
            "Running out of money: Aggressively competing to become the majority stockholder in a corporation can be risky. Invest wisely in corporations that are small and close to bigger ones."};
    private int index = 0;

    /**
     * Method to get the first hint, when the class is first initialized
     * @return String  The first hint
     */
    public String getHint() {
        return possibleHits[index];
    }

    /**
     * Method to get the next hint from the current index
     * @return String  The next possible hint
     */
    public String getNextHint() {
        if ( (index + 1) > (possibleHits.length - 1) ) {
            index = 0;
        } else {
            index++;
        }
        return possibleHits[index];
    }

    /**
     * Method to get the previous hint from the current index
     * @return String  The previous possible hint
     */
    public String getPreviousHint() {
        if ( (index - 1) < 0) {
            index = possibleHits.length - 1;
        } else {
            index--;
        }
        return possibleHits[index];
    }
}
