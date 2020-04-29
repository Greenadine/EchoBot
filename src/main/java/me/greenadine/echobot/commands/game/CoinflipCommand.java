package me.greenadine.echobot.commands.game;

import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.io.File;
import java.util.Random;

public class CoinflipCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "coinflip";
    }

    public String getDescription() {
        return "Flip a coin.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!coinflip";
    }

    public String getArguments() {
        return null;
    }

    public String getAliases() { return null; }

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (handler.length() != 0) {
            handler.reply("Invalid command usage. Type ``e!help coinflip`` for command information.");
            return;
        }

        boolean side = new Random().nextBoolean();

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.cyan);

        if (side) {
            embed.setTitle("Heads");
            embed.setImage(new File("assets/images/head.png"));
        } else {
            embed.setTitle("Tails");
            embed.setImage(new File("assets/images/tail.png"));
        }

        handler.reply(embed);
    }
}
