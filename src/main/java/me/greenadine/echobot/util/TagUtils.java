package me.greenadine.echobot.util;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.util.DiscordRegexPattern;

import java.util.Optional;

public class TagUtils {

    // User tag methods
    /**
     * Returns whether the given argument is a tag.
     * @param arg The argument
     * @return boolean
     */
    public static boolean isUserMentionTag(String arg) {
        return DiscordRegexPattern.USER_MENTION.matcher(arg).matches();
    }

    /**
     * Returns an Optional of the user mentioned in the tag.
     * @param arg The tag
     * @return Optional<User>
     */
    public static Optional<User> getUser(String arg) {
        if (isUserMentionTag(arg)) {
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
            } catch (NumberFormatException ex) {
                System.out.println("Failed to get id from string '" + stripped + "'.");
                return Optional.empty();
            }

            return EchoBot.bot.getCachedUserById(id);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns whether the user tagged is equal to the user with provided id.
     * @param arg The tag
     * @param id The ID of the user to check for
     * @return boolean
     */
    public static boolean isUserTagged(String arg, long id) {
        if (isUserMentionTag(arg)) {
            Optional<User> user = getUser(arg);

            if (user != null && user.isPresent()) {
                return user.get().getId() == id;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Channel tag methods

    /**
     * Returns whether the given argument is a channel mention tag.
     * @param arg The argument
     * @return boolean
     */
    public static boolean isChannelTag(String arg) {
        return DiscordRegexPattern.CHANNEL_MENTION.matcher(arg).matches();
    }

    /**
     * Returns an Optional of the channel mentioned in the tag.
     * @param arg The tag
     * @return Optional<TextChannel>
     */
    public static Optional<ServerChannel> getChannel(String arg) {
        if (isChannelTag(arg)) {
            String stripped = arg.substring(2, arg.length() - 1);

            long id;

            try {
                id = Long.valueOf(stripped);
            } catch (NumberFormatException ex) {
                System.out.println("Failed to get id from string '" + stripped + "'.");
                return Optional.empty();
            }

            return EchoBot.bot.getServerChannelById(id);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns an Optional of the channel mentioned in the tag.
     * @param arg The tag
     * @return Optional<TextChannel>
     */
    public static Optional<ServerTextChannel> getTextChannel(String arg) {
        if (isChannelTag(arg)) {
            String stripped = arg.substring(2, arg.length() - 1);

            long id;

            try {
                id = Long.valueOf(stripped);
            } catch (NumberFormatException ex) {
                System.out.println("Failed to get id from string '" + stripped + "'.");
                return Optional.empty();
            }

            return EchoBot.bot.getServerTextChannelById(id);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns whether the given argument is a role mention tag.
     * @param arg The argument
     * @return boolean
     */
    public static boolean isRoleTag(String arg) {
        return DiscordRegexPattern.ROLE_MENTION.matcher(arg).matches();
    }

    /**
     * Returns an Optional of the role mentioned in the tag.
     * @param arg The argument
     * @return Optional<Role>
     */
    public static Optional<Role> getRole(String arg) {
        if (isRoleTag(arg)) {
            String stripped = arg.substring(3, arg.length() - 1);

            long id;

            try {
                id = Long.valueOf(stripped);
            } catch (NumberFormatException ex) {
                System.out.println("Failed to get id from string '" + stripped + "'.");
                return Optional.empty();
            }

            return EchoBot.bot.getRoleById(id);
        } else {
            return Optional.empty();
        }
    }
}
