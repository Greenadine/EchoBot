package me.greenadine.echobot.commands.economy;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.TagHandler;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class EconAddCommand implements MessageCreateListener {

    private Economy econ = EchoBot.econ;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("econ-add")) {
            User user = handler.getUser();

            if (user == null | user.isBot()) {
                return;
            }

            if (handler.length() == 0) {
                handler.reply("Please specify a user and the amount.");
                return;
            }

            else if (handler.length() == 1) {
                handler.reply("Please specify the amount of Gold to add to the user's balance.");
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
                    handler.reply("Amount of Gold to add has to be a number.");
                    return;
                }

                if (!econ.hasData(tagged)) {
                    econ.register(tagged);
                }

                econ.add(tagged, amount);

                handler.reply("Added " + amount + " Gold to " + tagged.getNicknameMentionTag() + "'s balance. New balance: " + econ.getBalance(tagged) + " Gold.");
                return;
            }
        }
    }
}
