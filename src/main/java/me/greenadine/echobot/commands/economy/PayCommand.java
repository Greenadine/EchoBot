package me.greenadine.echobot.commands.economy;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Optional;

public class PayCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "pay";
    }

    public String getDescription() {
        return "Give another user Gold.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!pay <user> <amount>";
    }

    public String getArguments() {
        return "``user`` - The user. Either tag them, or give their ID.\namount - The amount of Gold to give to the user.";
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

        User user = handler.getUser().get();

        if (handler.length() != 2) {
            handler.reply("Invalid command usage. Type ``e!help pay`` for command information.");
            return;
        }

        Optional<User> optTarget;

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

        if (!optTarget.isPresent()) { // Target optional is empty
            handler.reply("Failed to check balance. Reason: Target empty.");
            return;
        }

        User target = optTarget.get();

        if (target.isBot()) {
            handler.reply("That user is a bot.");
            return;
        }

        int amount;

        try {
            amount = Integer.valueOf(handler.getArg(1));
        } catch (NumberFormatException ex) {
            handler.reply("Amount of Gold to send has to be a number.");
            return;
        }

        if (!econ.hasData(user)) {
            econ.register(user);
        }

        if (!econ.hasBalance(user, amount)) {
            handler.reply("You do not have enough Gold. Current balance: " + econ.getBalance(user) + " Gold.");
            return;
        }

        if (!econ.hasData(target)) {
            econ.register(target);
        }

        econ.withdraw(user, amount);
        econ.add(target, amount);

        handler.reply(amount + " Gold transferred to " + target.getNicknameMentionTag() + ". New balance: " + econ.getBalance(user) + " Gold.");
    }
}