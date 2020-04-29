package me.greenadine.echobot.commands;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.MessageHandler;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class UnknownCommandListener implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        MessageHandler handler = new MessageHandler(e);

        if (handler.startsWith(EchoBot.prefix)) {
            String cmd = handler.getArg(0).substring(EchoBot.prefix.length());

            if (!cmd.equalsIgnoreCase("help") && !EchoBot.commands.getCommand(cmd).isPresent()) {
                e.getChannel().sendMessage("Unknown command '" + cmd + "'. See ``e!help`` for a list of commands.");
            }
        }
    }
}
