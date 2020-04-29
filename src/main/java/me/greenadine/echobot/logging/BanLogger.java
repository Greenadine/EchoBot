package me.greenadine.echobot.logging;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.server.member.ServerMemberBanEvent;
import org.javacord.api.event.server.member.ServerMemberUnbanEvent;
import org.javacord.api.listener.server.member.ServerMemberBanListener;
import org.javacord.api.listener.server.member.ServerMemberUnbanListener;

import java.awt.*;

public class BanLogger implements ServerMemberBanListener, ServerMemberUnbanListener {

    /**
     * Logs all member banning/unbanning.
     */

    // Ban log channel ID
    private long ban_logs = 638091412709179411L;

    // Log to channel when a member is banned from the server.
    @Override
    public void onServerMemberBan(ServerMemberBanEvent e) {
        EmbedBuilder log = new EmbedBuilder()
                .setColor(Color.red)
                .setAuthor("Member Banned", null, e.getUser().getAvatar())
                .setThumbnail(e.getUser().getAvatar())
                .setDescription(e.getUser().getMentionTag() + " " + e.getUser().getDiscriminatedName())
                .setFooter("ID: " + e.getUser().getId())
                .setTimestampToNow();

        EchoBot.bot.getChannelById(ban_logs).ifPresent(channel ->
                channel.asTextChannel().ifPresent(logChannel ->
                        logChannel.sendMessage(log)
                )
        );
    }

    // Log to channel when a member is unbanned from the server.
    @Override
    public void onServerMemberUnban(ServerMemberUnbanEvent e) {
        EmbedBuilder log = new EmbedBuilder()
                .setColor(Color.green)
                .setAuthor("Member Unbanned", null, e.getUser().getAvatar())
                .setThumbnail(e.getUser().getAvatar())
                .setDescription(e.getUser().getMentionTag() + " " + e.getUser().getDiscriminatedName())
                .setFooter("ID: " + e.getUser().getId())
                .setTimestampToNow();

        EchoBot.bot.getChannelById(ban_logs).ifPresent(channel ->
                channel.asTextChannel().ifPresent(logChannel ->
                        logChannel.sendMessage(log)
                )
        );
    }
}
