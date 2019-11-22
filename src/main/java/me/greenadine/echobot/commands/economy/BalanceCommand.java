package me.greenadine.echobot.commands.economy;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.handlers.TagHandler;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class BalanceCommand implements MessageCreateListener {

    private Economy econ = EchoBot.econ;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
         CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("balance")) {
            User user = handler.getUser();

            if (handler.length() == 0) {
                if (!econ.hasData(user)) {
                    econ.register(user);
                }

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle(user.getName() + "'s balance")
                        .addField("Your balance is", econ.getBalance(user) + " Gold")
                        .setFooter("Your current position in the leaderboard is #" + econ.getTopPosition(user) + ".")
                        .setColor(Color.CYAN)
                        .setThumbnail(user.getAvatar());

                handler.reply(embed);
            }

            else if (handler.length() == 1) {
                if (!PermissionsHandler.hasPermission(user, e.getMessage().getServer().get(), PermissionType.MANAGE_ROLES)) {
                    handler.reply(user.getNicknameMentionTag() + " You do not have permission to check other people's balances.");
                    return;
                }

                if (!TagHandler.isTag(handler.getArg(0))) {
                    handler.reply("Please tag a user like this: " + EchoBot.bot.getYourself().getNicknameMentionTag() + ".");
                    return;
                }

                User tagged = TagHandler.getUser(handler.getArg(0));

                if (tagged.isBot()) {
                    handler.reply("That user is a bot.");
                    return;
                }

                if (!econ.hasData(tagged)) {
                    econ.register(tagged);
                }

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle(tagged.getName() + "'s balance")
                        .addField("Their balance is", econ.getBalance(tagged) + " Gold")
                        .setFooter("Their current position in the leaderboard is #" + econ.getTopPosition(tagged) + ".")
                        .setColor(Color.CYAN)
                        .setThumbnail(tagged.getAvatar());

                handler.reply(embed);
                return;
            }

            else {
                handler.reply("Invalid command usage. Type ``e!help balance`` for command information.");
            }
        }
    }
}