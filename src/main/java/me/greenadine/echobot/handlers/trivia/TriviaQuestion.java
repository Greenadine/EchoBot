package me.greenadine.echobot.handlers.trivia;

import java.io.Serializable;
import java.util.List;

public class TriviaQuestion implements Serializable {

    private String question;
    private List<String> answers;

    public TriviaQuestion(String q, List<String> a) {
        question = q;
        answers = a;
    }

    /**
     * Get the trivia question.
     * @return String
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Get the first possible correct answer to the question.
     * @return String
     */
    public String getCorrectAnswer() {
        return answers.get(0);
    }

    /**
     * Get all the possible correct answers to the question.
     * @return List<String>
     */
    public List<String> getAnswers() {
        return answers;
    }

    /**
     * Returns whether the given string can be counted as a correct answer.
     * @param a The given answer
     * @return boolean
     */
    public boolean isCorrectAnswer(String a) {
        for (String answer : answers) {
            if (answer.toLowerCase().equalsIgnoreCase(a.toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}
