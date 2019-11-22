package me.greenadine.echobot.listeners;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.MessageHandler;
import me.greenadine.echobot.handlers.Notifier;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class NotifyListener implements MessageCreateListener {

    private Notifier notifier = EchoBot.notifier;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        MessageHandler handler = new MessageHandler(e);

        if (handler.getChannel().getId() == 595252321336688643L) { // In 'repeat' channel
            if (handler.getMessage().contains("early for Patrons") || handler.getMessage().contains("Early for Patrons")) {
                for (Long id : notifier.getRepeatPatronList()) {
                    EchoBot.bot.getCachedUserById(id).ifPresent(user ->
                            user.openPrivateChannel().thenAcceptAsync(channel ->
                                channel.sendMessage("A new Repeat build has been released, currently available early for Patrons! Go to the announcement message in the server for a link to the Patreon post."))
                    );
                }
            } else {
                for (Long id : notifier.getRepeatList()) {
                    EchoBot.bot.getCachedUserById(id).ifPresent(user ->
                            user.openPrivateChannel().thenAcceptAsync(channel ->
                                    channel.sendMessage("A new Repeat build has become publicly available! Go to the announcement message in the server for a link to the Patreon post."))
                    );
                }
            }
        }

        if (handler.getChannel().getId() == 595252911194243072L) { // In 'patreon' channel
            for (Long id : notifier.getPatreonList()) {
                EchoBot.bot.getCachedUserById(id).ifPresent(user ->
                        user.openPrivateChannel().thenAcceptAsync(channel ->
                                channel.sendMessage("A new post has been placed on Shirokoi's Patreon! Go to the announcement message in the server for a link to the Patreon post."))
                );
            }
        }
    }
}