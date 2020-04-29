package me.greenadine.echobot.handlers.shop;

import org.javacord.api.entity.user.User;

import java.io.*;
import java.util.HashMap;

public class Inventories {

    private HashMap<Long, HashMap<Item, Integer>> inventories = load();

    /**
     * Returns whether the user is already in map.
     * @param user The user
     * @return boolean
     */
    public boolean hasData(User user) {
        return inventories.containsKey(user.getId());
    }

    /**
     * Add a user to the map if the user is not mapped yet.
     * @param user The user
     */
    public void register(User user) {
        if (!hasData(user)) {
            HashMap<Item, Integer> map = new HashMap<>();

            for (Item item : Item.values()) {
                map.put(item, 0);
            }

            inventories.put(user.getId(), map);
            reload();
        }
    }

    /**
     * Get the contents of a user's inventory.
     * @param user The user
     * @return HashMap<Item, Integer>
     */
    public HashMap<Item, Integer> getInventory(User user) {
        return inventories.get(user.getId());
    }

    /**
     * Add an item to a user's inventory. Returns true if success, false if otherwise.
     * @param user The user
     * @param item The item
     * @return boolean
     */
    public boolean addItem(User user, Item item) {
        if (hasData(user)) {
            try {
                inventories.get(user.getId()).put(item, inventories.get(user.getId()).get(item) + 1);
            } catch (Exception ex) {
                System.out.println("Failed to add item '" + item.getName() + "' to inventory of user " + user.getDiscriminatedName() + ".");
                ex.printStackTrace();
                return false;
            }

            reload();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes an item from a user's inventory. Returns true if success, false if otherwise.
     * @param user The user
     * @param item The item
     * @return boolean
     */
    public boolean removeItem(User user, Item item) {
        if (hasData(user)) {
            try {
                inventories.get(user.getId()).put(item, inventories.get(user.getId()).get(item) - 1);
            } catch (Exception ex) {
                System.out.println("Failed to add item '" + item.getName() + "' to inventory of user " + user.getDiscriminatedName() + ".");
                ex.printStackTrace();
                return false;
            }
        }

        return false;
    }

    /**
     * Save data to file, and reload it into the bot.
     */
    private void reload() {
        save();
        inventories = load();
    }

    /**
     * Serialize and save economy data to file.
     */
    private void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/inventories.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(inventories);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Failed to save data to file 'inventories.ser'. Reason: IOException.");
            e.printStackTrace();
        }
    }

    /**
     * Deserialize data from file and load into bot.
     * @return HashMap<User, Integer>
     */
    private HashMap<Long, HashMap<Item, Integer>> load(){
        HashMap<Long, HashMap<Item, Integer>> map;

        try {
            FileInputStream fileIn = new FileInputStream("data/inventories.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap) in.readObject();
        } catch (EOFException e) {
            System.out.println("Nothing found in file 'inventories.ser'.");
            map = new HashMap<>();
        } catch (IOException e) {
            System.out.println("Failed to load data from file 'inventories.ser'. Reason: IOException.");
            e.printStackTrace();
            map = new HashMap<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load data from file 'inventories.ser'. Reason: Class not found.");
            e.printStackTrace();
            map = new HashMap<>();
        }

        return map;
    }
}
