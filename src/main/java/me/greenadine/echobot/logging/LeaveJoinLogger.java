package me.greenadine.echobot.logging;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.event.server.member.ServerMemberLeaveEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;
import org.javacord.api.listener.server.member.ServerMemberLeaveListener;

import java.awt.*;

public class LeaveJoinLogger implements ServerMemberJoinListener, ServerMemberLeaveListener {

    /**
     * Logs all member joining/leaving.
     */

    // Leave join log channel ID
    private long leave_join_logs = 633421476661297175L;

    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent e) {
        EmbedBuilder log = new EmbedBuilder()
                .setColor(Color.green)
                .setAuthor("Member Joined", null, e.getUser().getAvatar())
                .setThumbnail(e.getUser().getAvatar())
                .setDescription(e.getUser().getMentionTag() + " " + e.getUser().getDiscriminatedName())
                .setFooter("ID: " + e.getUser().getId())
                .setTimestampToNow();

        EchoBot.bot.getChannelById(leave_join_logs).ifPresent(channel ->
                channel.asTextChannel().ifPresent(logChannel ->
                        logChannel.sendMessage(log)
                )
        );
    }

    @Override
    public void onServerMemberLeave(ServerMemberLeaveEvent e) {
        EmbedBuilder log = new EmbedBuilder()
                .setColor(Color.red)
                .setAuthor("Member Left", null, e.getUser().getAvatar())
                .setThumbnail(e.getUser().getAvatar())
                .setDescription(e.getUser().getMentionTag() + " " + e.getUser().getDiscriminatedName())
                .setFooter("ID: " + e.getUser().getId())
                .setTimestampToNow();

        EchoBot.bot.getChannelById(leave_join_logs).ifPresent(channel ->
                channel.asTextChannel().ifPresent(logChannel ->
                        logChannel.sendMessage(log)
                )
        );
    }
}
