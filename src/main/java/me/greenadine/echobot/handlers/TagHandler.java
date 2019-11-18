package me.greenadine.echobot.handlers;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.user.User;
import org.javacord.api.util.DiscordRegexPattern;

import java.util.NoSuchElementException;
import java.util.Optional;

public class TagHandler {

    /**
     * Returns the user that is tagged in a certain argument. Returns null if argument is no tag.
     * @param arg
     * @return boolean
     */
    public static boolean isTag(String arg) {
        return DiscordRegexPattern.USER_MENTION.matcher(arg).matches();
    }

    /**
     * Returns the user mentioned in the tag.
     * @param arg
     * @return User
     */
    public static User getUser(String arg) {
        if (isTag(arg)) {
            String stripped;

            if (arg.startsWith("<@!")) {
                stripped = arg.substring(3, arg.length() - 1);
            } else if (arg.startsWith("<@")) {
                stripped = arg.substring(2, arg.length() - 1);
            } else {
                throw new NullPointerException("Failed to identify given string '" + arg + "' as user tag.");
            }

            long id;

            try {
                id = Long.valueOf(stripped);
            } catch (Exception e) {
                System.out.println("Failed to get id from string '" + stripped + "'.");
                return null;
            }

            Optional<User> optuser = EchoBot.bot.getCachedUserById(id);

            if (optuser.isPresent()) {
                return optuser.get();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Returns whether the user tagged is equal to the user with provided id.
     * @param arg
     * @param id
     * @return boolean
     */
    public static boolean isUserTagged(String arg, long id) {
        if (isTag(arg)) {
            User user = getUser(arg);

            if (user != null) {
                return user.getId() == id;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}