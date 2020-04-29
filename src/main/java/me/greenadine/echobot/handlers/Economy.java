package me.greenadine.echobot.handlers;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.user.User;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class Economy {

    private HashMap<Long, Integer> econ = load();

    /**
     * Returns whether the user is already in map.
     * @param user The user
     * @return boolean
     */
    public boolean hasData(User user) {
        return econ.containsKey(user.getId());
    }

    /**
     * Add a user to the map if the user is not mapped yet.
     * @param user The user
     */
    public void register(User user) {
        if (!hasData(user)) {
            econ.put(user.getId(), 100);
            reload();
        }
    }

    /**
     * Returns the Gold balance of the user.
     * @param user The user
     * @return int
     */
    public int getBalance(User user) {
        if (hasData(user)) {
            return econ.get(user.getId());
        } else {
            return -1;
        }
    }

    /**
     * Returns the Gold balance of a user, formatted with thousands separator.
     * @param user The user
     * @return String
     */
    public String getFormattedBalance(User user) {
        if (hasData(user)) {
            return String.format("%,d", econ.get(user.getId()));
        } else {
            return "null";
        }
    }

    /**
     * Returns whether a user has a certain amount of Gold or more.
     * @param user The user
     * @param amount The minimum balance to check for
     * @return boolean
     */
    public boolean hasBalance(User user, int amount) {
        if (hasData(user)) {
            return getBalance(user) >= amount;
        } else {
            return false;
        }
    }

    /**
     * Set a user's balance to the given amount. Returns true if success, false if otherwise.
     * @param user The user
     * @param amount The amount of Gold to set their balance to
     * @return
     */
    public boolean set(User user, int amount) {
        if (hasData(user)) {
            if (amount > EchoBot.settings.getEconomyGoldLimit()) {
                amount = EchoBot.settings.getEconomyGoldLimit();
            }

            econ.put(user.getId(), amount);
            reload();
            return true;
        } else {
            return false;
        }
    }


    /**
     * Adds an amount to a user's balance. Returns true if success, false if otherwise.
     * @param user The user
     * @param amount The amount to add to balance
     * @return boolean
     */
    public boolean add(User user, int amount) {
        if (hasData(user)) {
            econ.put(user.getId(), econ.get(user.getId()) + amount);
            reload();

            if (getBalance(user) > EchoBot.settings.getEconomyGoldLimit()) {
                set(user, EchoBot.settings.getEconomyGoldLimit());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retracts an amount from a user's balance. Returns true if success, false if otherwise.
     * @param user The user
     * @param amount The amount to withdraw from balance
     * @return boolean
     */
    public boolean withdraw(User user, int amount) {
        if (hasData(user) && hasBalance(user, amount)) {
            econ.put(user.getId(), econ.get(user.getId()) - amount);
            reload();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove a user from data memory. Returns true if success, false if otherwise.
     * @param user The user
     * @return boolean
     */
    public boolean clear(User user) {
        if (hasData(user)) {
            econ.remove(user.getId());
            reload();
            return true;
        } else {
            return false;
        }
    }

    public void clearAll() {
        for (Long id : econ.keySet()) {
            econ.put(id, 0);
        }

        reload();
    }

    /**
     * Returns at what position the user is in the Gold leaderboard.
     * @param user The user
     * @return int
     */
    public int getTopPosition(User user) {
        LinkedHashMap<Long, Integer> sorted = new LinkedHashMap<>();

        econ.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));

        return new ArrayList<>(sorted.keySet()).indexOf(user.getId()) + 1;
    }

    /**
     * Get the users with the highest amounts of Gold in the server.
     * @param range The amount of users to retrieve.
     * @return List<Entry<Long, Integer>>
     * @throws NoSuchElementException
     */
    public List<Entry<Long, Integer>> getTop(int range) throws NoSuchElementException {
        return findGreatest(econ, range);
    }

    /**
     * Retrieve the entries with the highest values within a range.
     * @param map The map to retrieve the highest values from
     * @param n The amount of values to retrieve
     * @param <K> The key
     * @param <V> The value
     * @return List<Entry<K, V>>
     */
    private static <K, V extends Comparable<? super V>> List<Entry<K, V>> findGreatest(Map<K, V> map, int n) {
        Comparator<? super Entry<K, V>> comparator = new Comparator<Entry<K, V>>() {
                    @Override
                    public int compare(Entry<K, V> e0, Entry<K, V> e1)
                    {
                        V v0 = e0.getValue();
                        V v1 = e1.getValue();
                        return v0.compareTo(v1);
                    }
                };
        PriorityQueue<Entry<K, V>> highest = new PriorityQueue<Entry<K,V>>(n, comparator);

        for (Entry<K, V> entry : map.entrySet()) {
            highest.offer(entry);
            while (highest.size() > n) {
                highest.poll();
            }
        }

        List<Entry<K, V>> result = new ArrayList<>();

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
        econ = load();
    }

    /**
     * Serialize and save economy data to file.
     */
    private void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/econ.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(econ);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Failed to save data to file 'econ.ser'. Reason: IOException.");
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
            FileInputStream fileIn = new FileInputStream("data/econ.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap) in.readObject();
        } catch (EOFException e) {
            System.out.println("Nothing found in file 'econ.ser'.");
            map = new HashMap<>();
        } catch (IOException e) {
            System.out.println("Failed to load data from file 'econ.ser'. Reason: IOException.");
            e.printStackTrace();
            map = new HashMap<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load data from file 'econ.ser'. Reason: Class not found.");
            e.printStackTrace();
            map = new HashMap<>();
        }

        return map;
    }
}