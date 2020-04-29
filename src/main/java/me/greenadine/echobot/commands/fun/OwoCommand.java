package me.greenadine.echobot.commands.fun;

import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.Color;

public class OwoCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "owo";
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

        if (handler.length() == 0) {
            handler.reply("Pwease give me something to owo-ify. <:owo:674192498670829569>");
            return;
        }

        if (!handler.getUser().isPresent()) {
            handler.reply("Failed to execute command. Reason: User empty.");
            return;
        }

        String owo = handler.getArguments()
                .replaceAll("r", "w")
                .replaceAll("l", "w")
                .replaceAll("R", "W")
                .replaceAll("L", "W");

        e.deleteMessage();

        User user = handler.getUser().get();

        handler.reply(new EmbedBuilder()
                .setAuthor(user)
                .setDescription(owo + " <:owo:674192498670829569>")
                .setColor(Color.CYAN));
    }
}