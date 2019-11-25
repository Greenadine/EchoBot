package me.greenadine.echobot.handlers;

import org.javacord.api.entity.user.User;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

public class Lottery {

    private List<Long> lottery = load();

    private LocalTime targetTime = setInitTime();

    public boolean isEntered(User user) {
        return lottery.contains(user.getId());
    }

    /**
     * Enter a user to the lottery draw. Returns true if success, false if otherwise.
     * @param user The user
     * @return boolean
     */
    public boolean enter(User user) {
        if (!isEntered(user)) {
            lottery.add(user.getId());
            reload();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get a random user's ID from the entries list.
     * @return Long
     */
    public Long getRandomEntry() {
        if (lottery.size() > 0) {
            return lottery.get(new Random().nextInt(lottery.size()));
        } else {
            return 0L;
        }
    }

    /**
     * Get the amount of entries.
     * @return int
     */
    public int getSize() {
        return lottery.size();
    }


    /**
     * Clear entries.
     */
    public void clear() {
        lottery.clear();
        reload();
    }

    /**
     * Get initial time to compare to.
     * @return LocalTime
     */
    private LocalTime setInitTime() {
        LocalTime now = LocalTime.now(ZoneId.of("Europe/Berlin"));
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Berlin"));
        LocalDateTime tomorrowMidnight = LocalDateTime.of(today, LocalTime.of(1, 0, 0)).plusDays(1);
        LocalDateTime followingDayMidnight = tomorrowMidnight.plusDays(1);

        System.out.println("LocalTime now: " + now.toString());
        System.out.println("LocalDate now: " + today.toString());
        System.out.println("Tomorrow midnight: " + tomorrowMidnight.toString());
        System.out.println("Following day midnight: " + followingDayMidnight.toString());

        int offset = now.compareTo(tomorrowMidnight.toLocalTime());

        System.out.println("Offset: " + offset);

        if (offset < 0) {
            return tomorrowMidnight.toLocalTime();
        }  else {
            return followingDayMidnight.toLocalTime();
        }
    }

    /**
     * Returns integer -1 if before next draw.
     * Returns 0 if equal to time for next draw.
     * Returns 1 if after next draw.
     * @return int
     */
    public int timeUntilDraw() {
        return LocalTime.now().compareTo(targetTime);
    }

    public void setNextTarget() {
        targetTime = targetTime.plusHours(24);
    }

    /**
     * Save data to file, and reload it into the bot.
     */
    private void reload() {
        save();
        lottery = load();
    }

    /**
     * Serialize and save data to file.
     */
    private void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\kevin\\IdeaProjects\\EchoBot\\src\\main\\java\\me\\greenadine\\echobot\\data\\lottery.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(lottery);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Failed to save data to file 'lottery.ser'. Reason: IOException.");
            e.printStackTrace();
        }
    }

    /**
     * Deserialize data from file and load into bot.
     * @return HashMap<User, Integer>
     */
    private List<Long> load(){
        List<Long> map;

        try {
            FileInputStream fileIn = new FileInputStream("C:\\Users\\kevin\\IdeaProjects\\EchoBot\\src\\main\\java\\me\\greenadine\\echobot\\data\\lottery.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (List) in.readObject();
        } catch (EOFException e) {
            System.out.println("Nothing found in file 'lottery.ser'.");
            map = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Failed to load data from file 'lottery.ser'. Reason: IOException.");
            e.printStackTrace();
            map = new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load data from file 'lottery.ser'. Reason: Class not found.");
            e.printStackTrace();
            map = new ArrayList<>();
        }

        return map;
    }
}