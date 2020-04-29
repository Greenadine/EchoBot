package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.handlers.*;
import me.greenadine.echobot.handlers.mute.MuteHandler;
import me.greenadine.echobot.handlers.warning.WarningsHandler;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.Optional;

public class StatusCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "status";
    }

    public String getDescription() {
        return "View information on a user.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!status <user>";
    }

    public String getArguments() {
        return "``user`` - The user. Either tag them, or give their ID.";
    }

    public String getAliases() { return null; }

    private Levels lvl = EchoBot.lvl;
    private Economy econ = EchoBot.econ;
    private WarningsHandler warning = EchoBot.warnings;
    private MuteHandler mute = EchoBot.mute;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (handler.getUser().isPresent()) {
            User user = handler.getUser().get();

            if (user.isBot()) {
                return;
            }

            if (e.getServer().isPresent()) {
                if (!PermissionsHandler.isAssistant(user)) {
                    handler.reply(user.getNicknameMentionTag() + " Nice try buddy, but you don't have permission to use this command.");
                    return;
                }
            } else {
                handler.reply("Failed to execute command. Reason: Permission check fail (Server empty).");
                return;
            }

            if (!PermissionsHandler.hasPermission(user, e.getServer().get(), PermissionType.KICK_MEMBERS)) {
                handler.reply("You do not have permission to check users' statuses.");
                return;
            }

            if (handler.length() == 0) {
                handler.reply("Please specify the user to check.");
            }

            else if (handler.length() == 1) {
                Optional<User> optTarget;

                if (!TagUtils.isUserMentionTag(handler.getArg(0))) {
                    long id;

                    try {
                        id = Long.valueOf(handler.getArg(0));
                    } catch (NumberFormatException ex) {
                        handler.reply("Please either give a user's ID or tag a user like this: " + EchoBot.bot.getYourself().getNicknameMentionTag() + ".");
                        return;
                    }

                    optTarget = EchoBot.bot.getCachedUserById(id);
                } else {
                    optTarget = TagUtils.getUser(handler.getArg(0));
                }

                if (optTarget.isPresent()) {
                    User target = optTarget.get();

                    if (target.isBot()) {
                        handler.reply("That user is a bot.");
                        return;
                    }

                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Status")
                            .setDescription("Information of user " + target.getNicknameMentionTag())
                            .setThumbnail(target.getAvatar())
                            .setColor(Color.CYAN);

                    embed.addInlineField("Username", target.getName());
                    embed.addInlineField("Discord Name & Tag", target.getDiscriminatedName());

                    if (EchoBot.bot.getServerById(595246997762605065L).isPresent()) {
                        if (target.getNickname(EchoBot.bot.getServerById(595246997762605065L).get()).isPresent()) {
                            embed.addInlineField("Nickname", target.getNickname(EchoBot.bot.getServerById(595246997762605065L).get()).get());
                        } else {
                            embed.addInlineField("Nickname", "None");
                        }
                    } else {
                        embed.addInlineField("Nickname", "None");
                    }

                    embed.addInlineField("Level", lvl.calculateLevel(lvl.getXp(target)) + " (" + lvl.getXp(target) + " XP)");
                    embed.addInlineField("Balance", econ.getBalance(target) + " Gold");
                    embed.addInlineField("# of Warnings", String.valueOf(warning.getWarningSize(target)));

                    if (mute.isMuted(target)) {
                        embed.addInlineField("Muted", "Yes (" + mute.getFormattedMuteDuration(target) + ")");
                    } else {
                        embed.addInlineField("Muted", "No");
                    }

                    handler.reply(embed);
                } else {
                    handler.reply("Failed to execute command. Reason: Target empty.");
                }
            } else {
                handler.reply("Invalid command usage. Type ``e!help status`` for command information.");
            }
        } else {
            handler.reply("Failed to execute command. Reason: User empty.");
        }
    }
}
