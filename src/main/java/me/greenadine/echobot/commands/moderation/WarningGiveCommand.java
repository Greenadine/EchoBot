package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.handlers.TagHandler;
import me.greenadine.echobot.handlers.WarningHandler;
import me.greenadine.echobot.objects.Warning;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.StringJoiner;

public class WarningGiveCommand implements MessageCreateListener {

    private WarningHandler warnings = EchoBot.warnings;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("warning-give")) {
            User user = handler.getUser();

            if (user == null | user.isBot()) {
                return;
            }

            if (!PermissionsHandler.hasPermission(user, e.getServer().get(), PermissionType.KICK_MEMBERS)) {
                handler.reply("You do not have permission to give warnings.");
                return;
            }

            if (handler.length() == 0) {
                handler.reply("Please specify a user and a description for the warning.");
                return;
            }

            else if (handler.length() == 1) {
                handler.reply("Please add a description for the warning.");
                return;
            }

            else if (handler.length() > 1) {
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

                StringJoiner description = new StringJoiner(" ");

                for (int i = 1; i < handler.getArgs().size(); i++) {
                    String arg = handler.getArgs().get(i);

                    description.add(arg);
                }

                Warning warning = warnings.addWarning(user, tagged, description.toString());

                if (warning != null) {
                    handler.reply("Warning has been issued to user. Warning description:", warning.toEmbed());

                    tagged.openPrivateChannel().thenAcceptAsync(pch -> pch.sendMessage("You've received a warning. Details about the warning are below.", warning.toEmbed()));
                    tagged.openPrivateChannel().thenAcceptAsync(pch -> pch.sendMessage("You currently have " + warnings.getWarningSize(tagged) + " warning(s). At 5 warnings you will be (temporarily) banned. Be sure to carefully read the rules once more."));

                    EchoBot.bot.getTextChannelById(645413244164374579L).ifPresent(channel -> channel.sendMessage("Warning issued", warning.toEmbed().setTimestampToNow()));

                    if (warnings.getWarningSize(tagged) == 5) {
                        EchoBot.bot.getTextChannelById(645413604308418609L).ifPresent(channel -> channel.sendMessage("User " + tagged.getNicknameMentionTag() + " has reached 5 warnings. Please review their warnings below, and decide if the user should be (temporarily) banned.", warnings.getWarningsAsEmbed(tagged)));
                    }
                } else {
                    handler.reply("An internal error has occurred while trying to issue warning to user.");
                }
            }
        }
    }
}
