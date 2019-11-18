package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class PingCommand implements MessageCreateListener {

    private String prefix = EchoBot.prefix;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        Message message = e.getMessage();
        String[] args = message.getContent().split("\\s+");

        if (args[0].equalsIgnoreCase(prefix + "ping")) {
            long start = System.currentTimeMillis();
            Message test = message.getChannel().sendMessage("Testing latency...").join();
            test.edit("My latency is " + (System.currentTimeMillis() - start) + "ms.");
        }
    }
}
