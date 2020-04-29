package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.Levels;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.Optional;

public class RankCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "rank";
    }

    public String getDescription() {
        return "Check your or someone else's level.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!rank [user]";
    }

    public String getArguments() {
        return "``user`` - The user. Either tag them, or give their ID.";
    }

    public String getAliases() { return null; }

    private Levels lvl = EchoBot.lvl;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (handler.getUser().isPresent()) {
            User user = handler.getUser().get();

            if (handler.length() == 0) {
                if (!lvl.hasData(user)) {
                    lvl.register(user);
                }
                int xp = lvl.getXp(user);

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle(user.getName() + "'s balance")
                        .addField("Your are currently", "Level " + lvl.calculateLevel(xp) + " (" + xp + " XP).")
                        .setColor(Color.CYAN)
                        .setThumbnail(user.getAvatar());

                int topPos = lvl.getTopPosition(user);

                if (topPos <= 10) {
                    embed.setFooter("Your current position on the leaderboard is #" + lvl.getTopPosition(user) + ".");
                } else {
                    embed.setFooter("You are currently not on the leaderboard.");
                }

                handler.reply(embed);
            }

            else if (handler.length() == 1) {
                if (e.getServer().isPresent()) {
                    if (!PermissionsHandler.isAssistant(user)) {
                        handler.reply(user.getNicknameMentionTag() + "Nice try buddy, but you don't have permision to check other people's rank.");
                        return;
                    }
                } else {
                    handler.reply("Failed to execute command. Reason: Permission check fail (Server empty).");
                    return;
                }

                Optional<User> optTarget;

                if (!TagUtils.isUserMentionTag(handler.getArg(0))) {
                    long id;
                    try {
                        id = Long.valueOf(handler.getArg(0));
                    } catch (NumberFormatException  ex) {
                        handler.reply("Please either give a user's ID or tag a user like this: " + EchoBot.bot.getYourself().getNicknameMentionTag() + ".");
                        return;
                    }

                    if (EchoBot.bot.getCachedUserById(id).isPresent()) {
                        optTarget = EchoBot.bot.getCachedUserById(id);
                    } else {
                        handler.reply("User with ID '" + handler.getArg(0)+ "' could not be found.");
                        return;
                    }
                } else {
                    optTarget = TagUtils.getUser(handler.getArg(0));
                }

                if (optTarget.isPresent()) {
                    User target = optTarget.get();

                    if (target.isBot()) {
                        handler.reply("That user is a bot.");
                        return;
                    }

                    if (!lvl.hasData(target)) {
                        lvl.register(target);
                    }

                    int xp = lvl.getXp(target);

                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle(target.getName() + "'s Rank")
                            .addField("They are currently", "Level " + lvl.calculateLevel(xp) + " (" + xp + " XP).")
                            .setFooter("Their current position in the leaderboard is #" + lvl.getTopPosition(target) + ".")
                            .setColor(Color.CYAN)
                            .setThumbnail(target.getAvatar());

                    handler.reply(embed);
                } else {
                    handler.reply("Failed to execute command. Reason: Target empty.");
                }
            }

            else {
                handler.reply("Invalid command usage. Type ``e!help rank`` for command information.");
            }
        }
    }
}
