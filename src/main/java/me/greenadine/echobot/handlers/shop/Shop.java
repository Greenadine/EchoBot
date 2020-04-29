package me.greenadine.echobot.handlers.shop;

import java.util.*;

public class Shop {

    private HashMap<ShopCategory, List<Item>> itemList;

    public Shop() {
        itemList = new LinkedHashMap<>();

        // Add shop items
        addShopItem(ShopCategory.GENERAL, Item.POLL);
        addShopItem(ShopCategory.GENERAL, Item.NICKNAME_CHANGE);
        addShopItem(ShopCategory.GENERAL, Item.STAFF_NICKNAME_CHANGE);
        addShopItem(ShopCategory.GENERAL, Item.PRINCIPAL_NICKNAME_CHANGE);
        addShopItem(ShopCategory.GENERAL, Item.TRUSTED_ROLE);
        addShopItem(ShopCategory.GENERAL, Item.KEVINS_LEWD_PICTURES);

        addShopItem(ShopCategory.CARD_PACKS, Item.CARD_PACK_COMMON);
        addShopItem(ShopCategory.CARD_PACKS, Item.CARD_PACK_RARE);
        addShopItem(ShopCategory.CARD_PACKS, Item.CARD_BOX_COMMON);
        addShopItem(ShopCategory.CARD_PACKS, Item.CARD_BOX_RARE);
    }

    /**
     * Get all the items under a category.
     * @param category The category
     * @return List<Item>
     */
    public List<Item> getItems(ShopCategory category) {
        return itemList.get(category);
    }

    /**
     * Get the shop item based on the given name.
     * @param name The name
     * @return Optional<Item>
     */
    public Optional<Item> getItem(String name) {
        for (ShopCategory category : itemList.keySet()) {
            for (Item item : itemList.get(category)) {
                if (item.getName().equalsIgnoreCase(name)) {
                    return Optional.of(item);
                }
            }
        }

        return Optional.empty();
    }

    private void addShopItem(ShopCategory category, Item item) {
        itemList.putIfAbsent(category, new ArrayList<>());

        itemList.get(category).add(item);
    }
}