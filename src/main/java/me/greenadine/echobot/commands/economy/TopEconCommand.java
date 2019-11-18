package me.greenadine.echobot.commands.economy;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.Economy;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.util.List;
import java.util.Map.Entry;

public class TopEconCommand implements MessageCreateListener {

    private Economy econ = EchoBot.econ;
    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("top-econ")) {
            if (handler.length() != 0) {
                handler.reply("Invalid command usage. Type ``e!help top-econ`` for command information.");
                return;
            }

            List<Entry<Long, Integer>> top = econ.getTop(10);

            EmbedBuilder embed = new EmbedBuilder().setTitle("Top " + top.size() + " Users - Gold").setColor(Color.CYAN)
                    .setThumbnail(EchoBot.bot.getYourself().getAvatar());

            for (int i = 0; i < top.size(); i++) {
                Entry<Long, Integer> entry = top.get(i);
                User user = EchoBot.bot.getCachedUserById(entry.getKey()).get();

                embed.addField("#" + (i + 1) + " - " + user.getName(), econ.getBalance(user) + " Gold");
            }

            handler.reply(embed);
        }
    }
}
