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

public class UnmuteCommand implements MessageCreateListener {

    private MuteHandler mute = EchoBot.mute;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("unmute")) {
            User user = handler.getUser();

            if (user == null | user.isBot()) {
                return;
            }

            if (!PermissionsHandler.hasPermission(user, e.getServer().get(), PermissionType.KICK_MEMBERS)) {
                handler.reply("You do not have permission to un-mute users.");
                return;
            }

            if (handler.length() == 0) {
                handler.reply("Please specify a user to un-mute.");
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

                if (!mute.isMuted(tagged)) {
                    handler.reply("That user is not muted.");
                    return;
                }

                boolean success = mute.unmute(tagged);

                if (success) {
                    handler.reply("User un-muted.");
                    return;
                } else {
                    handler.reply("An internal error has occurred while trying to un-mute user.");
                }
            }

            else {
                handler.reply(user.getNicknameMentionTag() + " Invalid command usage. Type ``e!help unmute`` for command information.");
            }
        }
    }
}
