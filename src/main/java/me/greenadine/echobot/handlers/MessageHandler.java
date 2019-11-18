package me.greenadine.echobot.handlers;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class MessageHandler {

    private TextChannel channel;
    private Optional<User> user;

    private List<String> arguments;

    public MessageHandler(MessageCreateEvent e) {
        String[] args = e.getMessage().getContent().split("\\s+"); // Split message per whitespace.

        channel = e.getChannel();
        user = e.getMessage().getUserAuthor();

        arguments = new ArrayList<>(); // Create new array list holding only the message arguments.

        for (int i = 0; i < args.length; i++) {
            String s = args[i];

            arguments.add(s);
        }
    }

    /**
     * Reply with a message in the channel of the message.
     * @param content
     */
    public void reply(String content) {
        channel.sendMessage(content);
    }

    /**
     * Reply with an embed in the channel of the message that invoked the command.
     * @param embed
     */
    public void reply(EmbedBuilder embed) {
        channel.sendMessage(embed);
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
