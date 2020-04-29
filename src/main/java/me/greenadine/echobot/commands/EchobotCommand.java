package me.greenadine.echobot.commands;

import org.javacord.api.listener.message.MessageCreateListener;

public interface EchobotCommand extends MessageCreateListener {

    /**
     * Get the command's name.
     * @return String
     */
    String getName();

    /**
     * Get the command's description.
     * @return String
     */
    String getDescription();

    /**
     * Get the commmand's detailed description.
     * @return String
     */
    String getDetails();

    /**
     * Get the way the command should be used.
     * @return String
     */
    String getUsage();

    /**
     * Get which arguments are used in the command.
     * @return String
     */
    String getArguments();

    /**
     * Get the command's aliases, if it has any
     * @return String
     */
    String getAliases();
}
