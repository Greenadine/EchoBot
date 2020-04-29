package me.greenadine.echobot.commands.fun;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.valentine.RepeatCharacter;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class ValentineCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "valentine";
    }

    public String getDescription() {
        return "Get your Valentine's!";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!valentine";
    }

    public String getArguments() {
        return null;
    }

    public String getAliases() { return null; }

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (!EchoBot.settings.isValentineEnabled()) {
            handler.reply("The Valentine's feature is currently disabled. Wait until next Valentine's Day!");
            return;
        }

        if (handler.length() != 0) {
            handler.reply("Invalid command usage. Usage: ``e!valentine``.");
            return;
        }

        if (handler.getUser().isPresent()) {
            User user = handler.getUser().get();

            if (EchoBot.valentine.hasValentine(user)) {
                RepeatCharacter val = EchoBot.valentine.getValentine(user);

                EmbedBuilder embed = new EmbedBuilder()
                        .setAuthor(user)
                        .setColor(val.getColor())
                        .setTitle("Your valentine is " + val.getName())
                        .setDescription(val.getBio())
                        .addInlineField("Age", val.getAge())
                        .addInlineField("Species", val.getSpecies());

                if (val.getImage() != null) {
                    embed.setThumbnail(val.getImage());
                }

                handler.reply(embed);
            }

            else {
                RepeatCharacter val = EchoBot.valentine.giveValentine(user);

                EmbedBuilder embed = new EmbedBuilder()
                        .setAuthor(user)
                        .setColor(val.getColor())
                        .setTitle("Your valentine is " + val.getName())
                        .setDescription(val.getBio())
                        .addInlineField("Age", val.getAge())
                        .addInlineField("Species", val.getSpecies());

                if (val.getImage() != null) {
                    embed.setThumbnail(val.getImage());
                }

                handler.reply(embed);
            }
        }
    }
}