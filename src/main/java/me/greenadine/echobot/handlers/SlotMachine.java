package me.greenadine.echobot.handlers;

import me.greenadine.echobot.objects.SlotEntry;
import me.greenadine.echobot.objects.SlotsResult;

import java.util.*;

public class SlotMachine {

    /**
     * // Points list:
     * Cecilia    - 10 points
     * Echo       - 25 points
     * Euca       - 15 points
     * Halley     - 30 points
     * Hershel    - 10 points
     * Jinny      - 30 points
     * Owen       - 20 points
     * Phillip    - 20 points
     * Sissel     - 20 points
     * HershelWtf - 50 points
     *
     * // Combo list:
     * Echo + Euca / Halley + Jinny         = x2 (Wish and Wisher)
     * Sissel + Hershel                     = x2 (Café du Coeur)
     * Sissel + Jinny                       = x2 (Homeschooled)
     * Euca + Sissel                        = x2 (Flusterbunny)
     * Sissel + Hershel + Cecilia           = x2.5 (The Bradley's)
     * Euca + Sissel + Hershel/HershelWtf   = x2.5 (There's Lube in the Drawer)
     * Sissel + Euca + Jinny + Hershel      = x3 (Tease the Bunny)
     * Euca + Owen + Phillip + Sissel       = x3 (The Gang)
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

        int points = 0;

        for (SlotEntry entry : SlotEntry.values()) {
            int occurrences = Collections.frequency(slotResult, entry);

            points += occurrences * entry.getPoints();
        }

        return new SlotsResult(slot1, slot2, slot3, slot4, points, bet);
    }
}
