package me.greenadine.echobot.commands.game;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.Lottery;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class LotteryCommand implements MessageCreateListener {

    private Economy econ = EchoBot.econ;
    private Lottery lottery = EchoBot.lottery;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("lottery")) {
            User user = handler.getUser();

            if (handler.length() != 0) {
                handler.reply("Invalid command usage. Type ``e!help lottery`` for command information.");
                return;
            }

            if (lottery.isEntered(user)) {
                handler.reply("You've already entered for today's draw.");
                return;
            }

            if (econ.hasBalance(user, 50)) {
                handler.reply("You need 50 Gold to enter the lottery draw.");
                return;
            }

            econ.withdraw(user, 50);
            lottery.enter(user);

            handler.reply("You've been entered into the lottery draw.");
        }
    }
}
