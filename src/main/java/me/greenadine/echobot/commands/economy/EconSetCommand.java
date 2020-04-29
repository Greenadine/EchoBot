package me.greenadine.echobot.commands.economy;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Optional;

public class EconSetCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "econ-set";
    }

    public String getDescription() {
        return "Set a user's balance.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!econ-set <user>";
    }

    public String getArguments() {
        return "user - The user. Either tag them, or give their ID.";
    }

    public String getAliases() { return null; }

    private Economy econ = EchoBot.econ;

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

        if (handler.length() != 2) {
            handler.reply("Invalid command usage. Type ``e!help econ-set`` for command information.");
            return;
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
        if (!optTarget.isPresent()) { // Target optional is empty
            handler.reply("Failed to check balance. Reason: Target empty.");
            return;
        }

        User target = optTarget.get();

        // If the targeted user is a bot, do not continue.
        if (target.isBot()) {
            handler.reply("That user is a bot.");
            return;
        }

        int amount;

        // Retrieve gold amount from given argument.
        try {
            amount = Integer.valueOf(handler.getArg(1));
        } catch (NumberFormatException ex) {
            handler.reply("Amount of Gold to set has to be a number.");
            return;
        }

        // If the given gold amount is above the set gold limit, prevent action.

        if (amount > EchoBot.settings.getEconomyGoldLimit()) {
            handler.reply("Number is higher than the Gold limit (" + EchoBot.settings.getEconomyGoldLimit() + ").");
            return;
        }

        // If target user has no data yet, register them.
        if (!econ.hasData(target)) {
            econ.register(target);
        }

        // Set the targeted user's Gold balance to the given amount.
        boolean success = econ.set(target, amount);

        if (success) {
            handler.reply("Set " + target.getNicknameMentionTag() + "'s balance to " + econ.getBalance(target) + " Gold.");
        } else {
            handler.reply("An internal error has occurred while trying to set user's balance.");
        }
    }
}
