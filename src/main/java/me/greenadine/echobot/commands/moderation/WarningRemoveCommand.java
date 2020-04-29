package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.handlers.warning.WarningsHandler;
import me.greenadine.echobot.handlers.warning.Warning;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Optional;

public class WarningRemoveCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "warning-remove";
    }

    public String getDescription() { return "Remove a warning from a user."; }

    public String getDetails() { return "Remove a warning from a user. Uses the index number of the list show in the ``warning-list`` command."; }

    public String getUsage() {
        return "e!warning-remove <user> <index>";
    }

    public String getArguments() {
        return "``user`` - The user. Either tag them, or give their ID.\n``index`` - The index number of the warning to remove.";
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
                handler.reply("Please specify a user and the index of the warning.");
            }

            else if (handler.length() == 1) {
                handler.reply("Please give the index of the warning to remove.");
            }

            else if (handler.length() == 2) {
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

                    int index;

                    try {
                        index = Integer.valueOf(handler.getArg(1));
                    } catch (NumberFormatException ex) {
                        handler.reply("Index has to be a number.");
                        return;
                    }

                    if (warnings.getWarnings(target).size() == 0) {
                        handler.reply("This user has received no warnings yet.");
                        return;
                    }

                    if (index < 1 || index - 1 > warnings.getWarnings(target).size()) {
                        handler.reply("Invalid index. See ``e!warning-list " + target.getId() + "`` for the list of indexes.");
                        return;
                    }

                    Warning warning = warnings.removeWarning(target, index - 1);

                    if (warning != null) {
                        handler.reply("Successfully removed warning from user.");

                        EchoBot.bot.getTextChannelById(645413244164374579L).ifPresent(channel -> channel.sendMessage(warning.toEmbed().setAuthor(user).setTitle("Warning removed").setTimestampToNow()));
                    } else {
                        handler.reply("An internal error has occurred while trying to remove warning from user.");
                        System.out.println("Error occurred when attempting to remove warning with index '" + (index - 1) + "' from user '" + target.getDiscriminatedName() + "'. Reason: null.");
                    }
                } else {
                    handler.reply("Failed to execute command. Reason: Target empty.");
                }
            }

            else {
                handler.reply("Invalid command usage. Type ``e!help warning-remove`` for command information.");
            }
        } else {
            handler.reply("Failed to execute command. Reason: User empty.");
        }
    }
}
