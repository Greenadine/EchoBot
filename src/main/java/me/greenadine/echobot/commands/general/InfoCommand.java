package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class InfoCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "info";
    }

    public String getDescription() {
        return "OwO-ify a message.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!owo <message>";
    }

    public String getArguments() {
        return "``message`` - The message to OwO-ify.";
    }

    public String getAliases() { return null; }

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("About me")
                .setDescription("Hi, I'm EchoBot! I'm a bot based on the character from Shirokoi's visual novel, Repeat. I was created by the lovely staff team of this server!")
                .setColor(Color.CYAN)
                .addInlineField("Current bot version", EchoBot.versionID)
                .addInlineField("Created with", "[Javacord](https://javacord.org/) by Bastian Oppermann")
                .addInlineField("Source code", "[GitHub repository](https://github.com/Greenadine/EchoBot)")
                .addField("Developer", "Greenadine (Kevin)")
                .addField("Testers/Ideas", "Snave Lane (Evans)\nSome Dumb Ape (Brayden)\naleksthetenth (Aleks)\nGarominho\nValerie")
                .addField("Launch date", EchoBot.launchDate)
                .addField("Last update", EchoBot.lastUpdate)
                .addField("Changlelog", "See ``e!changelog``");

        handler.reply(embed);
    }
}
