package me.greenadine.echobot.objects;

public enum SlotCombo {

    WISH_AND_WISHER("Wish and Wisher", 2),
    CAFE_DU_COEUR("Café du Coeur", 2),
    HOMESCHOOLED("Homeschooled", 2),
    FLUSTERBUNNY("Flusterbunny", 2),
    THE_BRADLEYS("The Bradley's", 2.5),
    THERES_LUBE_IN_THE_DRAWER("There's Lube in the Drawer", 2.5),
    TEASE_THE_BUNNY("Tease the Bunny", 3),
    THE_GANG("The Gang", 3);

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
