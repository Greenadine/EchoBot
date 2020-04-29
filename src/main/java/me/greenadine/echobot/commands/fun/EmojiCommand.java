package me.greenadine.echobot.commands.fun;

import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.util.EmojiUtils;
import org.javacord.api.entity.emoji.KnownCustomEmoji;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.io.InputStream;
import java.util.Optional;

public class EmojiCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "emoji";
    }

    public String getDescription() {
        return "Enlarge a custom emoji.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!emoji <emoji>";
    }

    public String getArguments() {
        return "``emoji`` - The emoji to enlarge.";
    }

    public String getAliases() { return null; }

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) {return;}

        if (handler.length() == 0 || !EmojiUtils.isCustomEmoji(handler.getArg(0))) {
            handler.reply("Please give a custom emoji to enlarge (NOTE: only custom emoji from this server work).");
            return;
        }

        if (handler.length() != 1) {
            handler.reply("Invalid command usage. Type ``e!help emoji`` for command information.");
            return;
        }

        Optional<KnownCustomEmoji> optEmoji = EmojiUtils.getCustomEmoji(handler.getArg(0));

        if (!optEmoji.isPresent()) {
            handler.reply("Failed to execute command. Reason: Emoji empty.");
            return;
        }

        if (optEmoji.isPresent()) {
            KnownCustomEmoji emoji = optEmoji.get();

            if (handler.getUser().isPresent()) {
                StringBuilder reverse = new StringBuilder().append(handler.getArguments()).reverse();

                InputStream largeEmoji = emoji.getImage().asInputStream().join();

                if (emoji.isAnimated()) {
                    handler.reply(new EmbedBuilder().setColor(Color.cyan).setAuthor(handler.getUser().get()));
                } else {
                    handler.reply(emoji.getImage().asInputStream().join(), "emoji.png");
                }

                handler.reply(new EmbedBuilder()
                        .setColor(Color.cyan)
                        .setAuthor(handler.getUser().get())
                        .setDescription(reverse.toString()));

                e.deleteMessage();
            } else {
                handler.reply("Failed to reverse message. Reason: User empty.");
                return;
            }



            e.deleteMessage();
        } else {
            handler.reply("Failed to enlarge custom emoji. Reason: Optional empty.");
        }
    }
}