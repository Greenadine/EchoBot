package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.mute.MuteHandler;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Optional;

public class MuteCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "mute";
    }

    public String getDescription() {
        return "Mute a user, preventing them from sending messages in all channels and from joining voice channels for the duration.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!mute <user> <duration>";
    }

    public String getArguments() {
        return "``user`` - The user. Either tag them, or give their ID.\n``duration`` - The amount of time in minutes to mute the user for.";
    }

    public String getAliases() { return null; }

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

            if (!PermissionsHandler.isAssistant(user)) {
                handler.reply(user.getNicknameMentionTag() + " Nice try buddy, you do not have permission to use this command.");
                return;
            }

            if (handler.length() == 0) {
                handler.reply("Please specify the user to mute and the duration in minutes.");
            }

            else if (handler.length() == 1) {
                handler.reply("Please give duration in minutes to mute the user for.");
            }

            else if (handler.length() == 2) {
                Optional<User> optTarget ;

                if (!TagUtils.isUserMentionTag(handler.getArg(0))) {
                    long id;

                    try {
                        id = Long.valueOf(handler.getArg(0));
                    } catch (NumberFormatException ex) {
                        handler.reply("Please give either a user's ID or tag a user like this: " + EchoBot.bot.getYourself().getNicknameMentionTag() + ".");
                        return;
                    }

                    optTarget = EchoBot.bot.getCachedUserById(id);
                } else {
                    optTarget = TagUtils.getUser(handler.getArg(0));
                }

                optTarget.ifPresent(target -> {
                    if (target.isBot()) {
                        handler.reply("That user is a bot.");
                        return;
                    }

                    if (mute.isMuted(target)) {
                        handler.reply("This user is already muted. Mute remainder: " + mute.getFormattedMuteDuration(user) + ".");
                        return;
                    }

                    int duration;

                    try {
                        duration = Integer.valueOf(handler.getArg(1));
                    } catch (NumberFormatException ex) {
                        handler.reply("Duration has to be a number.");
                        return;
                    }

                    if (duration < 1) {
                        handler.reply("Duration has to be at least 1 minute.");
                        return;
                    }

                    if (duration > 1440) {
                        handler.reply("Can not mute a user for longer than a day (1440 minutes).");
                        return;
                    }

                    boolean success = mute.mute(target, duration);

                    if (success) {
                        handler.reply("User " + target.getName() + " muted for " + duration + " minutes.");
                    } else {
                        handler.reply("An internal error has occurred while trying to mute user.");
                    }
                });
            }

            else {
                handler.reply(user.getNicknameMentionTag() + " Invalid command usage. Type ``e!help mute`` for command information.");
            }
        } else {
            handler.reply("Failed to execute command. Reason: User empty.");
        }
    }
}
