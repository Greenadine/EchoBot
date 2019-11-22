package me.greenadine.echobot.commands.game;

import me.greenadine.echobot.handlers.CommandHandler;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.io.File;
import java.util.Random;

public class CoinflipCommand implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("coinflip")) {
            if (handler.length() != 0) {
                handler.reply("Invalid command usage. Type ``e!help coinflip`` for command information.");
                return;
            }

            boolean side = new Random().nextBoolean();

            if (side) {
                handler.reply("Heads.", new File("C:/Users/kevin/IdeaProjects/EchoBot/src/main/java/me/greenadine/echobot/assets/images/head.png"));
            } else {
                handler.reply("Tails.", new File("C:/Users/kevin/IdeaProjects/EchoBot/src/main/java/me/greenadine/echobot/assets/images/tail.png"));
            }
        }
    }
}
