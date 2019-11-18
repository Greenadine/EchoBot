package me.greenadine.echobot.commands.fun;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.Phrases;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class FutureCommand implements MessageCreateListener {

    private Phrases phrases = EchoBot.future;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("future")) {
            if (handler.length() != 0) {
                handler.reply("Invalid command usage. Type ``e!help future`` for command information.");
                return;
            }

            handler.reply(phrases.getRandom().replaceAll("%mention%", handler.getUser().getNicknameMentionTag()));
        }
    }

}