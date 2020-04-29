package me.greenadine.echobot.cardgame;

public enum Rarity {

    LEGENDARY("Legendary", 4),
    VERY_RARE("Very Rare", 3),
    RARE("Rare", 2),
    UNCOMMON("Uncommon", 1),
    COMMON("Common", 0),
    ;

    String name;
    int id;

    Rarity(String n, int i) {
        name = n;
        id = i;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Get the ID of the rarity.
     * @return int
     */
    public int getId() {
        return id;
    }
}
