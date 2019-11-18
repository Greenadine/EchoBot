package me.greenadine.echobot.handlers;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

import java.io.*;
import java.util.HashMap;
import java.util.Set;
import java.util.StringJoiner;

public class MuteHandler {

    private HashMap<Long, Long> mute = load();

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
    public boolean hasRole(User user) {
        return role.getUsers().contains(user);
    }

    /**
     * Give a user the Muted role. Returns true if success, false if otherwise.
     * @param user The user
     * @return boolean
     */
    public boolean giveRole(User user) {
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
    public boolean removeRole(User user) {
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
    public boolean mute(User user, long duration) {
        if (!isMuted(user)) {
            giveRole(user);

            mute.put(user.getId(), ((duration) * 60000L));
            reload();
            return true;
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
            removeRole(user);

            mute.remove(user.getId());
            reload();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the remaining mute duration of a muted user.
     * @param user The user
     * @return int
     */
    public long getMuteDuration(User user) {
        if (isMuted(user)) {
            return mute.get(user.getId()) / 60000L;
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
            long duration = getMuteDuration(user);

            int seconds = (int) (duration / 1000) % 60;
            int minutes = (int) ((duration / (1000*60)) % 60);
            int hours   = (int) ((duration / (1000*60*60)) % 24);

            System.out.println(duration + " milliseconds");
            System.out.println(seconds + " seconds");
            System.out.println(minutes + " minutes");
            System.out.println(hours + " hours");

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
    public boolean reduceMuteDuration(User user, long amount) {
        if (isMuted(user)) {
            mute.put(user.getId(), mute.get(user) - amount);

            if (mute.get(user) <= 0) {
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
    private void reload() {
        save();
        mute = load();
    }

    /**
     * Serialize and save data to file.
     */
    private void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\kevin\\IdeaProjects\\EchoBot\\src\\main\\java\\me\\greenadine\\echobot\\data\\muted.ser");
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
    private HashMap<Long, Long> load(){
        HashMap<Long, Long> map;

        try {
            FileInputStream fileIn = new FileInputStream("C:\\Users\\kevin\\IdeaProjects\\EchoBot\\src\\main\\java\\me\\greenadine\\echobot\\data\\muted.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap) in.readObject();

            System.out.println("Loaded data from file 'muted.ser'.");
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
