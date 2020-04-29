package me.greenadine.echobot.logging;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.server.role.UserRoleAddEvent;
import org.javacord.api.event.server.role.UserRoleRemoveEvent;
import org.javacord.api.event.user.UserChangeNameEvent;
import org.javacord.api.event.user.UserChangeNicknameEvent;
import org.javacord.api.listener.server.role.UserRoleAddListener;
import org.javacord.api.listener.server.role.UserRoleRemoveListener;
import org.javacord.api.listener.user.UserChangeNameListener;
import org.javacord.api.listener.user.UserChangeNicknameListener;

import java.awt.*;

public class UserLogger implements UserChangeNameListener, UserChangeNicknameListener, UserRoleAddListener, UserRoleRemoveListener {

    /**
     * Logs all user (nick)name changing and role adding/removing.
     */

    // User log channel ID
    private long user_logs = 691114985564143686L;

    // Log to channel when a user changes their name.
    @Override
    public void onUserChangeName(UserChangeNameEvent e) {
        EmbedBuilder log = new EmbedBuilder()
                .setColor(Color.cyan)
                .setAuthor(e.getUser())
                .setDescription(e.getUser().getNicknameMentionTag() + " **name changed**")
                .addField("Before", e.getOldName())
                .addField("After", e.getNewName())
                .setFooter("ID: " + e.getUser().getId())
                .setTimestampToNow();

        EchoBot.bot.getChannelById(user_logs).ifPresent(channel ->
                channel.asTextChannel().ifPresent(logChannel ->
                        logChannel.sendMessage(log)
                )
        );
    }

    // Log to channel when a user changes their nickname.
    @Override
    public void onUserChangeNickname(UserChangeNicknameEvent e) {
        EmbedBuilder log = new EmbedBuilder()
                .setColor(Color.cyan)
                .setAuthor(e.getUser())
                .setDescription(e.getUser().getNicknameMentionTag() + " **nickname changed**")
                .setFooter("ID: " + e.getUser().getId())
                .setTimestampToNow();

        if (e.getOldNickname().isPresent()) {
            log.addField("Before", e.getOldNickname().get());
        }

        if (e.getNewNickname().isPresent()) {
            log.addField("After", e.getNewNickname().get());
        }

        EchoBot.bot.getChannelById(user_logs).ifPresent(channel ->
                channel.asTextChannel().ifPresent(logChannel ->
                        logChannel.sendMessage(log)
                )
        );
    }

    // Log to channel when a user is added to a role.
    @Override
    public void onUserRoleAdd(UserRoleAddEvent e) {
        EmbedBuilder log = new EmbedBuilder()
                .setColor(Color.cyan)
                .setAuthor(e.getUser())
                .setDescription(e.getUser().getNicknameMentionTag() + " **was given the ``" + e.getRole().getName() + "`` role**")
                .setFooter("ID: " + e.getUser().getId())
                .setTimestampToNow();

        EchoBot.bot.getChannelById(user_logs).ifPresent(channel ->
                channel.asTextChannel().ifPresent(logChannel ->
                        logChannel.sendMessage(log)
                )
        );
    }

    // Log to channel when a user is removed from a role.
    @Override
    public void onUserRoleRemove(UserRoleRemoveEvent e) {
        EmbedBuilder log = new EmbedBuilder()
                .setColor(Color.cyan)
                .setAuthor(e.getUser())
                .setDescription(e.getUser().getNicknameMentionTag() + " **was removed from the ``" + e.getRole().getName() + "`` role**")
                .setFooter("ID: " + e.getUser().getId())
                .setTimestampToNow();

        EchoBot.bot.getChannelById(user_logs).ifPresent(channel ->
                channel.asTextChannel().ifPresent(logChannel ->
                        logChannel.sendMessage(log)
                )
        );
    }
}
