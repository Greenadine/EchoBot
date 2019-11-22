package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.Levels;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.handlers.TagHandler;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class RankCommand implements MessageCreateListener {

    private Levels lvl = EchoBot.lvl;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("rank")) {
            User user = handler.getUser();

            if (handler.length() == 0) {
                if (!lvl.hasData(user)) {
                    lvl.register(user);
                }
                int xp = lvl.getXp(user);

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle(user.getName() + "'s Rank")
                        .addField("You are currently", "Level " + lvl.calculateLevel(xp) + " (" + xp + " XP).")
                        .setFooter("Your current position in the leaderboard is #" + lvl.getTopPosition(user) + ".")
                        .setColor(Color.CYAN)
                        .setThumbnail(user.getAvatar());

                handler.reply(embed);
            }

            else if (handler.length() == 1) {
                if (!PermissionsHandler.hasPermission(user, e.getMessage().getServer().get(), PermissionType.MANAGE_ROLES)) {
                    handler.reply(user.getNicknameMentionTag() + " You do not have permission to check other people's balances.");
                    return;
                }

                if (!TagHandler.isTag(handler.getArg(0))) {
                    handler.reply("Please tag a user like this: " + EchoBot.bot.getYourself().getNicknameMentionTag() + ".");
                    return;
                }

                User tagged = TagHandler.getUser(handler.getArg(0));

                if (tagged.isBot()) {
                    handler.reply("That user is a bot.");
                    return;
                }

                if (!lvl.hasData(tagged)) {
                    lvl.register(tagged);
                }

                int xp = lvl.getXp(tagged);

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle(user.getName() + "'s Rank")
                        .addField("They are currently", "Level " + lvl.calculateLevel(xp) + " (" + xp + " XP).")
                        .setFooter("Their current position in the leaderboard is #" + lvl.getTopPosition(tagged) + ".")
                        .setColor(Color.CYAN)
                        .setThumbnail(user.getAvatar());

                handler.reply(embed);
                return;
            }

            else {
                handler.reply("Invalid command usage. Type ``e!help rank`` for command information.");
                return;
            }
        }
    }
}
