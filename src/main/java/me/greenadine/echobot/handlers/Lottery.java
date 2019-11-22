package me.greenadine.echobot.handlers;

import org.javacord.api.entity.user.User;

import java.util.List;
import java.util.Random;

public class Lottery {

    private List<Long> lottery;

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
        if (!lottery.isEmpty()) {
            return lottery.get(new Random().nextInt(lottery.size() - 1));
        } else {
            return 0L;
        }
    }


    /**
     * Clear entries.
     */
    public void clear() {
        lottery.clear();
    }
}
