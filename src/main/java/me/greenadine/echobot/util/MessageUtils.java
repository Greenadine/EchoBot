package me.greenadine.echobot.util;

import org.javacord.api.entity.message.Message;

public class MessageUtils {

    public static String getLink(Message message) {
        String link = "https://discordapp.com/channels/";

        if (message.getServer().isPresent()) {
            link += message.getServer().get().getId() + "/" + message.getChannel().getId() + "/" + message.getId();

            return link;
        }

        return null;
    }
}
