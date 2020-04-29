package me.greenadine.echobot.handlers.slots;

import java.util.*;

public class SlotMachine {

    /**
     * // Points list:
     * Emoji           | Points | Frequency in combos
     * ----------------|--------|--------------------
     * BlackCat        | 25     | X
     * Cecilia         | 10     | X
     * Echo            | 25     | X
     * Euca            | 15     | XXX
     * Halley          | 25     | X
     * Hershel         | 10     | X
     * HershelThumbsUp | 25     | X
     * HershelWtf      | 30     | X
     * Jinny           | 20     | XX
     * Owen            | 20     | X
     * OwenSmile       | 30     | X
     * Phillip         | 20     | XX
     * PhillipSmug     | 30     | X
     * Remnant         | 5      |
     * Samuel          | 10     | X
     * Sissel          | 20     | XX
     * SisselBlush     | 25     | X
     * SisselHappy     | 25     | X
     * SisselShy       | 30     | X
     *
     *
     * // Combo list:
     * Name                       | Possible combos                      | Multiplier
     * ---------------------------|--------------------------------------|---------------
     * Wish and Wisher            | Echo + Euca / Halley + Jinny         | 1.5
     * Café du Coeur              | Sissel + Hershel                     | 1.5
     * Homeschooled               | SisselShy + Jinny                    | 1.5
     * Thick as Thieves           | Phillip + OwenSmile                  | 1.5
     * Butlery Business           | Owen + Samuel                        | 1.5
     * Secret Identity            | PhillipSmug + BlackCat               | 1.5
     * The Bradleys               | SisselHappy + Hershel + Cecilia      | 2
     * There's Lube in the Drawer | Euca + SisselBlush + HershelThumbsUp | 2
     * The Gang                   | Euca + Sissel + Owen + Phillip       | 2.5
     */

    public SlotsResult roll(int bet) {
        SlotEntry slot1 = SlotEntry.getRandom();
        SlotEntry slot2 = SlotEntry.getRandom();
        SlotEntry slot3 = SlotEntry.getRandom();
        SlotEntry slot4 = SlotEntry.getRandom();
        List<SlotEntry> slotResult = new ArrayList<>();
        slotResult.add(slot1);
        slotResult.add(slot2);
        slotResult.add(slot3);
        slotResult.add(slot4);

        return new SlotsResult(slot1, slot2, slot3, slot4, bet);
    }
}
