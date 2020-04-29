package me.greenadine.echobot.handlers.valentine;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.user.User;

import java.io.*;
import java.util.HashMap;

public class Valentine {

    private String id = EchoBot.settings.getCurrentValentine();

    private HashMap<Long, RepeatCharacter> valentine = load();

    public void update() {
        save();
        id = EchoBot.settings.getCurrentValentine();
        valentine = load();
    }

    /**
     * Save data to file, and reload it into the bot.
     */
    private void reload() {
        save();
        valentine = load();
    }

    /**
     * Returns whether the user is already in map.
     * @param user The user
     * @return boolean
     */
    public boolean hasValentine(User user) {
        return valentine.containsKey(user.getId());
    }

    /**
     * Give the user a valentine.
     * @param user The user
     * @return RepeatCharacter
     */
    public RepeatCharacter giveValentine(User user) {
        RepeatCharacter rc = RepeatCharacter.getRandom();

        valentine.put(user.getId(), rc);
        reload();

        return rc;
    }

    /**
     * Get the user's valentine if they have one.
     * @param user The user
     * @return RepeatCharacter
     */
    public RepeatCharacter getValentine(User user) {
        if (hasValentine(user)) {
            return valentine.get(user.getId());
        } else {
            return null;
        }
    }

    /**
     * Remove a user from data memory. Returns true if success, false if otherwise.
     * @param user The user
     * @return boolean
     */
    public boolean clear(User user) {
        if (hasValentine(user)) {
            valentine.remove(user.getId());
            reload();
            return true;
        } else {
            return false;
        }
    }

    public void clearAll() {
        for (Long id : valentine.keySet()) {
            valentine.remove(id);
        }

        reload();
    }

    /**
     * Serialize and save economy data to file.
     */
    private void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/valentine/" + id + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(valentine);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Failed to save data to Valentine's  file '" + id + ".ser'. Reason: IOException.");
            e.printStackTrace();
        }
    }

    /**
     * Deserialize data from file and load into bot.
     * @return HashMap<User, Integer>
     */
    private HashMap<Long, RepeatCharacter> load(){
        HashMap<Long, RepeatCharacter> map;

        try {
            FileInputStream fileIn = new FileInputStream("data/valentine/" + id + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap) in.readObject();
        } catch (EOFException e) {
            System.out.println("Nothing found in Valentine's file '" + id + ".ser'.");
            map = new HashMap<>();
        } catch (IOException e) {
            System.out.println("Failed to load data from Valentine's file '" + id + ".ser'. Reason: IOException.");
            e.printStackTrace();
            map = new HashMap<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load data from Valentine's file '" + id + ".ser'. Reason: Class not found.");
            e.printStackTrace();
            map = new HashMap<>();
        }

        return map;
    }
}
