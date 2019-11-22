package me.greenadine.echobot.commands.economy;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.TagHandler;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class EconWithdrawCommand implements MessageCreateListener {

    private Economy econ = EchoBot.econ;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("econ-withdraw")) {
            User user = handler.getUser();

            if (user == null | user.isBot()) {
                return;
            }

            if (handler.length() == 0) {
                handler.reply("Please specify a user and the amount.");
                return;
            }

            else if (handler.length() == 1) {
                handler.reply("Please specify the amount of Gold to withdraw from the user's balance.");
                return;
            }

            else if (handler.length() == 2) {
                if (!TagHandler.isTag(handler.getArg(0))) {
                    handler.reply("Please tag a user like this: " + EchoBot.bot.getYourself().getNicknameMentionTag() + ".");
                    return;
                }

                User tagged = TagHandler.getUser(handler.getArg(0));

                if (tagged.isBot()) {
                    handler.reply("That user is a bot.");
                    return;
                }

                int amount;

                try {
                    amount = Integer.valueOf(handler.getArg(1));
                } catch (NumberFormatException ex) {
                    handler.reply("Amount of Gold to withdraw has to be a number.");
                    return;
                }

                if (amount <= 0) {
                    handler.reply("Please put in a number higher than 0.");
                    return;
                }

                if (!econ.hasData(tagged)) {
                    econ.register(tagged);
                }

                if (!econ.hasBalance(user, amount)) {
                    handler.reply("User does not have that much Gold. Current balance: " + econ.getBalance(user) + " Gold.");
                    return;
                }

                econ.withdraw(tagged, amount);

                handler.reply("Withdrew " + amount + " Gold from " + tagged.getNicknameMentionTag() + "'s balance. New balance: " + econ.getBalance(user) + " Gold.");
                return;
            }

            else {
                handler.reply("Invalid command usage. Type ``e!help econ-withdraw`` for command information.");
                return;
            }
        }
    }
}
