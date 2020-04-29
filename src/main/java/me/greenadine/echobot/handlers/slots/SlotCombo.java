package me.greenadine.echobot.handlers.slots;

import java.util.ArrayList;
import java.util.List;

public enum SlotCombo {

    WISH_AND_WISHER_1("Wish and Wisher", "ECHO,EUCA", 2),
    WISH_AND_WISHER_2("Wish and Wisher", "HALLEY,JINNY", 2),
    CAFE_DU_COEUR("Café du Coeur", "SISSEL,HERSHEL", 2),
    HOMESCHOOLED("Homeschooled", "SISSELSHY,JINNY", 2),
    THICK_AS_THIEVES("Thick as Thieves", "PHILLIPSMUG,OWENSMILE", 2),
    BUTLERY_BUSINESS("Butlery Business", "OWEN,SAMUEL", 2),
    SECRET_IDENTITY("Secret Identity", "PHILLIP,BLACKCAT", 2),
    THE_BRADLEYS("The Bradleys", "SISSELHAPPY,HERSHEL,CECILIA", 2.5),
    THERES_LUBE_IN_THE_DRAWER("There's Lube in the Drawer", "EUCA,SISSELBLUSH,HERSHELTHUMBSUP", 2.5),
    THE_GANG("The Gang", "EUCA,SISSEL,OWEN,PHILLIP", 3);

    String name;
    List<SlotEntry> entries;
    double multiplier;

    SlotCombo(String n, String e, double m) {
        name = n;

        entries = new ArrayList<>();

        String[] sa = e.split(",");

        for (String s : sa) {
            SlotEntry entry = SlotEntry.valueOf(s);

            entries.add(entry);
        }

        multiplier = m;
    }

    public String getName() {
        return name;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
