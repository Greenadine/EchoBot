package me.greenadine.echobot.logging;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.channel.server.*;
import org.javacord.api.listener.channel.server.*;

import java.awt.*;
import java.util.StringJoiner;

public class ModerationLogger implements ServerChannelCreateListener, ServerChannelDeleteListener, ServerChannelChangeNameListener {

    /**
     * Logs all administrative/moderative actions taken on the server.
     */

    // Moderation log channel ID
    private long moderation_logs = 691107969315635251L;

    // Log to channel when a channel (category) is created.
    @Override
    public void onServerChannelCreate(ServerChannelCreateEvent e) {
        ServerChannel channel = e.getChannel();

        EmbedBuilder log = new EmbedBuilder()
                .setColor(Color.green);

        if (e.getServer().getIcon().isPresent()) {
            log.setAuthor(e.getServer().getName(), null, e.getServer().getIcon().get());
        } else {
            log.setAuthor(e.getServer().getName());
        }

        StringJoiner desc = new StringJoiner(" ");

        if (channel.getType().equals(ChannelType.SERVER_TEXT_CHANNEL)) {
            desc.add("Text channel");
        }
        else if (channel.getType().equals(ChannelType.SERVER_VOICE_CHANNEL)) {
            desc.add("Voice channel");
        }
        else if (channel.getType().equals(ChannelType.SERVER_NEWS_CHANNEL)) {
            desc.add("News channel");
        }
        else if (channel.getType().equals(ChannelType.CHANNEL_CATEGORY)) {
            desc.add("Channel category");
        }

        desc.add("created: #" + channel.getName());

        log.setDescription(desc.toString());
        log.setFooter("ID: " + e.getChannel().getId());
        log.setTimestampToNow();

        EchoBot.bot.getChannelById(moderation_logs).ifPresent(serverChannel ->
                serverChannel.asTextChannel().ifPresent(logChannel ->
                        logChannel.sendMessage(log))
        );
    }

    // Log to channel when a channel (category) is deleted.
    @Override
    public void onServerChannelDelete(ServerChannelDeleteEvent e) {
        ServerChannel channel = e.getChannel();

        EmbedBuilder log = new EmbedBuilder()
                .setColor(Color.red);

        if (e.getServer().getIcon().isPresent()) {
            log.setAuthor(e.getServer().getName(), null, e.getServer().getIcon().get());
        } else {
            log.setAuthor(e.getServer().getName());
        }

        StringJoiner desc = new StringJoiner(" ");

        if (channel.getType().equals(ChannelType.SERVER_TEXT_CHANNEL)) {
            desc.add("Text channel");
        }
        else if (channel.getType().equals(ChannelType.SERVER_VOICE_CHANNEL)) {
            desc.add("Voice channel");
        }
        else if (channel.getType().equals(ChannelType.SERVER_NEWS_CHANNEL)) {
            desc.add("News channel");
        }
        else if (channel.getType().equals(ChannelType.CHANNEL_CATEGORY)) {
            desc.add("Channel category");
        }

        desc.add("deleted: #" + channel.getName());

        log.setDescription("**" + desc.toString() + "**");
        log.setFooter("ID: " + e.getChannel().getId());
        log.setTimestampToNow();

        EchoBot.bot.getChannelById(moderation_logs).ifPresent(serverChannel ->
                serverChannel.asTextChannel().ifPresent(logChannel ->
                        logChannel.sendMessage(log))
        );
    }

    // Log to channel when a channel (category) is renamed.
    @Override
    public void onServerChannelChangeName(ServerChannelChangeNameEvent e) {
        EmbedBuilder log = new EmbedBuilder()
                .setColor(Color.cyan)
                .setDescription("Channel name changed");

        if (e.getServer().getIcon().isPresent()) {
            log.setAuthor(e.getServer().getName(), null, e.getServer().getIcon().get());
        } else {
            log.setAuthor(e.getServer().getName());
        }

        log.addField("Before", "#" + e.getOldName());
        log.addField("After", "#" + e.getNewName());

        log.setFooter("ID: " + e.getChannel().getId());
        log.setTimestampToNow();

        EchoBot.bot.getChannelById(moderation_logs).ifPresent(serverChannel ->
                serverChannel.asTextChannel().ifPresent(logChannel ->
                        logChannel.sendMessage(log))
        );
    }
}
