package me.greenadine.echobot.handlers;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unchecked")
public class Settings {

    private HashMap<String, Object> settings;

    public Settings() {
        settings = load();

        // Set default welcome channel
        settings.putIfAbsent("welcomeChannel", 676391123761233930L);

        // Create default roles to give at joining
        settings.putIfAbsent("defaultRoles", new ArrayList<>());

        // Create default banned words list
        settings.putIfAbsent("bannedWords", new ArrayList<>());

        // Create default excluded channels list (channels excluded for specific things, such as logging)
        settings.putIfAbsent("excludedChannels", new ArrayList<>());

        // Create default excluded channels list (channels excluded for specific things, such as logging)
        settings.putIfAbsent("ignoredChannels", new ArrayList<>());
    }

    /**
     * Save settings to file, and reload it into the bot.
     */
    private void reload() {
        save();
        settings = load();
    }

    /**
     * Get the welcoming channel.
     * @return Long
     */
    public Long getWelcomeChannel() {
        return (Long) settings.get("welcomeChannel");
    }

    /**
     * Set the welcoming channel.
     * @param id The ID of the channel
     */
    public void setWelcomeChannel(Long id) {
        settings.put("welcomeChannel", id);
    }

    /**
     * Returns whether the given role is already set as the welcoming channel.
     * @param id The ID of the channel
     * @return boolean
     */
    public boolean isWelcomeChannel(Long id) {
        return settings.get("welcomeChannel") == id;
    }

    /**
     * Get the list of roles that should be given to new members.
     * @return List<Long>
     */
    public List<Long> getDefaultRoles() {
        return (List<Long>) settings.get("defaultRoles");
    }

    /**
     * Add a role to the list of roles that should be given to new members.
     * @param id The ID of the role
     */
    public boolean addDefaultRole(Long id) {
        if (((List<Long>)settings.get("defaultRoles")).contains(id)) {
            return false;
        }

        ((List<Long>)settings.get("defaultRoles")).add(id);
        reload();
        return true;
    }


    /**
     * Remove a role from the list of roles that should be given to new members.
     * @param id The ID of the role
     */
    public boolean removeDefaultRole(Long id) {
        if (!((List<Long>)settings.get("defaultRoles")).contains(id)) {
            return false;
        }

        ((List<Long>)settings.get("defaultRoles")).remove(id);
        reload();
        return true;
    }

    /**
     * Returns whether the given role is already in the default roles list.
     * @param id The ID of the role
     * @return boolean
     */
    public boolean isDefaultRole(Long id) {
        return ((List<Long>) settings.get("defaultRoles")).contains(id);
    }

    /**
     * Get the list of words that are banned from the server.
     * @return List<String>
     */
    public List<String> getBannedWords() {
        return (List<String>) settings.get("bannedWords");
    }

    /**
     * Add a word to the list of words banned from the server.
     * @param word The word
     */
    public void addBannedWord(String word) {
        ((List<String>)settings.get("bannedWords")).add(word);
        reload();
    }

    /**
     * Remove a word from the list of words banned from the server.
     * @param word The word
     */
    public void removeBannedWord(String word) {
        ((List<String>)settings.get("bannedWords")).remove(word);
        reload();
    }

    /**
     * Returns whether the given word is banned.
     * @param word The word
     * @return boolean
     */
    public boolean isBannedWord(String word) {
        return ((List<String>)settings.get("bannedWords")).contains(word);
    }

    /**
     * Get the list of channels that are excluded from logging.
     * @return List<Long>
     */
    public List<Long> getExcludedChannels() {
        return (List<Long>) settings.get("excludedChannels");
    }

    /**
     * Add a channel to the list of channels excluded from logging.
     * @param id The ID of the channel
     */
    public void addExcludedChannel(Long id) {
        ((List<Long>)settings.get("excludedChannels")).add(id);
        reload();
    }

    /**
     * Remove a channel from the list of channels excluded from logging.
     * @param id The ID of the channel
     */
    public void removeExcludedChannel(Long id) {
        ((List<Long>)settings.get("excludedChannels")).remove(id);
        reload();
    }

    /**
     * Returns whether the given channel is excluded from logging.
     * @param id The ID of the channel
     * @return boolean
     */
    public boolean isExcludedChannel(Long id) {
        return ((List<Long>)settings.get("excludedChannels")).contains(id);
    }

    /**
     * Get the economy gold limit.
     * @return int
     */
    public int getEconomyGoldLimit() {
        return (int) settings.getOrDefault("economyGoldLimit", 999999999);
    }

    /**
     * Set the economy gold limit.
     * @param limit The new gold limit
     */
    public void setEconomyGoldLimit(int limit) {
        settings.put("economyGoldLimit", limit);
        reload();
    }

    /**
     * Get the warning weight threshold.
     * @return double
     */
    public double getWarningWeightThreshold() {
        return (double) settings.getOrDefault("warningWeightThreshold", 5.0);
    }

    /**
     * Set the warning weight threshold
     * @param threshold The new weight threshold
     */
    public void setWarningWeightThreshold(double threshold) {
        settings.put("warningWeightThreshold", threshold);
        reload();
    }

    /**
     * Returns whether the Valentine's command is enabled.
     * @return boolean
     */
    public boolean isValentineEnabled() {
        return (boolean) settings.getOrDefault("valentineEnabled", true);
    }

    /**
     * Enable or disable the Valentine's command.
     * @param v The new value (true/false)
     */
    public void setValentineEnabled(boolean v) {
        settings.put("valentineEnabled", v);
        reload();
    }

    /**
     * Return the current Valentine's year to use for the command.
     * @return String
     */
    public String getCurrentValentine() {
        return (String) settings.getOrDefault("valentineCurrent", "2020");
    }

    /**
     * Set the current Valentine's year's data to use for the command.
     * @param id The id (e.g. 2020)
     */
    public void setCurrentValentine(String id) {
        settings.put("valentineCurrent", id);
        reload();
    }

    /**
     * Serialize and save settings to file.
     */
    private void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("settings.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(settings);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Failed to save data to file 'settings.ser'. Reason: IOException.");
            e.printStackTrace();
        }
    }

    /**
     * Deserialize settings from file and load into bot.
     * @return HashMap<User, Integer>
     */
    private HashMap<String, Object> load(){
        HashMap<String, Object> map;

        try {
            FileInputStream fileIn = new FileInputStream("settings.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap) in.readObject();
        } catch (EOFException e) {
            System.out.println("Nothing found in file 'settings.ser'.");
            map = new HashMap<>();
        } catch (IOException e) {
            System.out.println("Failed to load data from file 'settings.ser'. Reason: IOException.");
            e.printStackTrace();
            map = new HashMap<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load data from file 'settings.ser'. Reason: Class not found.");
            e.printStackTrace();
            map = new HashMap<>();
        }

        return map;
    }
}
