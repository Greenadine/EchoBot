package me.greenadine.echobot.handlers;

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

    public String getRandom() {
        return phrases.get(new Random().nextInt(phrases.size() - 1));
    }

    private void setup() {
        ArrayList<String> list = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(file));

            while (scanner.hasNext()) {
                list.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e){
            System.out.println("Failed to load file with path '" + file + "': file not found.");
            return;
        }

        phrases = list;

        System.out.println("Loaded " + list.size()+ " phrases from file '" + new File(file).getName() + "'.");
    }
}
