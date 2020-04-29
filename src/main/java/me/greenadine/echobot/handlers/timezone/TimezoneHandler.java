package me.greenadine.echobot.handlers.timezone;

import org.javacord.api.entity.user.User;

import java.io.*;
import java.util.HashMap;
import java.util.TimeZone;

public class TimezoneHandler {

    private HashMap<Long, TimeZone> timezones = load();

    /**
     * Get the timezones of all users.
     * @return HashMap<Long, Timezone>
     */
    public HashMap<Long, TimeZone> getTimezones() {
        return timezones;
    }

    /**
     * Returns whether the user already has a timezone set.
     * @param user The user
     * @return boolean
     */
    public boolean hasTimezoneSet(User user) {
        return timezones.containsKey(user.getId());
    }

    /**
     * Set the timezone of a user.
     * @param user The user
     */
    public void setTimezone(User user, TimeZone timeZone) {
        timezones.put(user.getId(), timeZone);
        reload();
    }

    /**
     * Get the timezone of a user.
     * @param user The user
     * @return TimeZone
     */
    public TimeZone getTimezone(User user) {
        return timezones.get(user.getId());
    }

    /**
     * Remove the timezone of a user.
     * @param user The user
     */
    public void clearTimezone(User user) {
        timezones.remove(user.getId());
        reload();
    }

    /**
     * Clear the timezones of all users.
     */
    public void clearAllTimezones() {
        timezones.clear();
        reload();
    }

    /**
     * Save data to file, and reload it into the bot.
     */
    private void reload() {
        save();
        timezones = load();
    }
    /**
     * Serialize and save timezones data to file.
     */
    private void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/timezones.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(timezones);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Failed to save data to file 'timezones.ser'. Reason: IOException");
            e.printStackTrace();
        }
    }

    /**
     * Deserialize data from file and load into bot.
     * @return HashMap<User, Integer>
     */
    private HashMap<Long, TimeZone> load(){
        HashMap<Long, TimeZone> map;

        try {
            FileInputStream fileIn = new FileInputStream("data/timezones.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap) in.readObject();
        } catch (EOFException e) {
            System.out.println("Nothing found in file 'timezones.ser'.");
            map = new HashMap<>();
        } catch (IOException e) {
            System.out.println("Failed to load data from file 'timezones.ser'. Reason: IOException.");
            e.printStackTrace();
            map = new HashMap<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load data from file 'timezones.ser'. Reason: Class not found.");
            e.printStackTrace();
            map = new HashMap<>();
        }

        return map;
    }
}
