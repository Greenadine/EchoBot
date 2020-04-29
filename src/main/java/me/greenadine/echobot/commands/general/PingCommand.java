package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
public class PingCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "ping";
    }

    public String getDescription() {
        return "Check latency.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!ping";
    }

    public String getArguments() {
        return null;
    }

    public String getAliases() { return null; }

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        long start = System.currentTimeMillis();

        e.getChannel().sendMessage("Testing latency...").thenAcceptAsync(message -> {
            message.edit("My latency is " + (System.currentTimeMillis() - start) + "ms");
        });
    }
}
