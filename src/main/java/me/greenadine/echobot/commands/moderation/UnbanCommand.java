package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.handlers.BanHandler;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class UnbanCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "unban";
    }

    public String getDescription() {
        return "Unban a user from the server.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!unban <user>";
    }

    public String getArguments() {
        return "user - The user. Either tag them, or give their ID.";
    }

    public String getAliases() { return null; }

    private BanHandler banHandler = EchoBot.bans;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (!handler.getUser().isPresent()) { // User optional is empty
            handler.reply("Failed to execute command. Reason: User empty.");
            return;
        }

        // Get the user that invoked the command.
        User user = handler.getUser().get();

        // If user does not have permission to use command (is not a moderator or higher)
        if (!PermissionsHandler.isModerator(user)) {
            handler.reply(user.getNicknameMentionTag() + " Nice try buddy, you do not have permission to use this command.");
            return;
        }

        if (handler.length() != 1) {
            handler.reply("Invalid command usage. Type ``e!help ban`` for command information.");
            return;
        }

        User target;

        // Retrieve user instance from tag mention, or from given ID.
        if (!TagUtils.isUserMentionTag(handler.getArg(0))) {
            long id;

            try {
                id = Long.valueOf(handler.getArg(0));
            } catch (NumberFormatException ex) {
                handler.reply("Please either give a user's ID or tag them like this: " + EchoBot.bot.getYourself().getNicknameMentionTag() + ".");
                return;
            }

            target = EchoBot.bot.getUserById(id).join();

            if (target == null) {
                handler.reply("Could not find user with ID '" + id + "'.");
                return;
            }
        } else {
            if (TagUtils.getUser(handler.getArg(0)).isPresent()) {
                target = TagUtils.getUser(handler.getArg(0)).get();
            } else {
                handler.reply("Failed to execute command. Reason: Target empty.");
                return;
            }
        }

        if (!e.getServer().isPresent()) {
            handler.reply("Failed to execute command. Reason: Server empty.");
            return;
        }

        if (!banHandler.isBanned(target, e.getServer().get())) {
            handler.reply("User ``" + target.getDiscriminatedName() + "`` (ID: " + target.getId() + ") is not currently banned from the server.");
            return;
        }

        boolean success = banHandler.unbanUser(target, e.getServer().get());

        if (success) {
            handler.reply("User ``" + target.getDiscriminatedName() + "`` (ID: " + target.getId() + ") has been unbanned from the server.");
        } else {
            handler.reply("An internal error has occurred while trying to unban user.");
        }
    }
}
