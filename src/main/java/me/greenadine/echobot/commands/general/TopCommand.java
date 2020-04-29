package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.Levels;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TopCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "top";
    }

    public String getDescription() {
        return "Check the rank or economy leaderboard.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!top <rank|balance>";
    }

    public String getArguments() {
        return null;
    }

    public String getAliases() { return null; }

    private Levels lvl = EchoBot.lvl;
    private Economy econ = EchoBot.econ;
    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (handler.length() != 1) {
            handler.reply("Invalid command usage. Type ``e!help top`` for command information.");
            return;
        }

        if (handler.getArg(0).equalsIgnoreCase("levels") || handler.getArg(0).equalsIgnoreCase("rank")) {
            List<Map.Entry<Long, Integer>> top = lvl.getTop(10);

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Top " + top.size() + " Users - Level / XP")
                    .setColor(Color.CYAN);

            for (int i = 0; i < top.size(); i++) {
                Map.Entry<Long, Integer> entry = top.get(i);

                if (EchoBot.bot.getCachedUserById(entry.getKey()).isPresent()) {
                    User user = EchoBot.bot.getCachedUserById(entry.getKey()).get();

                    int xp = lvl.getXp(user);
                    embed.addField("#" + (i + 1) + " - " + getName(user), "Level " + lvl.calculateLevel(xp) + " (" + xp + " XP)");

                    if (i == 0) {
                        embed.setThumbnail(user.getAvatar());
                    }
                } else {
                    embed.addField("#" + (i + 1) + " - Not found", "-");
                }
            }

            handler.reply(embed);
        }

        if (handler.getArg(0).equalsIgnoreCase("balance") || handler.getArg(0).equalsIgnoreCase("bal")) {
            List<Entry<Long, Integer>> top = econ.getTop(10);

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Top " + top.size() + " Users - Gold")
                    .setColor(Color.CYAN);

            for (int i = 0; i < top.size(); i++) {
                Entry<Long, Integer> entry = top.get(i);

                if (EchoBot.bot.getCachedUserById(entry.getKey()).isPresent()) {
                    User user = EchoBot.bot.getCachedUserById(entry.getKey()).get();

                    embed.addField("#" + (i + 1) + " - " + getName(user), String.format("%,d", econ.getBalance(user)) + " Gold");

                    if (i == 0) {
                        embed.setThumbnail(user.getAvatar());
                    }
                } else {
                    embed.addField("#" + (i + 1) + " - Not found", "-");
                }
            }

            handler.reply(embed);
        }
    }

    private String getName(User user) {
        if (EchoBot.bot.getServerById(EchoBot.serverId).isPresent()) {
            Server server = EchoBot.bot.getServerById(EchoBot.serverId).get();

            if (user.getNickname(server).isPresent()) {
                return user.getNickname(server).get();
            } else {
                return user.getName();
            }
        } else {
            return user.getName();
        }
    }
}
