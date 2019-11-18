package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.MuteHandler;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.handlers.TagHandler;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class MuteCommand implements MessageCreateListener {

    private MuteHandler mute = EchoBot.mute;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("mute")) {
            User user = handler.getUser();

            if (user == null | user.isBot()) {
                return;
            }

            if (!PermissionsHandler.hasPermission(user, e.getServer().get(), PermissionType.KICK_MEMBERS)) {
                handler.reply("You do not have permission to mute users.");
                return;
            }

            if (handler.length() == 0) {
                handler.reply("Please specify the user to mute and the duration in minutes.");
                return;
            }

            else if (handler.length() == 1) {
                handler.reply("Please give duration in minutes to mute the user for.");
                return;
            }

            else if (handler.length() == 2) {
                if (!TagHandler.isTag(handler.getArg(0))) {
                    handler.reply( "Please tag a user like this: " + EchoBot.bot.getYourself().getNicknameMentionTag() + ".");
                    return;
                }

                User tagged = TagHandler.getUser(handler.getArg(0));

                if (tagged.isBot()) {
                    handler.reply("That user is a bot.");
                    return;
                }

                if (mute.isMuted(tagged)) {
                    handler.reply("This user is already muted. Mute remainder: " + mute.getFormattedMuteDuration(user) + ".");
                    return;
                }

                long duration;

                try {
                    duration = Long.valueOf(handler.getArg(1));
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

                boolean success = mute.mute(tagged, duration);

                if (success) {
                    handler.reply("User " + tagged.getName() + " muted for " + duration + " minutes.");
                    return;
                } else {
                    handler.reply("An internal error has occurred while trying to mute user.");
                    return;
                }
            }

            else {
                handler.reply(user.getNicknameMentionTag() + " Invalid command usage. Type ``e!help mute`` for command information.");
            }
        }
    }
}
