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

public class UnmuteCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "unmute";
    }

    public String getDescription() {
        return "Unmute a currently muted user..";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!unmute <user>";
    }

    public String getArguments() {
        return "``user`` - The user. Either tag them, or give their ID.";
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
                handler.reply("Please specify a user to un-mute.");
            }

            else if (handler.length() == 1) {
                Optional<User> optTarget;

                if (!TagUtils.isUserMentionTag(handler.getArg(0))) {
                    long id;

                    try {
                        id = Long.valueOf(handler.getArg(0));
                    } catch (NumberFormatException ex) {
                        handler.reply("Please either give a user's ID or tag a user like this: " + EchoBot.bot.getYourself().getNicknameMentionTag() + ".");
                        return;
                    }

                    optTarget = EchoBot.bot.getCachedUserById(id);
                } else {
                    optTarget = TagUtils.getUser(handler.getArg(0));
                }

                if (optTarget.isPresent()) {
                    User target = optTarget.get();

                    if (target.isBot()) {
                        handler.reply("That user is a bot.");
                        return;
                    }

                    if (!mute.isMuted(target)) {
                        handler.reply("That user is not muted.");
                        return;
                    }

                    boolean success = mute.unmute(target);

                    if (success) {
                        handler.reply("User un-muted.");
                    } else {
                        handler.reply("An internal error has occurred while trying to un-mute user.");
                    }
                } else {
                    handler.reply("Failed to execute command. Reason: Target empty.");
                }
            }

            else {
                handler.reply(user.getNicknameMentionTag() + " Invalid command usage. Type ``e!help unmute`` for command information.");
            }
        } else {
            handler.reply("Failed to execute command. Reason: User empty.");
        }
    }
}
