package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.handlers.TagHandler;
import me.greenadine.echobot.handlers.Warnings;
import me.greenadine.echobot.objects.Warning;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class WarningRemoveCommand implements MessageCreateListener {

    private Warnings warnings = EchoBot.warnings;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("warning-remove")) {
            User user = handler.getUser();

            if (user == null | user.isBot()) {
                return;
            }

            if (!PermissionsHandler.hasPermission(user, e.getServer().get(), PermissionType.KICK_MEMBERS)) {
                handler.reply("You do not have permission to remove warnings.");
                return;
            }

            if (handler.length() == 0) {
                handler.reply("Please specify a user and the index of the warning.");
                return;
            }

            else if (handler.length() == 1) {
                handler.reply("Please give the index of the warning to remove.");
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

                if (!warnings.hasData(tagged)) {
                    warnings.register(tagged);
                }

                int index;

                try {
                    index = Integer.valueOf(handler.getArg(1));
                } catch (NumberFormatException ex) {
                    handler.reply("Index has to be a number.");
                    return;
                }

                if (warnings.getWarnings(tagged).size() == 0) {
                    handler.reply("This user has received no warnings yet.");
                    return;
                }

                if (index < 1 || index - 1 > warnings.getWarnings(tagged).size()) {
                    handler.reply("Invalid index. See ``e!warning-list " + tagged.getNicknameMentionTag() + "`` for the list of indexes.");
                    return;
                }

                Warning warning = warnings.removeWarning(user, index - 1);

                if (warning != null) {
                    handler.reply("Successfully removed warning from user.");

                    EchoBot.bot.getTextChannelById(645413244164374579L).ifPresent(channel -> channel.sendMessage("Warning removed", warning.toEmbed().setTimestampToNow()));
                } else {
                    handler.reply("An internal error has occurred while trying to issue warning to user.");
                }
            }

            else {
                handler.reply(user.getNicknameMentionTag() + " Invalid command usage. Type ``e!help warning-remove`` for command information.");
            }
        }
    }
}
