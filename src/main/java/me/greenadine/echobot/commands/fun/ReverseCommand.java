package me.greenadine.echobot.commands.fun;

import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class ReverseCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "reverse";
    }

    public String getDescription() {
        return "Reverse a message";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!reverse <message>";
    }

    public String getArguments() {
        return "``message`` - The message to reverse.";
    }

    public String getAliases() { return null; }

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (handler.length() == 0) {
            handler.reply("Please give me something to reverse.");
            return;
        }

        if (handler.getArguments().length() > 200) {
            handler.reply("That is way too long. I'm a *wish*, not your slave.");
            return;
        }

        if (handler.getUser().isPresent()) {
            StringBuilder reverse = new StringBuilder().append(handler.getArguments()).reverse();

            handler.reply(new EmbedBuilder()
                    .setColor(Color.cyan)
                    .setAuthor(handler.getUser().get())
                    .setDescription(reverse.toString()));

            e.deleteMessage();
        } else {
            handler.reply("Failed to reverse message. Reason: User empty.");
        }
    }
}
