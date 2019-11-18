package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.handlers.TagHandler;
import me.greenadine.echobot.handlers.WarningHandler;
import me.greenadine.echobot.objects.Warning;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class WarningListCommand implements MessageCreateListener {

    private WarningHandler warnings = EchoBot.warnings;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("warning-list")) {
            User user = handler.getUser();

            if (user == null | user.isBot()) {
                return;
            }

            if (!PermissionsHandler.hasPermission(user, e.getServer().get(), PermissionType.KICK_MEMBERS)) {
                handler.reply("You do not have permission to see other user's their warnings.");
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

                handler.reply(warnings.getWarningsAsEmbed(tagged));
            }
        }
    }
}
