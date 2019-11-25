package me.greenadine.echobot.objects;

import java.util.ArrayList;
import java.util.List;

public class SlotsResult {

    private int bet;

    private SlotEntry slot1;
    private SlotEntry slot2;
    private SlotEntry slot3;
    private SlotEntry slot4;

    private int slotPoints;
    private int points;
    private double multiplier;

    private List<SlotCombo> combos;

    public SlotsResult(SlotEntry s1, SlotEntry s2, SlotEntry s3, SlotEntry s4, int p, int b) {
        slot1 = s1;
        slot2 = s2;
        slot3 = s3;
        slot4 = s4;

        slotPoints = p;
        points = p;
        bet = b;

        combos = new ArrayList<>();
        determineCombos();
    }

    private void determineCombos() {
        // Euca & Echo: Wish and Wisher
        if ((hasSlot(SlotEntry.EUCA) && hasSlot(SlotEntry.ECHO)) || (hasSlot(SlotEntry.JINNY) && hasSlot(SlotEntry.HALLEY))) {
            combos.add(SlotCombo.WISH_AND_WISHER);
        }

        // Sissel & Hershel: Café Combo
        if (hasSlot(SlotEntry.SISSEL) && hasSlot(SlotEntry.HERSHEL)) {
            combos.add(SlotCombo.CAFE_DU_COEUR);
        }

        // Sissel & Jinny: Homeschooled
        if (hasSlot(SlotEntry.SISSEL) && hasSlot(SlotEntry.JINNY)) {
            combos.add(SlotCombo.HOMESCHOOLED);
        }

        // Euca & Sissel: Flusterbunny
        if (hasSlot(SlotEntry.EUCA) && hasSlot(SlotEntry.SISSEL)) {
            combos.add(SlotCombo.FLUSTERBUNNY);
        }

        if (hasSlot(SlotEntry.SISSEL) && hasSlot(SlotEntry.HERSHEL) && hasSlot(SlotEntry.CECILIA)) {
            combos.add(SlotCombo.THE_BRADLEYS);
        }

        // Euca & Sissel & Hershel: There's Lube in the Drawer
        if (hasSlot(SlotEntry.EUCA) && hasSlot(SlotEntry.SISSEL) && (hasSlot(SlotEntry.HERSHEL) || hasSlot(SlotEntry.HERSHELWTF))) {
            combos.add(SlotCombo.THERES_LUBE_IN_THE_DRAWER);

            combos.removeIf(combo -> combo == SlotCombo.FLUSTERBUNNY || combo == SlotCombo.CAFE_DU_COEUR);
        }

        // Sissel & Euca & Jinny * Hershel: Tease the Bunny
        if (hasSlot(SlotEntry.SISSEL) && hasSlot(SlotEntry.EUCA) && hasSlot(SlotEntry.JINNY) && hasSlot(SlotEntry.HERSHEL)) {
            combos.add(SlotCombo.TEASE_THE_BUNNY);

            combos.removeIf(combo -> combo == SlotCombo.CAFE_DU_COEUR || combo == SlotCombo.FLUSTERBUNNY || combo == SlotCombo.HOMESCHOOLED);
        }

        // Euca & Owen & Phillip & Sissel: The Gang
        if (hasSlot(SlotEntry.EUCA) && hasSlot(SlotEntry.OWEN) && hasSlot(SlotEntry.PHILLIP) && hasSlot(SlotEntry.SISSEL)) {
            combos.add(SlotCombo.THE_GANG);

            combos.removeIf(combo -> combo == SlotCombo.FLUSTERBUNNY);
        }

        multiplier = 0;

        for (SlotCombo combo : combos) {
            multiplier += combo.getMultiplier();
        }

        if (multiplier == 0) {
            multiplier = 1;
        }

        points *= multiplier;
    }

    /**
     * Calculate and return the reward.
     * @return int
     */
    public int getReward() {
        return (int) (points / 12 * (bet / 10) * multiplier);
    }

    /**
     * Get the bet.
     * @return int
     */
    public int getBet() {
        return bet;
    }

    /**
     * Get the amount of points made in this result.
     * @return int
     */
    public int getPoints() {
        return points;
    }

    /**
     * Get the total points from the slot entries.
     * @return int
     */
    public int getSlotPoints() {
        return slotPoints;
    }

    /**
     * Get the points multiplier of this result.
     * @return double
     */
    public double getMultiplier() {
        return multiplier;
    }

    /**
     * Returns whether the result has any slot combos.
     * @return boolean
     */
    public boolean hasCombos() {
        return !combos.isEmpty();
    }

    /**
     * Returns all the slot combos in the result.
     * @return List<SlotCombo>
     */
    public List<SlotCombo> getCombos() {
        return combos;
    }

    /**
     * Get a certain slot from the result.
     * @param i The slot number to get
     * @return String
     */
    public SlotEntry getSlot(int i) {
        switch (i) {
            case 1:
                return slot1;
            case 2:
                return slot2;
            case 3:
                return slot3;
            case 4:
                return slot4;
            default:
                return slot1;
        }
    }

    /**
     * Check whether the result contains a certain slot.
     * @param value The slot to check for.
     * @return boolean
     */
    public boolean hasSlot(SlotEntry value) {
        return slot1 == value || slot2 == value || slot3 == value || slot4 == value;
    }

    public String formatSlots() {
        return slot1.getEmoji() + " | " + slot2.getEmoji() + " | " + slot3.getEmoji() + " | " + slot4.getEmoji();
    }
}
