package me.greenadine.echobot.handlers;

import org.javacord.api.entity.user.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Phrases {

    private String file;

    private List<String> phrases;

    public Phrases(String filepath) {
        file = filepath;

        setup();
    }

    /**
     * Get the amount of loaded-in phrases.
     * @return int
     */
    public int getSize() {
        return phrases.size();
    }

    /**
     * Returns whether the list of phrases is empty or not.
     * @return boolean
     */
    public boolean isEmpty() {
        return phrases.isEmpty();
    }

    /**
     * Get a random phrase.
     * @param user The user that executed the command.
     * @return String
     */
    public String getRandom(User user) {
        return phrases.get(new Random().nextInt(phrases.size())).replaceAll("%user%", user.getNicknameMentionTag());
    }

    private void setup() {
        ArrayList<String> list = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(file));

            while (scanner.hasNext()) {
                list.add(scanner.nextLine());
            }

            scanner.close();
        } catch (FileNotFoundException e){
            System.out.println("Failed to load file with path '" + file + "': file not found.");
            return;
        }

        phrases = list;

        System.out.println("Loaded " + list.size()+ " phrases from file '" + new File(file).getName() + "'.");
    }
}
