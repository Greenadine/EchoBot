package me.greenadine.echobot.handlers;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandHandler {

    private TextChannel channel;
    private Optional<User> user;

    private String command;
    private List<String> arguments;

    public CommandHandler(MessageCreateEvent e) {
        String[] args = e.getMessage().getContent().split("\\s+"); // Split message per whitespace.

        channel = e.getChannel();
        command = args[0];

        user = e.getMessage().getUserAuthor();

        arguments = new ArrayList<>(); // Create new array list holding only the command arguments.

        for (int i = 0; i < args.length; i++) {
            String s = args[i];

            if (i != 0) {
                arguments.add(s);
            }
        }
    }

    /**
     * Reply with a message in the channel of the message that invoked the command.
     * @param content The message
     */
    public void reply(String content) {
        channel.sendMessage(content);
    }

    /**
     * Reply with an embed in the channel of the message that invoked the command.
     * @param embed The embed
     */
    public void reply(EmbedBuilder embed) {
        channel.sendMessage(embed);
    }

    /**
     * Reply with a message and embed in the channel of the message that invoked the command.
     * @param content The message
     * @param embed The embed
     */
    public void reply(String content, EmbedBuilder embed) {
        channel.sendMessage(content, embed);
    }

    /**
     * Reply with a message and an attached file in the channel of the message that invoked the command.
     * @param content The message
     * @param file The file(s)
     */
    public void reply(String content, File... file) {
        channel.sendMessage(content, file);
    }

    /**
     * Returns the user of message.
     * @return User
     */
    public User getUser() {
        return user.get();
    }

    /**
     * Returns the command that was invoked.
     * @return String
     */
    public String getFullCommand() {
        return command;
    }

    /**
     * Returns the command that was invoked, without the command prefix.
     * @return String
     */
    public String getCommand() {
        return command.substring(2);
    }

    /**
     * Returns whether the user has sent a message that is equal to the given command.
     * @param cmd The command to check for
     * @return boolean
     */
    public boolean isCommand(String cmd){
        if (command.startsWith(EchoBot.prefix)) {
            return command.substring(2).equalsIgnoreCase(cmd);
        } else {
            return false;
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
     * @param i The index of the argument
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