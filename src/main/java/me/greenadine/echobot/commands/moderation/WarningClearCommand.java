package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.handlers.TagHandler;
import me.greenadine.echobot.handlers.WarningHandler;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class WarningClearCommand implements MessageCreateListener {

    private WarningHandler warnings = EchoBot.warnings;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("warning-clear")) {
            User user = handler.getUser();

            if (user == null || user.isBot()) {
                return;
            }

            if (!PermissionsHandler.hasPermission(user, e.getServer().get(), PermissionType.KICK_MEMBERS)) {
                handler.reply("You do not have permission to clear a user's warnings.");
                return;
            }

            if (handler.length() == 0) {
                handler.reply("Please specify a user.");
                return;
            }

            if (handler.length() == 1) {
                if (!TagHandler.isTag(handler.getArg(0))) {
                    handler.reply("Please tag a user like this: " + EchoBot.bot.getYourself().getNicknameMentionTag() + ".");
                    return;
                }

                User tagged = TagHandler.getUser(handler.getArg(0));

                if (tagged.isBot()) {
                    handler.reply("That user is a bot.");
                    return;
                }

                if (!warnings.hasData(tagged)) {
                    warnings.register(tagged);
                }

                if (warnings.getWarnings(tagged).size() == 0) {
                    handler.reply("This user has received no warnings yet.");
                    return;
                }

                boolean success = warnings.clear(tagged);

                if (success) {
                    handler.reply("Warnings cleared.");
                } else {
                    handler.reply("An internal error has occurred while trying to clear the user's warnings.");
                }
            } else {
                handler.reply(user.getNicknameMentionTag() + " Invalid command usage. Type ``e!help warning-clear`` for command information.");
            }
        }
    }
}
