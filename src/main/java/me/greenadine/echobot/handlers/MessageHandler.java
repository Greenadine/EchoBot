package me.greenadine.echobot.handlers;

import me.greenadine.echobot.util.StringUtils;

import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class MessageHandler {

    private Message message;
    private TextChannel channel;
    private Optional<User> user;

    private List<String> arguments;

    public MessageHandler(MessageCreateEvent e) {
        String[] args = e.getMessage().getContent().split("\\s+"); // Split message per whitespace.

        message = e.getMessage();
        channel = e.getChannel();
        user = e.getMessage().getUserAuthor();

        arguments = new ArrayList<>(); // Create new array list holding only the message arguments.

        for (int i = 0; i < args.length; i++) {
            String s = args[i];

            arguments.add(s);
        }
    }

    /**
     * Get the content of the message.
     * @return String
     */
    public String getMessage() {
        return message.getContent();
    }

    /**
     * Returns whether the message is equal to the given string.
     * @param s The string
     * @return boolean
     */
    public boolean equals(String s) {
        return getMessage().equals(s);
    }

    /**
     * Returns whether the message is equal to the given string while ignoring case.
     * @param s The string
     * @return boolean
     */
    public boolean equalsIgnoreCase(String s) {
        return getMessage().equalsIgnoreCase(s);
    }

    /**
     * Returns whether the message contains the given string.
     * @param s The string
     * @return boolean
     */
    public boolean contains(String s) {
        return getMessage().contains(s);
    }

    /**
     * Returns whether the message contains the given string while ignoring case.
     * @param s The string
     * @return boolean
     */
    public boolean containsIgnoreCase(String s) {
        return StringUtils.containsIgnoreCase(getMessage(), s);
    }

    /**
     * Returns whether the message contains the given string, ignoring the order and case.
     * @param s The string
     * @return boolean
     */
    public boolean containsSegment(String s) {
        String[] str = s.split(" ");

        for (int i = 0; i < str.length; i++) {
            String s1 = str[i];

            if (!containsIgnoreCase(s1)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether the message starts with the given string.
     * @param s The string
     * @return boolean
     */
    public boolean startsWith(String s) {
        return message.getContent().startsWith(s);
    }

    /**
     * Get the instance of the message.
     * @return Message
     */
    public Message getInstance() {
        return message;
    }

    /**
     * Get the channel where the message was sent in.
     * @return Channel
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * Reply with a message in the channel of the message.
     * @param content The message
     */
    public CompletableFuture<Message> reply(String content) {
        return channel.sendMessage(content);
    }

    /**
     * Reply with an embed in the channel of the message that invoked the command.
     * @param embed The embed
     */
    public CompletableFuture<Message> reply(EmbedBuilder embed) {
        return channel.sendMessage(embed);
    }

    /**
     * Get the Optional of the User.
     * @return Optional<User>
     */
    public Optional<User> getOptionalUser() {
        return user;
    }

    /**
     * Returns the user of message.
     * @return User
     */
    public User getUser() {
        try {
            return user.get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Returns the command's arguments.
     * @return List<String>
     */
    public List<String> getArgs() {
        return arguments;
    }

    /**
     * Returns a certain command argument.
     * @param i
     * @return String
     */
    public String getArg(int i) {
        return arguments.get(i);
    }



    /**
     * Returns the amount of arguments.
     * @return int
     */
    public int length() {
        return arguments.size();
    }
}
