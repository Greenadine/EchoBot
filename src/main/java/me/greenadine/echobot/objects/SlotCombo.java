package me.greenadine.echobot.objects;

public enum SlotCombo {

    WISH_AND_WISHER("Wish and Wisher", 2),
    CAFE_DU_COEUR("Café du Coeur", 2),
    HOMESCHOOLED("Homeschooled", 2),
    FLUSTERED_BUNNY("Flustered Bunny", 2),
    THERES_LUBE_IN_THE_DRAWER("There's Lube in the Drawer", 2.5),
    TEASE_THE_BUNNY("Tease the Bunny", 4),
    THE_GANG("The Gang", 4);

    String name;
    double multiplier;

    SlotCombo(String n, double m) {
        name = n;
        multiplier = m;
    }

    public String getName() {
        return name;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
