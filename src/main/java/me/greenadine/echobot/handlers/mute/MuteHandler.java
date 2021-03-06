package me.greenadine.echobot.handlers.mute;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

import java.io.*;
import java.util.HashMap;
import java.util.Set;
import java.util.StringJoiner;

public class MuteHandler {

    private HashMap<Long, Integer> mute = load();

    private Role role = EchoBot.bot.getRoleById(645763033905365022L).get();

    /**
     * Returns whether the user is muted.
     * @param user The user
     * @return boolean
     */
    public boolean isMuted(User user) {
        return mute.containsKey(user.getId());
    }

    /**
     * Return whether a user has the Muted role.
     * @param user The user
     * @return boolean
     */
    private boolean hasRole(User user) {
        return role.getUsers().contains(user);
    }

    /**
     * Give a user the Muted role. Returns true if success, false if otherwise.
     * @param user The user
     * @return boolean
     */
    private boolean giveRole(User user) {
        if (!hasRole(user)) {
            role.addUser(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes the Muted role from a user. Returns true if success, false if otherwise.
     * @param user The user
     * @return boolean
     */
    private boolean removeRole(User user) {
        if (hasRole(user)) {
            role.removeUser(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Mute a user. Returns true if success, false if otherwise.
     * @param user The user
     * @param duration The duration for the mute
     * @return boolean
     */
    public boolean mute(User user, int duration) {
        if (!isMuted(user)) {
            boolean roleGiven = giveRole(user);

            mute.put(user.getId(), duration * 60000);
            reload();
            return roleGiven;
        } else {
            return false;
        }
    }

    /**
     * Unmutes a muted user. Returns true if success, false if otherwise.
     * @param user The user
     * @return boolean
     */
    public boolean unmute(User user) {
        if (isMuted(user)) {
            boolean roleRemoved = removeRole(user);

            mute.remove(user.getId());
            reload();
            return roleRemoved;
        } else {
            return false;
        }
    }

    /**
     * Returns the remaining mute duration of a muted user.
     * @param user The user
     * @return int
     */
    public int getMuteDuration(User user) {
        if (isMuted(user)) {
            return mute.get(user.getId());
        } else {
            return -1;
        }
    }

    /**
     * Get the remaining mute duration of a muted user displayed in hours, minutes and seconds.
     * @param user The user
     * @return String
     */
    public String getFormattedMuteDuration(User user) {
        if (isMuted(user)) {
            int duration = getMuteDuration(user);

            int seconds = (duration / 1000) % 60;
            int minutes = ((duration / (1000*60)) % 60);
            int hours   = ((duration / (1000*60*60)) % 24);

            StringJoiner joiner = new StringJoiner(", ");

            if (hours > 1) {
                joiner.add(hours + " hours");
            } else if (hours == 1) {
                joiner.add(hours + " hour");
            }

            if (minutes > 1) {
                joiner.add(minutes + " minutes");
            } else if (minutes == 1) {
                joiner.add(minutes + " minute");
            }

            if (seconds > 1) {
                joiner.add(seconds + " seconds");
            } else if (seconds == 1) {
                joiner.add(seconds + " second");
            }

            if (hours < 1 && minutes < 1 && seconds < 1 && duration > 1) {
                joiner.add(duration + " milliseconds");
            }

            return joiner.toString();
        } else {
            return "(user not muted)";
        }
    }

    /**
     * Reduces the remaining duration for a mute. Returns true if success, false if otherwise.
     * @param user The user
     * @param amount The amount to reduce the duration with
     * @return boolean
     */
    boolean reduceMuteDuration(User user, int amount) {
        if (isMuted(user)) {
            mute.put(user.getId(), mute.get(user.getId()) - amount);

            if (mute.get(user.getId()) <= 0) {
                unmute(user);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the muted user's ID's.
     * @return Set<Long>
     */
    public Set<Long> getMutedUsers() {
        return mute.keySet();
    }

    /**
     * Save data to file, and reload it into the bot.
     */
    public void reload() {
        save();
        mute = load();
    }

    /**
     * Serialize and save data to file.
     */
    private void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/muted.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(mute);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Failed to save data to file 'muted.ser'. Reason: IOException.");
            e.printStackTrace();
        }
    }

    /**
     * Deserialize data from file and load into bot.
     * @return HashMap<User, Integer>
     */
    private HashMap<Long, Integer> load(){
        HashMap<Long, Integer> map;

        try {
            FileInputStream fileIn = new FileInputStream("data/muted.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap) in.readObject();
        } catch (EOFException e) {
            System.out.println("Nothing found in file 'muted.ser'.");
            map = new HashMap<>();
        } catch (IOException e) {
            System.out.println("Failed to load data from file 'muted.ser'. Reason: IOException.");
            e.printStackTrace();
            map = new HashMap<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load data from file 'muted.ser'. Reason: Class not found.");
            e.printStackTrace();
            map = new HashMap<>();
        }

        return map;
    }
}
