package me.greenadine.echobot.commands.fun;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.Phrases;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class FutureCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "future";
    }

    public String getDescription() {
        return "Let Echo tell you your future.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!future";
    }

    public String getArguments() {
        return null;
    }

    public String getAliases() { return null; }

    private Phrases phrases = EchoBot.future;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (handler.length() != 0) {
            handler.reply("Invalid command usage. Type ``e!help future`` for command information.");
            return;
        }

        if (!handler.getUser().isPresent()) {
            handler.reply("Failed to execute command. Reason: User empty.");
            return;
        }

        if (handler.getUser().isPresent()) {
            User user = handler.getUser().get();

            if (phrases.getSize() == 0) {
                handler.reply("I'm currently unable to tell you your future.");
            } else {
                handler.reply(phrases.getRandom(user));
            }
        } else {
            handler.reply("Failed to execute command. Reason: User empty.");
        }
    }
}