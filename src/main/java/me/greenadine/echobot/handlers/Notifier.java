package me.greenadine.echobot.handlers;

import org.javacord.api.entity.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Notifier {

    private List<Long> repeat = loadRepeatList();
    private List<Long> repeat_patron = loadRepeatPatronList();
    private List<Long> patreon = loadPatreonList();

    /**
     * Returns whether the user is in the Repeat notifications list.
     * @param user The user
     * @return boolean
     */
    public boolean isInRepeat(User user) {
        return repeat.contains(user.getId());
    }

    /**
     * Adds a user to the Repeat notifications list. Returns true if success, false if otherwise.
     * @param user The user
     * @return boolean
     */
    public boolean addToRepeat(User user) {
        if (!isInRepeat(user)) {
            repeat.add(user.getId());
            saveRepeatList();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a user from the Repeat notifications list. Returns true if success, false if otherwise.
     * @param user The user
     * @return boolean
     */
    public boolean removeFromRepeat(User user) {
        if (isInRepeat(user)) {
            repeat.remove(user.getId());
            saveRepeatList();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the list of ID's of users in the Repeat notifcations list.
     * @return List<Long>
     */
    public List<Long> getRepeatList() {
        return repeat;
    }

    /**
     * Returns whether the user is in the Repeat patron notifications list.
     * @return boolean
     */
    public boolean isInRepeatPatron(User user) {
        return repeat_patron.contains(user.getId());
    }

    /**
     * Adds a user to the Repeat patron notifications list. Returns true if success, false if otherwise.
     * @param user The user
     * @return boolean
     */
    public boolean addToRepeatPatron(User user) {
        if (!isInRepeatPatron(user)) {
            repeat_patron.add(user.getId());
            saveRepeatPatronList();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a user from the Repeat patron notifications list. Returns true if success, false if otherwise.
     * @param user The user
     * @return boolean
     */
    public boolean removeFromRepeatPatron(User user) {
        if (isInRepeatPatron(user)) {
            repeat_patron.remove(user.getId());
            saveRepeatPatronList();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the list of ID's of users in the Repeat patron notifications list.
     * @return List<Long>
     */
    public List<Long> getRepeatPatronList() {
        return repeat_patron;
    }

    /**
     * Return whether the user is in the Patreon notifications list.
     * @return boolean
     */
    public boolean isInPatreon(User user) {
        return patreon.contains(user.getId());
    }

    /**
     * Adds a user to the Patreon notifcations list. Returns true if success, false if otherwise.
     * @param user The user
     * @return boolean
     */
    public boolean addToPatreon(User user) {
        if (!isInPatreon(user)) {
            patreon.add(user.getId());
            savePatreonList();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove a user from the Patreon notifications list. Returns true if success, false if otherwise.
     * @param user The user
     * @return boolean
     */
    public boolean removeFromPatreon(User user) {
        if (isInPatreon(user)) {
            patreon.remove(user.getId());
            savePatreonList();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the list of ID's of users in the Patreon notifications list.
     * @return List<Long>
     */
    public List<Long> getPatreonList() {
        return patreon;
    }

    /**
     * Remove a user from all notification lists.
     * @param user The user
     */
    public void removeFromAll(User user) {
        if (isInRepeat(user)) {
            repeat.remove(user.getId());
        }

        if (isInRepeatPatron(user)) {
            repeat_patron.remove(user.getId());
        }

        if (isInPatreon(user)) {
            patreon.remove(user.getId());
        }
    }

    /**
     * Add a user to all notification lists.
     * @param user The user
     */
    public void addToAll(User user) {
        if (!isInRepeat(user)) {
            repeat.add(user.getId());
        }

        if (!isInRepeatPatron(user)) {
            repeat_patron.add(user.getId());
        }

        if (!isInPatreon(user)) {
            patreon.add(user.getId());
        }
    }

    // Data methods //

    /**
     * Serialize and save Repeat notifications list to file.
     */
    private void saveRepeatList() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/notifier/repeat.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(repeat);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Failed to save data to file 'notifier/repeat.ser'. Reason: IOException.");
            e.printStackTrace();
        }
    }

    /**
     * Serialize and save Repeat notifications list to file.
     */
    private void saveRepeatPatronList() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/notifier/repeat_patron.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(repeat_patron);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Failed to save data to file 'notifier/repeat_patron.ser'. Reason: IOException.");
            e.printStackTrace();
        }
    }

    /**
     * Serialize and save Repeat notifications list to file.
     */
    private void savePatreonList() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/notifier/patreon.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(patreon);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Failed to save data to file 'notifier/patreon.ser'. Reason: IOException.");
            e.printStackTrace();
        }
    }

    /**
     * Deserialize Repeat notifications list from file and load into bot.
     * @return List<Long>
     */
    private List<Long> loadRepeatList(){
        List<Long> list;

        try {
            FileInputStream fileIn = new FileInputStream("data/notifier/repeat.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            list = (List) in.readObject();
        } catch (EOFException e) {
            System.out.println("Nothing found in file 'notifier/repeat.ser'.");
            list = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Failed to load data from file 'notifier/repeat.ser'. Reason: IOException.");
            e.printStackTrace();
            list = new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load data from file 'notifier/repeat.ser'. Reason: Class not found.");
            e.printStackTrace();
            list = new ArrayList<>();
        }

        return list;
    }

    /**
     * Deserialize Repeat patron notifications list from file and load into bot.
     * @return List<Long>
     */
    private List<Long> loadRepeatPatronList(){
        List<Long> list;

        try {
            FileInputStream fileIn = new FileInputStream("data/notifier/repeat_patron.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            list = (List) in.readObject();
        } catch (EOFException e) {
            System.out.println("Nothing found in file 'notifier/repeat_patron.ser'.");
            list = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Failed to load data from file 'notifier/repeat_patron.ser'. Reason: IOException.");
            e.printStackTrace();
            list = new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load data from file 'notifier/repeat_patron.ser'. Reason: Class not found.");
            e.printStackTrace();
            list = new ArrayList<>();
        }

        return list;
    }

    /**
     * Deserialize Patreon notifications list from file and load into bot.
     * @return List<Long>
     */
    private List<Long> loadPatreonList(){
        List<Long> list;

        try {
            FileInputStream fileIn = new FileInputStream("data/notifier/patreon.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            list = (List) in.readObject();
        } catch (EOFException e) {
            System.out.println("Nothing found in file 'notifier/patreon.ser'.");
            list = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Failed to load data from file 'notifier/patreon.ser'. Reason: IOException.");
            e.printStackTrace();
            list = new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load data from file 'notifier/patreon.ser'. Reason: Class not found.");
            e.printStackTrace();
            list = new ArrayList<>();
        }

        return list;
    }
}