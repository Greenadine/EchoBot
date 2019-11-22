package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.Levels;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class TopRankCommand implements MessageCreateListener {

    private Levels lvl = EchoBot.lvl;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("top-rank")) {
            if (handler.length() != 0) {
                handler.reply("Invalid command usage. Type ``e!help top-rank`` for command information.");
                return;
            }

            List<Map.Entry<Long, Integer>> top = lvl.getTop(10);

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Top " + top.size() + " Users - Level / XP")
                    .setColor(Color.CYAN);

            for (int i = 0; i < top.size(); i++) {
                Map.Entry<Long, Integer> entry = top.get(i);
                User user = EchoBot.bot.getCachedUserById(entry.getKey()).get();
                int xp = lvl.getXp(user);

                embed.addField("#" + (i + 1) + " - " + user.getName(), "Level " + lvl.calculateLevel(xp) + " (" + xp + " XP)");

                if (i == 0) {
                    embed.setThumbnail(user.getAvatar());
                }
            }

            handler.reply(embed);
        }
    }
}
