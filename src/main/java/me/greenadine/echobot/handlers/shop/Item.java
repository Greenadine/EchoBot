package me.greenadine.echobot.handlers.shop;

public enum Item {

    POLL("Poll", "Create a 24-hour poll for the entire server to vote on (24-hour cooldown).", 2500, true),
    NICKNAME_CHANGE("Nickname Change", "Change someone's nickname for 24 hours.", 5000, true),
    STAFF_NICKNAME_CHANGE("Staff Nickname Change", "Change a staff member's nickname for 24 hours.", 20000, true),
    PRINCIPAL_NICKNAME_CHANGE("Principal Nickname Change", "Change the principal's nickname for 24 hours", 30000, true),
    TRUSTED_ROLE("Trusted Role", "Get the <@&667795907424813056> role, unlocking new fun commands with Echo.", 7500, false),
    KEVINS_LEWD_PICTURES("Kevin's Lewd Pictures", "Get some *lewd* pictures of the principal~", 50000, false),

    CARD_PACK_COMMON("Repeat Trading Card Pack (Common)", "Get a trading card pack containing 5 random Repeat character cards!", 2500, true),
    CARD_PACK_RARE("Repeat Trading Card Pack (Rare)", "Get a trading card pack containing 5 random Repeat character cards!", 3500, true),
    CARD_BOX_COMMON("Repeat Trading Card Box (Common)", "Get a trading card box containing 10 random Repeat character cards! ", 5000, true),
    CARD_BOX_RARE("Repeat Trading Card Box (Rare)", "Get a trading card box containing 10 random Repeat character cards! ", 7000, true),
    ;


    private String name;
    private String description;
    private int price;

    private boolean inventory;

    Item(String name, String description, int price, boolean inventory) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.inventory = inventory;
    }

    /**
     * Get the display name of the item.
     * @return String
     */
    public String getName() { return name; }

    /**
     * Get the description of the item.
     * @return String
     */
    public String getDescription() { return description; }

    /**
     * Get the buying price in Gold of the item
     * @return int
     */
    public int getPrice() { return price; }

    /**
     * Returns whether the item can be put into an inventory.
     * @return boolean
     */
    public boolean isInventory() { return inventory; }
}
