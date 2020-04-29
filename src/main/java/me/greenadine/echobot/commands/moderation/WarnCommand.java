package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.handlers.warning.WarningsHandler;
import me.greenadine.echobot.handlers.warning.Rule;
import me.greenadine.echobot.handlers.warning.Warning;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Optional;
import java.util.StringJoiner;

public class WarnCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "warn";
    }

    public String getDescription() {
        return "Give a user a warning.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!warn <user> <rule> [additional notes]";
    }

    public String getArguments() {
        return "``user`` - The user. Either tag them, or give their ID.\n``rule`` - The number of the rule that the user broke.\n``additonal notes`` - Any other information that should be added to the warning.";
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

            if (handler.length() < 2) {
                handler.reply(user.getNicknameMentionTag() + " Invalid command usage. Type ``e!help warn`` for command information.");
            }

            else {
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

                    String number = handler.getArg(1);

                    Rule rule = Rule.fromNumber(number);

                    if (rule == null) {
                        handler.reply("Invalid rule.");
                        return;
                    }

                    StringJoiner notes = new StringJoiner(" ");

                    for (int i = 2; i < handler.getArgs().size(); i++) {
                        String arg = handler.getArgs().get(i);

                        notes.add(arg);
                    }

                    Warning warning = warnings.addWarning(target, target, rule, notes.toString());

                    if (warning != null) {
                        handler.reply("Warning has been issued to user.");

                        target.openPrivateChannel().thenAcceptAsync(pch -> pch.sendMessage("You've received a warning. Details about the warning are below.", warning.toEmbed()));

                        if (warnings.getTotalWarningWeight(target) >= EchoBot.settings.getWarningWeightThreshold()) {
                            target.openPrivateChannel().thenAcceptAsync(pch -> pch.sendMessage("Your current warning weight is " + warnings.getTotalWarningWeight(target) + ". As a result, you may be banned. You will hear from one of the Staff members on the matter."));
                        } else {
                            target.openPrivateChannel().thenAcceptAsync(pch -> pch.sendMessage("You current warning weight is " + warnings.getTotalWarningWeight(target) + ". At a total weight of 5 or higher you may be banned. Be sure to carefully read the rules once more. If you feel like this warning was not in place, you can appeal it by private messaging "));
                        }

                        EchoBot.bot.getTextChannelById(645413244164374579L).ifPresent(channel -> channel.sendMessage(warning.toEmbed().setTitle("Warning issued").setAuthor(user).setTimestampToNow())); // Log warning to #warning-logs

                        if (warnings.getTotalWarningWeight(target) >= EchoBot.settings.getWarningWeightThreshold()) {
                            EchoBot.bot.getTextChannelById(645413604308418609L).ifPresent(channel -> channel.sendMessage("User " + target.getNicknameMentionTag() + " (ID: " + target.getId() + ") has reached a total warning weight of " + warnings.getTotalWarningWeight(target) + ". Please review their warnings below, and decide if the user should be banned.", warnings.getWarningsAsEmbed(target)));
                        }
                    } else {
                        handler.reply("An internal error has occurred while trying to issue warning to user.");
                    }
                } else {
                    handler.reply("Failed to execute command. Reason: Target empty.");
                }
            }
        } else {
            handler.reply("Failed to execute command. Reason: User empty.");
        }
    }
}
