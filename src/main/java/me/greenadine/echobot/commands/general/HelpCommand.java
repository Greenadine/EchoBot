package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.CommandCategory;
import me.greenadine.echobot.commands.Commands;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.Color;
import java.util.Optional;
import java.util.StringJoiner;

public class HelpCommand implements MessageCreateListener {

    private Commands commands = EchoBot.commands;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("help")) {
            if (handler.length() == 0) {
                EmbedBuilder embed = embed()
                        .setTitle("EchoBot Help")
                        .setDescription("Choose a category of commands.");

                for (CommandCategory category : CommandCategory.values()) {
                    embed.addField("e!help " + category.getName().toLowerCase(), "- " + category.getDescription());
                }

                handler.reply(embed);
            }

            else if (handler.length() == 1) {
                try {
                    CommandCategory category = CommandCategory.valueOf(handler.getArg(0).toUpperCase());

                    EmbedBuilder embed = embed()
                            .setTitle("EchoBot Help - " + category.getName())
                            .setDescription("Note: <> = required, [] = optional.")
                            .setFooter("Tip: Use ``e!help <command>`` for more information on a command.");

                    for (EchobotCommand command : commands.getCategoryCommands(category)) {
                        embed.addField(command.getUsage(), "- " + command.getDescription());
                    }

                    handler.reply(embed);
                } catch (IllegalArgumentException ex) {
                    Optional<EchobotCommand> optionalCommand = commands.getCommand(handler.getArg(0));

                    if (optionalCommand.isPresent()) {
                        EchobotCommand command = optionalCommand.get();

                        EmbedBuilder embed = embed();

                        if (command.getDetails() != null) {
                            embed.setTitle("EchoBot Help - Command")
                                    .setDescription(EchoBot.prefix + command.getName())
                                    .addField("Description", command.getDetails())
                                    .addField("Usage", command.getUsage());
                        } else {
                            embed.setTitle("EchoBot Help - Command")
                                    .setDescription(EchoBot.prefix + command.getName())
                                    .addField("Description", command.getDescription())
                                    .addField("Usage", command.getUsage());
                        }

                        if (command.getArguments() != null) {
                            embed.addField("Argument(s)", command.getArguments());
                        }

                        if (command.getAliases() != null) {
                            String aliases = command.getAliases().replaceAll(",", ", ");

                            embed.addField("Aliases", aliases);
                        }

                        handler.reply(embed);
                    } else {
                        handler.reply("Unknown category or command '" + handler.getArg(0) + "'. Use ``e!help`` for a list of categories and commands.");
                    }
                }
            } else {
                handler.reply("Invalid command usage. Usage: ``e!help <category|command>``.");
            }
        }
    }

    private EmbedBuilder embed() {
        return new EmbedBuilder()
                .setColor(Color.cyan)
                .setThumbnail(EchoBot.bot.getYourself().getAvatar());
    }
}
