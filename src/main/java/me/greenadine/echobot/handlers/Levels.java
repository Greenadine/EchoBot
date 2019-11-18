package me.greenadine.echobot.handlers;

import org.javacord.api.entity.user.User;

import java.io.*;
import java.util.*;

public class Levels {

    private HashMap<Long, Integer> lvl = load();

    /**
     * Returns whether the user is already in map.
     * @param user The user
     * @return boolean
     */
    public boolean hasData(User user) {
        return lvl.containsKey(user.getId());
    }

    /**
     * Add a user to the map if the user is not mapped yet.
     * @param user The user
     */
    public void register(User user) {
        if (!hasData(user)) {
            lvl.put(user.getId(), 0);
            reload();
        }
    }

    /**
     * Get the XP amount of a user.
     * @param user The user
     * @return int
     */
    public Integer getXp(User user) {
        if (hasData(user)) {
            return lvl.get(user.getId());
        } else {
            return -1;
        }
    }

    /**
     * Set a user's amount of XP.
     * @param user The user
     * @param amount The amount to set their XP to
     * @return boolean
     */
    public boolean set(User user, int amount) {
        if (hasData(user)) {
            lvl.put(user.getId(), amount);
            reload();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gives a user XP. Returns true if success, false if otherwise.
     * @param user The user
     * @param amount The amount to add to balance
     * @return boolean
     */
    public boolean add(User user, int amount) {
        if (hasData(user)) {
            lvl.put(user.getId(), lvl.get(user.getId()) + amount);
            reload();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Subtracts an amount from a user's XP. Returns true if success, false if otherwise.
     * @param user The user
     * @param amount The amount of XP to subtract
     * @return boolean
     */
    public boolean subtract(User user, int amount) {
        if (hasData(user)) {
            lvl.put(user.getId(), lvl.get(user.getId()) - amount);
            reload();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculate a user's level based on XP.
     * @param xp The user's XP.
     * @return int
     */
    public int calculateLevel(int xp) {
        return (int) Math.pow(xp, 0.25);
    }

    /**
     * Remove a user from data memory.
     * @param user The user
     * @return boolean
     */
    public boolean clear(User user) {
        if (hasData(user)) {
            lvl.remove(user.getId());
            reload();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns at what position the user is in the Gold leaderboard.
     * @param user The user
     * @return int
     */
    public int getTopPosition(User user) {
        LinkedHashMap<Long, Integer> sorted = new LinkedHashMap<>();

        lvl.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));


        int pos = new ArrayList<Long>(sorted.keySet()).indexOf(user.getId()) + 1;

        return pos;
    }

    /**
     * Get the users with the highest amounts of Gold in the server.
     * @param range The amount of users to retrieve.
     * @return List<Entry<Long, Integer>>
     * @throws NoSuchElementException
     */
    public List<Map.Entry<Long, Integer>> getTop(int range) throws NoSuchElementException {
        return findGreatest(lvl, range);
    }

    /**
     * Retrieve the entries with the highest values within a range.
     * @param map The map to retrieve the highest values from
     * @param n The amount of values to retrieve
     * @param <K> The key
     * @param <V> The value
     * @return List<Entry<K, V>>
     */
    private static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> findGreatest(Map<K, V> map, int n) {
        Comparator<? super Map.Entry<K, V>> comparator = new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> e0, Map.Entry<K, V> e1)
            {
                V v0 = e0.getValue();
                V v1 = e1.getValue();
                return v0.compareTo(v1);
            }
        };
        PriorityQueue<Map.Entry<K, V>> highest = new PriorityQueue<Map.Entry<K,V>>(n, comparator);

        for (Map.Entry<K, V> entry : map.entrySet()) {
            highest.offer(entry);
            while (highest.size() > n) {
                highest.poll();
            }
        }

        List<Map.Entry<K, V>> result = new ArrayList<Map.Entry<K,V>>();

        while (highest.size() > 0) {
            result.add(highest.poll());
        }

        Collections.reverse(result);

        return result;
    }

    /**
     * Save data to file, and reload it into the bot.
     */
    private void reload() {
        save();
        lvl = load();
    }

    /**
     * Serialize and save data to file.
     */
    private void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\kevin\\IdeaProjects\\EchoBot\\src\\main\\java\\me\\greenadine\\echobot\\data\\levels.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(lvl);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Failed to save data to file 'levels.ser'. Reason: IOException.");
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
            FileInputStream fileIn = new FileInputStream("C:\\Users\\kevin\\IdeaProjects\\EchoBot\\src\\main\\java\\me\\greenadine\\echobot\\data\\levels.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap) in.readObject();
        } catch (EOFException e) {
            System.out.println("Nothing found in file 'levels.ser'.");
            map = new HashMap<>();
        } catch (IOException e) {
            System.out.println("Failed to load data from file 'levels.ser'. Reason: IOException.");
            e.printStackTrace();
            map = new HashMap<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load data from file 'levels.ser'. Reason: Class not found.");
            e.printStackTrace();
            map = new HashMap<>();
        }

        return map;
    }
}