package me.greenadine.echobot.handlers.slots;

import java.util.ArrayList;
import java.util.List;

public class SlotsResult {

    private int bet;

    private SlotEntry slot1;
    private SlotEntry slot2;
    private SlotEntry slot3;
    private SlotEntry slot4;

    private int points;
    private int reward;
    private double multiplier;

    private List<SlotCombo> combos;

    public SlotsResult(SlotEntry s1, SlotEntry s2, SlotEntry s3, SlotEntry s4, int b) {
        slot1 = s1;
        slot2 = s2;
        slot3 = s3;
        slot4 = s4;

        points = s1.getPoints() + s2.getPoints() + s3.getPoints() + s4.getPoints();

        bet = b;

        combos = new ArrayList<>();
        determineCombos();
        calculateReward();
    }

    private void determineCombos() {
        for (SlotCombo combo : SlotCombo.values()) {
            if (hasSlots(combo.entries)) {
                combos.add(combo);
            }
        }

        multiplier = 0;

        for (SlotCombo combo : combos) {
            multiplier += combo.getMultiplier();
        }

        if (multiplier == 0) {
            multiplier = 1;
        }
    }

    private void calculateReward() {
        if (combos.size() > 0 ) { // If there are any combo's
            reward =  (int) ((points + (bet / 2) + points * combos.size()) * multiplier);
        } else { // If there are no combos
            reward = (int) ((points + (bet / 2)) * multiplier);
        }
    }

    /**
     * Return the reward.
     * @return int
     */
    public int getReward() {
        return reward;
    }

    /**
     * Get the bet.
     * @return int
     */
    public int getBet() {
        return bet;
    }

    /**
     * Get the total points from the slot entries.
     * @return int
     */
    public int getPoints() {
        return points;
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

    public boolean hasSlots(List<SlotEntry> entries) {
        for (SlotEntry entry : entries) {
            if (!hasSlot(entry)) {
                return false;
            }
        }

        return true;
    }

    public String formatSlots() {
        return slot1.getEmoji() + " | " + slot2.getEmoji() + " | " + slot3.getEmoji() + " | " + slot4.getEmoji();
    }
}
