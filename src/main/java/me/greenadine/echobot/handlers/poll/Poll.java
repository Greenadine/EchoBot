package me.greenadine.echobot.handlers.poll;

import org.javacord.api.entity.emoji.Emoji;

import java.util.HashMap;

public class Poll {

    private String question;
    private HashMap<String, Emoji> options;
    private int maxVotesPerUser;

    public Poll(String question, int maxVotesPerUser, HashMap<String, Emoji> options) {
        this.question = question;
        this.options = options;

        this.maxVotesPerUser = maxVotesPerUser;
    }

    /**
     * Get the question.
     * @return String
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Get all the react options.
     * @return HashMap<String, Emoji>
     */
    public HashMap<String, Emoji> getOptions() {
        return options;
    }

    /**
     * Get the maximum amount of times each user can cast a vote on the poll.
     * @return int
     */
    public int getMaxVotesPerUser() {
        return maxVotesPerUser;
    }

}
