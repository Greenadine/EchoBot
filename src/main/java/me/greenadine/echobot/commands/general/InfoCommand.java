package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class InfoCommand implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("info")) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("About me")
                    .setDescription("Hi, I'm EchoBot! I'm a bot based on the character from Shirokoi's visual novel, Repeat. I was created by the lovely staff team of this server!")
                    .setColor(Color.CYAN)
                    .addInlineField("Current bot version", EchoBot.versionID)
                    .addInlineField("Created with", "[Javacord](https://javacord.org/) Discord wrapper for Java")
                    .addInlineField("Source code", "[GitHub repository](https://github.com/Greenadine/EchoBot)")
                    .addField("Developer", "Greenadine (Kevin)")
                    .addField("Testers/Ideas", "Snave Lane (Evans)\nI want die (Brayden)\nGarominho\nValerie\naleksthetenth")
                    .addField("Last update", EchoBot.lastUpdate);

            handler.reply(embed);
        }
    }
}
