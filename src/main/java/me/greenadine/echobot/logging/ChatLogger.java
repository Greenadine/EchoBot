package me.greenadine.echobot.logging;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.util.MessageUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageDeleteEvent;
import org.javacord.api.event.message.MessageEditEvent;
import org.javacord.api.listener.message.MessageDeleteListener;
import org.javacord.api.listener.message.MessageEditListener;

import java.awt.*;

public class ChatLogger implements MessageDeleteListener, MessageEditListener {

    /**
     * Logs all message edits/deletes to given channel.
     */

    // Chat log channel ID
    private long chat_logs = 596442912863289347L;

    // Log to channel when message is deleted.
    @Override
    public void onMessageDelete(MessageDeleteEvent e) {
        if (EchoBot.settings.isExcludedChannel(e.getChannel().getId())) {
            return;
        }

        e.getMessage().ifPresent(message ->
                message.getAuthor().asUser().ifPresent(user -> {
                    EmbedBuilder log = new EmbedBuilder()
                            .setColor(Color.red)
                            .setAuthor(message.getAuthor())
                            .setDescription("**Message sent by " + user.getNicknameMentionTag() + " deleted in** <#" + message.getChannel().getId() + ">\n"
                            + message.getContent())
                            .setFooter("Author: " + user.getId() + " | Message ID: " + message.getId())
                            .setTimestampToNow();

                    EchoBot.bot.getChannelById(chat_logs).ifPresent(channel ->
                            channel.asTextChannel().ifPresent(logChannel ->
                                    logChannel.sendMessage(log))
                    );
                })
        );
    }

    // Log to channel when message is edited.
    @Override
    public void onMessageEdit(MessageEditEvent e) {
        if (EchoBot.settings.isExcludedChannel(e.getChannel().getId())) {
            return;
        }

        e.getMessage().ifPresent(message ->
                message.getAuthor().asUser().ifPresent(user -> {
                    if (user.isBot()) {
                        return;
                    }

                    e.getOldContent().ifPresent(oldContent -> {
                        EmbedBuilder log = new EmbedBuilder()
                                .setColor(Color.cyan)
                                .setAuthor(message.getAuthor())
                                .setDescription("**Message edited in <#" + message.getChannel().getId() + "> [Jump to message](" + MessageUtils.getLink(message) + ")**")
                                .addField("Before", oldContent)
                                .addField("After", e.getNewContent());

                        EchoBot.bot.getChannelById(chat_logs).ifPresent(channel ->
                                channel.asTextChannel().ifPresent(logChannel ->
                                        logChannel.sendMessage(log)
                                )
                        );
                    });
                })
        );
    }
}
