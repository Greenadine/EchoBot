package me.greenadine.echobot.commands.economy;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EconClearCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "econ-clear";
    }

    public String getDescription() {
        return "Clear a user's balance.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!econ-clear <user>";
    }

    public String getArguments() {
        return "``user`` - The user. Either tag them, or give their ID.";
    }

    public String getAliases() { return null; }

    private Economy econ = EchoBot.econ;

    private final List<User> confirm = new ArrayList<>();

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) {return;}

        if (!handler.getUser().isPresent()) { // User optional is empty
            handler.reply("Failed to execute command. Reason: User empty.");
            return;
        }

        // Get the user that invoked the command.
        User user = handler.getUser().get();

        // If user does not have permission to use command (is not a moderator or higher)
        if (!PermissionsHandler.isModerator(user)) {
            handler.reply(user.getNicknameMentionTag() + " Nice try buddy, you do not have permission to use this command.");
            return;
        }

        if (handler.length() != 1) {
            handler.reply("Invalid command usage. Type ``e!help econ-clear`` for command information.");
            return;
        }

        // Add user to ArrayList for confirmation if not added yet, otherwise perform action.
        if (handler.getArg(0).equalsIgnoreCase("all")) {
            if (!confirm.contains(user)) {
                confirm.remove(user);
                econ.clearAll();
                handler.reply("All user's Gold has been cleared.");
                return;
            } else{
                confirm.add(user);
                handler.reply("***WARNING:*** This will clear all Gold from all users. Are you sure you want to continue? If so, enter the same command again. To cancel the action, enter the command ``e!clear cancel``.");
                return;
            }
        }

        // Cancel clear-all action.
        if (handler.getArg(0).equalsIgnoreCase("cancel")) {
            if (confirm.contains(user)) {
                confirm.remove(user);
                handler.reply("Action cancelled.");
                return;
            } else {
                handler.reply("There is no action to be cancelled.");
                return;
            }
        }

        Optional<User> optTarget;

        // Retrieve user instance from tag mention, or from given ID.
        if (!TagUtils.isUserMentionTag(handler.getArg(0))) {
            long id;

            try {
                id = Long.valueOf(handler.getArg(0));
            } catch (NumberFormatException  ex) {
                handler.reply("Please either give a user's ID or tag a user like this: " + EchoBot.bot.getYourself().getNicknameMentionTag() + ".");
                return;
            }

            if (EchoBot.bot.getCachedUserById(id).isPresent()) {
                optTarget = EchoBot.bot.getCachedUserById(id);
            } else {
                handler.reply("User with ID '" + handler.getArg(0)+ "' could not be found.");
                return;
            }
        } else {
            optTarget = TagUtils.getUser(handler.getArg(0));
        }

        // If target optional is empty, do not execute command.
        if (!optTarget.isPresent()) {
            handler.reply("Failed to check balance. Reason: Target empty.");
            return;
        }

        // Get the targeted user
        User target = optTarget.get();

        // If the targeted user is a bot, do not continue.
        if (target.isBot()) {
            handler.reply("That user is a bot.");
            return;
        }

        // If the user doesn't have any data, do not continue.
        if (!econ.hasData(target)) {
            handler.reply("This user does not have any Gold yet.");
            return;
        }

        // Clear the user's balance.
        econ.clear(target);

        handler.reply("Cleared balance of " + target.getNicknameMentionTag() + ".");
    }
}
