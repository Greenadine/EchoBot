package me.greenadine.echobot.commands.economy;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.util.TagUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.Optional;

public class BalanceCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "balance";
    }

    public String getDescription() {
        return "Check how much Gold you or someone else currently has.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!balance [user]";
    }

    public String getArguments() {
        return "``user`` (optional) - The user. Either tag them, or give their ID.";
    }

    public String getAliases() { return "bal"; }

    private Economy econ = EchoBot.econ;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) {return;}

        if (!handler.getUser().isPresent()) { // User optional is empty
            handler.reply("Failed to execute command. Reason: User empty.");
            return;
        }

        // Get user that invoked command
        User user = handler.getUser().get();

        // Check user's own balance
        if (handler.length() == 0) {
            // If user has no data yet, register them
            if (!econ.hasData(user)) {
                econ.register(user);
            }

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle(user.getName() + "'s balance")
                    .addField("Your balance is", econ.getFormattedBalance(user) + " Gold")
                    .setColor(Color.CYAN)
                    .setThumbnail(user.getAvatar());

            int topPos = econ.getTopPosition(user);

            if (topPos <= 10) {
                embed.setFooter("Your current position on the leaderboard is #" + econ.getTopPosition(user) + ".");
            } else {
                embed.setFooter("You are currently not on the leaderboard.");
            }

            handler.reply(embed);
        }

        // Check other user's balance (target)
        else if (handler.length() == 1) {
            // If user does not have permission to use command (is not an assistant or higher)
            if (!PermissionsHandler.isAssistant(user)) {
                handler.reply(user.getNicknameMentionTag() + " You do not have permission to check other people's balances.");
                return;
            }

            Optional<User> optTarget;

            // Retrieve user instance from tag mention, or from given ID.
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

            // Target optional is empty
            if (!optTarget.isPresent()) {
                handler.reply("Failed to check balance. Reason: Target empty.");
                return;
            }

            // Get the targeted user.
            User target = optTarget.get();

            // If the targeted user is a bot, do not continue.
            if (target.isBot()) {
                handler.reply("That user is a bot.");
                return;
            }

            // If target has no data yet, register them.
            if (!econ.hasData(target)) {
                econ.register(target);
            }

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle(target.getName() + "'s balance")
                    .addField("Their balance is", econ.getFormattedBalance(target) + " Gold")
                    .setFooter("Their current position in the leaderboard is #" + econ.getTopPosition(target) + ".")
                    .setColor(Color.CYAN)
                    .setThumbnail(target.getAvatar());

            int topPos = econ.getTopPosition(target);

            if (topPos <= 10) {
                embed.setFooter("Their current position in the leaderboard is #" + econ.getTopPosition(target) + ".");
            } else {
                embed.setFooter("They are not currently on the leaderboard.");
            }

            handler.reply(embed);
        }

        else {
            handler.reply("Invalid command usage. Type ``e!help balance`` for command information.");
        }
    }
}