package me.greenadine.echobot.handlers;

import me.greenadine.echobot.objects.TriviaQuestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Trivia {

    private String file;

    private List<TriviaQuestion> questions;

    public Trivia(String filepath) {
        file = filepath;

        setup();
    }

    /**
     * Get a random trivia question.
     * @return TriviaQuestion
     */
    public TriviaQuestion getRandom() {
        return questions.get(new Random().nextInt(questions.size() - 1));
    }

    private void setup() {
        ArrayList<TriviaQuestion> list = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(file));

            while (scanner.hasNext()) {
                String[] s = scanner.nextLine().split(",");
                List<String> answers = new ArrayList<>();

                for (int i = 1; i < s.length; i++) {
                    answers.add(s[i]);
                }

                TriviaQuestion question = new TriviaQuestion(s[0], answers);
                list.add(question);
            }
        } catch (FileNotFoundException e){
            System.out.println("Failed to load file with path '" + file + "': file not found.");
            return;
        }

        questions = list;

        System.out.println("Loaded " + list.size()+ " questions from file '" + new File(file).getName() + "'.");
    }
}
