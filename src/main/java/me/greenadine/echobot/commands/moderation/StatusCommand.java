package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.*;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class StatusCommand implements MessageCreateListener {

    private Levels lvl = EchoBot.lvl;
    private Economy econ = EchoBot.econ;
    private Warnings warning = EchoBot.warnings;
    private Muter mute = EchoBot.mute;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("status")) {
            User user = handler.getUser();

            if (user == null || user.isBot()) {
                return;
            }

            if (!PermissionsHandler.hasPermission(user, e.getServer().get(), PermissionType.KICK_MEMBERS)) {
                handler.reply("You do not have permission to check users' statuses.");
                return;
            }

            if (handler.length() == 0) {
                handler.reply("Please specify the user to check.");
                return;
            }

            else if (handler.length() == 1) {
                if (!TagHandler.isTag(handler.getArg(0))) {
                    handler.reply( "Please tag a user like this: " + EchoBot.bot.getYourself().getNicknameMentionTag() + ".");
                    return;
                }

                User tagged = TagHandler.getUser(handler.getArg(0));

                if (tagged.isBot()) {
                    handler.reply("That user is a bot.");
                    return;
                }

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Status")
                        .setDescription("Information of user " + tagged.getNicknameMentionTag())
                        .setThumbnail(tagged.getAvatar())
                        .setColor(Color.CYAN);

                embed.addInlineField("Username:", tagged.getName());
                embed.addInlineField("Discord Name & Tag", tagged.getDiscriminatedName());
                EchoBot.bot.getServerById(595246997762605065L).ifPresent(server -> embed.addInlineField("Username:", tagged.getNickname(server).get()));
                embed.addInlineField("Level:", lvl.calculateLevel(lvl.getXp(tagged)) + " (" + lvl.getXp(tagged) + " XP)");
                embed.addInlineField("Balance:", econ.getBalance(tagged) + " Gold");
                embed.addInlineField("# of Warnings:", String.valueOf(warning.getWarningSize(tagged)));
                if (mute.isMuted(tagged)) {
                    embed.addInlineField("Muted:", "Yes (" + mute.getFormattedMuteDuration(tagged) + ")");
                } else {
                    embed.addInlineField("Muted:", "No");
                }

                handler.reply(embed);
            }
        }
    }
}
