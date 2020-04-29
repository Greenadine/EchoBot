package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.handlers.warning.WarningsHandler;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Optional;

public class WarningListCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "warning-list";
    }

    public String getDescription() {
        return "View the warnings a user has received so far.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!warning-list <user>";
    }

    public String getArguments() {
        return "``user`` - The user. Either tag them, or give their ID.";
    }

    public String getAliases() { return null; }

    private WarningsHandler warnings = EchoBot.warnings;

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
                handler.reply("Please specify a user.");
                return;
            }

            if (handler.length() == 1) {
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

                    if (!warnings.hasData(target)) {
                        warnings.register(target);
                    }

                    if (warnings.getWarnings(target).size() == 0) {
                        handler.reply("This user has received no warnings yet.");
                        return;
                    }

                    handler.reply(warnings.getWarningsAsEmbed(target));
                } else {
                    handler.reply("Failed to execute command. Reason: Target empty.");
                }
            } else {
                handler.reply("");
            }
        } else {
            handler.reply("Failed to execute command. Reason: User empty.");
        }
    }
}
