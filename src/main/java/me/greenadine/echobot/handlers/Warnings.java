package me.greenadine.echobot.handlers;

import me.greenadine.echobot.objects.Warning;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Warnings {

    private HashMap<Long, List<Warning>> warnings;

    public Warnings() {
        warnings = load();
    }

    public boolean hasData(User user) {
        return warnings.containsKey(user.getId());
    }

    public void register(User user) {
        if (!hasData(user)) {
            warnings.put(user.getId(), new ArrayList<>());
            reload();
        }
    }

    /**
     * Give a user a warning. Returns Warning if success, null if otherwise.
     * @param staff The staff member issuing the warning
     * @param user The user to receive the warning
     * @param description The description of the warning
     * @return boolean
     */
    public Warning addWarning(User staff, User user, String description) {
        if (hasData(user)) {
            try {
                Warning warning = new Warning(user.getId(), staff.getId(), description.trim());

                warnings.get(user.getId()).add(warning);
                reload();
                return warning;
            } catch (Exception e) {
                System.out.println("Failed to give user '" + user.getName() + "' (" + user.getId() + ") warning.");
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Remove a warning from a user by index. Returns true if success, false if otherwise.
     * @param user The user to remove the warning from
     * @param index The index number of the warning.
     * @return boolean
     */
    public Warning removeWarning(User user, int index) {
        if (hasData(user)) {
            try {
                Warning warning = warnings.get(user.getId()).get(index);
                warnings.get(user.getId()).remove(index);
                reload();
                return warning;
            } catch (Exception e) {
                System.out.println("Failed to remove warning from user '" + user.getName() + "' (" + user.getId() + ").");
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Get a list of warnings a user has received.
     * @param user The user
     * @return List<Warning>
     */
    public List<Warning> getWarnings(User user) {
        if (hasData(user)) {
            return warnings.get(user.getId());
        } else {
            return null;
        }
    }

    /**
     * Get the amount of warnings a user currently has.
     * @param user The user
     * @return int
     */
    public int getWarningSize(User user) {
        if (hasData(user)) {
            return warnings.get(user.getId()).size();
        } else {
            return 0;
        }
    }


    /**
     * Get the warning of a user at the specified index.
     * @param user The user
     * @param index The index of the warning to get
     * @return Warning
     */
    public Warning getWarning(User user, int index) {
        if (hasData(user)) {
            return warnings.get(user.getId()).get(index);
        } else {
            return null;
        }
    }

    /**
     * Return all warnings a user has listed in an embed.
     * @param user The user
     * @return EmbedBuilder
     */
    public EmbedBuilder getWarningsAsEmbed(User user) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Warnings issued to " + user.getName())
                .setColor(Color.CYAN)
                .setFooter("User ID: " + user.getId())
                .setThumbnail(user.getAvatar());

        for (int i = 0; i < getWarnings(user).size(); i++) {
            Warning warning = getWarning(user, i);

            embed.addField("#" + (i + 1) + " - Issued by " + warning.getStaff().getName() + ".", warning.getDescription());
        }

        return embed;
    }

    /**
     * Remove a user from data memory.
     * @param user The user
     * @return boolean
     */
    public boolean clear(User user) {
        if (hasData(user)) {
            warnings.remove(user.getId());
            reload();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Save data to file, and reload it into the bot.
     */
    private void reload() {
        save();
        warnings = load();
    }

    /**
     * Serialize and save data to file.
     */
    private void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\kevin\\IdeaProjects\\EchoBot\\src\\main\\java\\me\\greenadine\\echobot\\data\\warnings.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(warnings);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Failed to save data to file 'warnings.ser'. Reason: IOException.");
            e.printStackTrace();
        }
    }

    /**
     * Deserialize data from file and load into bot.
     * @return HashMap<User, Integer>
     */
    private HashMap<Long, List<Warning>> load(){
        HashMap<Long, List<Warning>> map;

        try {
            FileInputStream fileIn = new FileInputStream("C:\\Users\\kevin\\IdeaProjects\\EchoBot\\src\\main\\java\\me\\greenadine\\echobot\\data\\warnings.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap) in.readObject();
        } catch (EOFException e) {
            System.out.println("Nothing found in file 'warnings.ser'.");
            map = new HashMap<>();
        } catch (IOException e) {
            System.out.println("Failed to load data from file 'warnings.ser'. Reason: IOException.");
            e.printStackTrace();
            map = new HashMap<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load data from file 'warnings.ser'. Reason: Class not found.");
            e.printStackTrace();
            map = new HashMap<>();
        }

        return map;
    }
}
