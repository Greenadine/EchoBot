package me.greenadine.echobot.commands.economy;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.PermissionsHandler;
import me.greenadine.echobot.handlers.TagHandler;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class EconClearCommand implements MessageCreateListener {

    private Economy econ = EchoBot.econ;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("econ-clear")) {
            User user = handler.getUser();

            if (!PermissionsHandler.hasPermission(user, e.getMessage().getServer().get(), PermissionType.MANAGE_ROLES)) {
                handler.reply(user.getNicknameMentionTag() + " You do not have permission to clear someone's balance.");
                return;
            }

            if (handler.length() == 0) {
                handler.reply("Please specify a user to clear the balance from.");
                return;
            }

            else if (handler.length() == 1) {
                if (!TagHandler.isTag(handler.getArg(0))) {
                    handler.reply("Please tag a user like this: " + EchoBot.bot.getYourself().getNicknameMentionTag() + ".");
                    return;
                }

                User tagged = TagHandler.getUser(handler.getArg(0));

                if (tagged.isBot()) {
                    handler.reply("That user is a bot.");
                    return;
                }

                if (!econ.hasData(tagged)) {
                    handler.reply("This user does not have any Gold yet.");
                    return;
                }

                econ.clear(tagged);
                handler.reply("Cleared balance of " + tagged.getNicknameMentionTag() + ".");
                return;
            }

            else {
                handler.reply("Invalid command usage. Type ``e!help econ-reset`` for command information.");
                return;
            }

        }
    }
}
