package me.greenadine.echobot.handlers.shop;

public enum ShopCategory {

    GENERAL(1, "General"),
    CARD_PACKS(2, "Card Packs"),
    ;

    private int id;
    private String name;

    ShopCategory(int i, String n) {
        id = i;
        name = n;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ShopCategory getFromId(int id) {
        for (ShopCategory category : values()) {
            if (category.getId() == id) {
                return category;
            }
        }

        return null;
    }
}
