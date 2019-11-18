package me.greenadine.echobot.commands.fun;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.TagHandler;
import me.greenadine.echobot.handlers.Phrases;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class RoastCommand implements MessageCreateListener {

    private Phrases roast = EchoBot.roast;
    private Phrases roast_self = EchoBot.roast_self;
    private Phrases roast_echo = EchoBot.roast_echo;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("roast")) {
            if (handler.length() == 0) {
                handler.reply("Give me someone to roast. Or do you want me to roast you?");
                return;
            }

            if (handler.length() == 1) {
                if (TagHandler.isTag(handler.getArg(0))) {
                    User author = handler.getUser();
                    User tagged = TagHandler.getUser(handler.getArg(0));

                    if (author.getId() == tagged.getId()) {
                        handler.reply(roast_self.getRandom().replaceAll("%mention%", tagged.getNicknameMentionTag()));
                        return;
                    }

                    if (TagHandler.isUserTagged(handler.getArg(0), 642192249563906050L)) {
                        handler.reply(roast_echo.getRandom().replaceAll("%mention%", tagged.getNicknameMentionTag()));
                        return;
                    }

                    if (tagged != null) {
                        handler.reply(roast.getRandom().replaceAll("%mention%", tagged.getNicknameMentionTag()));
                        return;
                    } else {
                        throw new NullPointerException("Failed to send message from command 'e!roast': tagged user null.");
                    }
                } else {
                    handler.reply("That is not a person.");
                }
            }

            else {
                handler.reply("Invalid command usage. Type ``e!help roast`` for command information.");
                return;
            }
        }
    }
}