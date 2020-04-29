package me.greenadine.echobot.handlers;

import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class BanHandler {

    private HashMap<Long, ArrayList<Long>> banned = load();

    /**
     * Returns whether a user is banned.
     * @param user The user
     * @param server The server
     * @return boolean
     */
    public boolean isBanned(User user, Server server) {
        if (banned.get(server.getId()) == null) {
            banned.put(server.getId(), new ArrayList<>());
            reload();
        }

        return banned.get(server.getId()).contains(user.getId());
    }

    /**
     * Ban a user from the server.
     * @param user The user
     * @param server The server
     * @return boolean
     */
    public boolean banUser(User user, Server server) {
        banned.putIfAbsent(server.getId(), new ArrayList<>());

        if (!isBanned(user, server)) {
            banned.get(server.getId()).add(user.getId());
            reload();

            server.banUser(user);

            CompletableFuture<Void> ban = server.banUser(user);

            return !ban.isCancelled();
        }

        return false;
    }

    /**
     * Unban a user that is currently banned from the server.
     * @param user The user
     * @param server The server
     * @return boolean
     */
    public boolean unbanUser(User user, Server server) {
        banned.putIfAbsent(server.getId(), new ArrayList<>());

        if (isBanned(user ,server)) {
            banned.get(server.getId()).remove(user.getId());
            reload();

            CompletableFuture<Void> unban = server.unbanUser(user);

            return !unban.isCancelled();
        }

        return false;
    }

    private void reload() {
        save();
        banned = load();
    }

    /**
     * Serialize and save economy data to file.
     */
    private void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/banned.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(banned);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Failed to save data to file 'banned.ser'. Reason: IOException.");
            e.printStackTrace();
        }
    }

    /**
     * Deserialize data from file and load into bot.
     * @return HashMap<User, Integer>
     */
    private HashMap<Long, ArrayList<Long>> load(){
        HashMap<Long, ArrayList<Long>> map;

        try {
            FileInputStream fileIn = new FileInputStream("data/banned.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap) in.readObject();
        } catch (EOFException e) {
            System.out.println("Nothing found in file 'banned.ser'.");
            map = new HashMap<>();
        } catch (IOException e) {
            System.out.println("Failed to load data from file 'banned.ser'. Reason: IOException.");
            e.printStackTrace();
            map = new HashMap<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load data from file 'banned.ser'. Reason: Class not found.");
            e.printStackTrace();
            map = new HashMap<>();
        }

        return map;
    }
}
