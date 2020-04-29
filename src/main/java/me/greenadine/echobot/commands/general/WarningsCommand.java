package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.warning.WarningsHandler;
import me.greenadine.echobot.handlers.warning.Warning;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class WarningsCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "warnings";
    }

    public String getDescription() {
        return "Check the warnings you've received on the server so far.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!warnings";
    }

    public String getArguments() {
        return null;
    }

    public String getAliases() { return null; }

    private WarningsHandler warnings = EchoBot.warnings;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (handler.getUser().isPresent()) {
            User user = handler.getUser().get();

            if (!warnings.hasData(user)) {
                warnings.register(user);
            }

            if (warnings.getWarnings(user).size() == 0) {
                handler.reply("You have not received any warnings yet. Keep it up!");
                return;
            }

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Your warnings")
                    .setDescription("Total weighting: " + warnings.getTotalWarningWeight(user))
                    .setColor(Color.CYAN)
                    .setThumbnail(user.getAvatar());

            for (int i = 0; i < warnings.getWarnings(user).size(); i++) {
                Warning warning = warnings.getWarning(user, i);

                String title = "Issued by";

                if (warning.getStaff().isPresent()) {
                    title += " " + warning.getStaff().get().getName();
                } else {
                    title += " unknown";
                }

                if (warning.hasNotes()) {
                    embed.addField("#" + (i + 1) + " - " + title, "__Rule #" + warning.getRule().getNumber() + "__\n" + warning.getRule().getDescription() + "\n__Additional notes__\n" + warning.getNotes());
                } else {
                    embed.addField("#" + (i + 1) + " - " + title, "__Rule #" + warning.getRule().getNumber() + "__\n" + warning.getRule().getDescription());
                }
            }

            handler.reply(embed);
        } else {
            handler.reply("Failed to execute command. Reason: User empty.");
        }
    }
}
