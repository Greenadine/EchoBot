package me.greenadine.echobot.commands;

import com.sun.istack.internal.Nullable;
import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.io.File;
import java.io.InputStream;
import java.util.*;

public class CommandHandler {

    private EchobotCommand command;

    private List<String> aliases;

    private TextChannel channel;

    private Message message;
    private String cmd;
    private List<String> arguments;

    public CommandHandler(MessageCreateEvent event) {
        this.command = null;

        String[] args = event.getMessage().getContent().split("\\s+"); // Split message per whitespace.

        message = event.getMessage();
        channel = event.getChannel();
        cmd = args[0];

        arguments = new ArrayList<>(); // Create new array list holding only the command arguments.

        for (int i = 0; i < args.length; i++) {
            String s = args[i];

            if (i != 0) {
                arguments.add(s);
            }
        }
    }

    public CommandHandler(EchobotCommand command, MessageCreateEvent event) {
        this.command = command;

        if (command.getAliases() != null) {
            aliases = new ArrayList<>();
            aliases.addAll(Arrays.asList(command.getAliases().split(",")));
        }

        String[] args = event.getMessage().getContent().split("\\s+"); // Split message per whitespace.

        message = event.getMessage();
        channel = event.getChannel();
        cmd = args[0];

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
     * Reply with an attached file in the channel of the message that invoked the command.
     * @param file The file(s)
     */
    public void reply(File... file) {
        channel.sendMessage(file);
    }

    /**
     * Reply with an InputStream as a file with the given file name.
     * @param is The InputStream
     * @param fileName The filename
     */
    public void reply(InputStream is, String fileName) {
       channel.sendMessage(is, fileName);
    }

    /**
     * Returns an Optional of the user of the message.
     * @return Optional<User>
     */
    public Optional<User> getUser() {
        return getMessage().getUserAuthor();
    }

    /**
     * Return the instance of the message.
     * @return Message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Returns the command that was invoked, without the command prefix.
     * @return String
     */
    public String getCommand() {
        return cmd.substring(2);
    }

    /**
     * Returns whether the user has sent a message that is equal to the given command.
     * @return boolean
     */
    public boolean isCommand(){
        if (cmd.startsWith(EchoBot.prefix)) {
            if (command != null) {
                if (hasAliases()) {
                    return cmd.substring(2).equalsIgnoreCase(command.getName()) || isAlias(cmd.substring(2));
                } else {
                    return cmd.substring(2).equalsIgnoreCase(command.getName());
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Returns whether the user has sent a message that is equal to the given command.
     * @return boolean
     */
    public boolean isCommand(String command){
        if (cmd.startsWith(EchoBot.prefix)) {
            return cmd.substring(2).equalsIgnoreCase(command);
        } else {
            return false;
        }
    }

    /**
     * Returns whether the command has aliases or not.
     * @return boolean
     */
    private boolean hasAliases() {
        return aliases != null;
    }

    /**
     * Returns whether the given string is an alias of the command.
     * @param command The string
     * @return boolean
     */
    private boolean isAlias(String command) {
        return aliases.contains(command);
    }

    /**
     * Returns the command's arguments.
     * @return List<String>
     */
    public List<String> getArgs() {
        return arguments;
    }

    /**
     * Returns the arguments in a string form.
     * @return String
     */
    public String getArguments() {
        StringJoiner joiner = new StringJoiner(" ");

        for (String s : getArgs()) {
            joiner.add(s);
        }

        return joiner.toString();
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

    /**
     * Get an array of all the characters in the arguments.
     * @return char[]
     */
    public char[] getCharacters() {
        return getArguments().toCharArray();
    }
}