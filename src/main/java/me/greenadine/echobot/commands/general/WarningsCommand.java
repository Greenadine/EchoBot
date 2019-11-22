package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.Warnings;
import me.greenadine.echobot.objects.Warning;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class WarningsCommand implements MessageCreateListener {

    private Warnings warnings = EchoBot.warnings;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("warnings")) {
            User user = handler.getUser();

            if (!warnings.hasData(user)) {
                warnings.register(user);
            }

            if (warnings.getWarnings(user).size() == 0) {
                handler.reply("You have not received any warnings yet. Keep it up!");
                return;
            }

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Your warnings")
                    .setColor(Color.CYAN)
                    .setThumbnail(user.getAvatar());

            for (int i = 0; i < warnings.getWarnings(user).size(); i++) {
                Warning warning = warnings.getWarning(user, i);

                embed.addField("#" + (i + 1) + " - Issued by " + warning.getStaff().getName() + ".", warning.getDescription());
            }

            handler.reply(embed);
        }
    }
}
