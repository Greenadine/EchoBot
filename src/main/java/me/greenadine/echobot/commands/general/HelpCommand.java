package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class HelpCommand implements MessageCreateListener {

    private DiscordApi bot = EchoBot.bot;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("help")) {
            if (handler.length() == 0) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("EchoBot Help")
                        .setDescription("Choose a category of commands.")
                        .addField("e!help general", "- Shows a list of general commands.")
                        .addField("e!help economy", "- Shows a list of economy commands.")
                        .addField("e!help fun", "- Shows a list of fun commands.")
                        .addField("e!help game", "- Shows a list of game commands.")
                        .addField("e!help moderation", "- Shows a list of moderator commands.")
                        .setColor(Color.CYAN)
                        .setThumbnail(bot.getYourself().getAvatar());

                handler.reply(embed);

                return;
            }

            else if (handler.length() == 1) {
                // e!help general
                if (handler.getArg(0).equalsIgnoreCase("general")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - General")
                            .setDescription("Note: <> = required, [] = optional.")
                            .setFooter("Tip: Use 'e!help (command)' for more information on a command.")
                            .addField("e!ping", "- Test bot latency.")
                            .addField("e!rank [user]", "- Check your or someone else's level and leaderboard position.")
                            .addField("e!top-rank", "- Check the levels leaderboard.")
                            .addField("e!warnings", "- Check your received warnings.")
                            .addField("e!notifications", "- Check or manage your notification settings.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help economy
                else if (handler.getArg(0).equalsIgnoreCase("economy")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Economy")
                            .setDescription("Note: <> = required, [] = optional.")
                            .setFooter("Tip: Use 'e!help (command)' for more information on a command.")
                            .addField("e!balance [user]", "- Check your or someone else's Gold balance.")
                            .addField("e!pay <user> <amount>", "- Give Gold to another user.")
                            .addField("e!top-econ", "- Check the Gold leaderboard.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help fun
                else if (handler.getArg(0).equalsIgnoreCase("fun")){
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Fun")
                            .setDescription("Note: <> = required, [] = optional.")
                            .setFooter("Tip: Use 'e!help (command)' for more information on a command.")
                            .addField("e!poke", "- Poke Echo.")
                            .addField("e!wish <wish>", "- Let Echo grant you a wish. Or not...")
                            .addField("e!future", "- Let Echo tell you your future.")
                            .addField("e!roast <user>", "- Let Echo RoastCommand someone for you.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help game
                else if (handler.getArg(0).equalsIgnoreCase( "game")){
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Game")
                            .setDescription("Note: <> = required, [] = optional.")
                            .setFooter("Tip: Use 'e!help (command)' for more information on a command.")
                            .addField("e!coinflip", "- Flip a coin.")
                            .addField("e!trivia", "- Play a game of trivia.")
                            .addField("e!trivia-repeat", "- Play a game of Repeat trivia.")
                            .addField("e!lottery", "- Enter the daily lottery.")
                            .addField("e!slots <bet>", "- Crank that slot machine.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help game
                else if (handler.getArg(0).equalsIgnoreCase( "moderation")){
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Moderation")
                            .setDescription("Note: <> = required, [] = optional.")
                            .setFooter("Tip: Use 'e!help (command)' for more information on a command.")
                            .addField("e!mute <user> <duration>", "- Mute a user for the duration.")
                            .addField("e!unmute <user>", "- Unmute a user.")
                            .addField("e!warning-list <user>", "- Shows the warnings a user has received.")
                            .addField("e!warning-give <user> <reason>", "- Give a user a warning.")
                            .addField("e!warning-remove <user> <index>", "- Remove a warning from a user.")
                            .addField("e!warning-clear <user>", "- Clears all warnings of a user.")
                            .addField("e!econ-set <user> <amount>", "- Set a user's Gold balance.")
                            .addField("e!econ-add <user> <amount>", "- Add Gold to a user's balance.")
                            .addField("e!econ-withdraw <user> <amount>", "- Withdraw Gold to a user's balance.")
                            .addField("e!econ-clear <user>", "- Clear a user's balance.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help ping
                else if (handler.getArg(0).equalsIgnoreCase("ping")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Check EchoBot's latency.")
                            .addField("Usage:", "e!ping")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help rank
                else if (handler.getArg(0).equalsIgnoreCase("rank")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Check your or someone else's level, and your or their current position on the levels leaderboard.")
                            .addField("Usage:", "e!rank [user]")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help top-rank
                else if (handler.getArg(0).equalsIgnoreCase("top-rank")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Check the levels leaderboard.")
                            .addField("Usage:", "e!top-rank")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help warnings
                else if (handler.getArg(0).equalsIgnoreCase("warnings")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Check the warnings you've received in the server so far.")
                            .addField("Usage:", "e!warnings")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help notifications
                else if (handler.getArg(0).equalsIgnoreCase("notifications")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Check or manage your notification settings.")
                            .addField("Notification types:", "``repeat``: Get a notification when a new Repeat build is available for everyone.\n " +
                                    "``repeat-patron``: Get a notification when a new Repeat build is initially released, and only available for patrons. \n" +
                                    "``patreon``: Get a notification when Shirokoi publishes a new post on his Patreon.\n" +
                                    "``all``: Opt-in or out of all notifications.")
                            .addField("Usage:", "e!notifications <type> <on:off|toggle>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help balance
                else if (handler.getArg(0).equalsIgnoreCase("balance")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Check how much Gold you or someone else currently has.")
                            .addField("Usage:", "e!balance [user]")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help pay
                else if (handler.getArg(0).equalsIgnoreCase("pay")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Give another user Gold.")
                            .addField("Usage:", "e!pay <user> <amount>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help top-econ
                else if (handler.getArg(0).equalsIgnoreCase("top-econ")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Check the Gold leaderboard.")
                            .addField("Usage:", "e!top-econ")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help poke
                else if (handler.getArg(0).equalsIgnoreCase("poke")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Poke Echo.")
                            .addField("Usage:", "e!poke")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help wish
                else if (handler.getArg(0).equalsIgnoreCase("wish")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Let Echo gran you a wish. Or not...")
                            .addField("Usage:", "e!wish <wish>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help future
                else if (handler.getArg(0).equalsIgnoreCase("future")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Let Echo tell you your future.")
                            .addField("Usage:", "e!future")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help roast
                else if (handler.getArg(0).equalsIgnoreCase("roast")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Let Echo roast someone for you.")
                            .addField("Usage:", "e!roast <user>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help trivia
                else if (handler.getArg(0).equalsIgnoreCase("trivia")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Play a game of trivia.")
                            .addField("Usage:", "e!trivia")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help trivia-repeat
                else if (handler.getArg(0).equalsIgnoreCase("trivia-repeat")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Play a game of Repeat trivia.")
                            .addField("Usage:", "e!trivia-repeat")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help coinflip
                else if (handler.getArg(0).equalsIgnoreCase("coinflip")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Flip a coin.")
                            .addField("Usage:", "e!coinflip")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help lottery
                else if (handler.getArg(0).equalsIgnoreCase("lottery")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Buy a lottery ticket for 50 Gold. A winner will be chosen " +
                                    "every 24 hours, and will receive all the entry fees for that roll.")
                            .addField("Usage:", "e!lottery")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help slots
                else if (handler.getArg(0).equalsIgnoreCase("slots")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Play a game of slot machine. Getting one of 5 combinations " +
                                    "will grant you your bet with bonusses and multipliers depending on your combination.")
                            .addField("Usage:", "e!slots <bet>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help mute
                else if (handler.getArg(0).equalsIgnoreCase("mute")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Mute a user, preventing them from sending messages in all channels and from joining Voice Channels for the duration in minutes.")
                            .addField("Usage:", "e!mute <user> <duration>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help unmute
                else if (handler.getArg(0).equalsIgnoreCase("unmute")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Unmute a user..")
                            .addField("Usage:", "e!unmute <user>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help warning-list
                else if (handler.getArg(0).equalsIgnoreCase("warning-list")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "View the warnings a user has received so far.")
                            .addField("Usage:", "e!warning-list <user>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help warning-give
                else if (handler.getArg(1).equalsIgnoreCase("warning-give")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Give a user a warning.")
                            .addField("Usage:", "e!warning-give <user> <reason>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help warning-remove
                else if (handler.getArg(1).equalsIgnoreCase("warning-remove")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Remove a warning from a user. Uses the index number of the " +
                                    "list shown in the 'warning-list' command.")
                            .addField("Usage:", "e!warning-remove <user> <index>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help warning-clear
                else if (handler.getArg(1).equalsIgnoreCase("warning-clear")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Clear all warnings of a user.")
                            .addField("Usage:", "e!warning-clear <user>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help econ-set
                else if (handler.getArg(1).equalsIgnoreCase("econ-set")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Set a user's balance.")
                            .addField("Usage:", "e!econ-set <user> <amount>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help econ-add
                else if (handler.getArg(1).equalsIgnoreCase("econ-set")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Add an amount of Gold to a user's balance.")
                            .addField("Usage:", "e!econ-add <user> <amount>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help econ-withdraw
                else if (handler.getArg(1).equalsIgnoreCase("econ-withdraw")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Withdraw an amount of Gold to a user's balance.")
                            .addField("Usage:", "e!econ-withdraw <user> <amount>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                // e!help econ-
                else if (handler.getArg(1).equalsIgnoreCase("econ-set")) {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("EchoBot Help - Command")
                            .setDescription(EchoBot.prefix + handler.getArg(0))
                            .addField("Description:", "Clear a user's balance, removing from data memory.")
                            .addField("Usage:", "e!econ-clear <user>")
                            .setFooter("Note: <> = required, [] = optional.")
                            .setColor(Color.CYAN)
                            .setThumbnail(bot.getYourself().getAvatar());

                    handler.reply(embed);
                    return;
                }

                else {
                    handler.reply("Unknown category or command '" + handler.getCommand() + "'. Use ``e!help`` for a list of categories and commands.");
                    return;
                }
            }

            handler.reply(handler.getUser().getNicknameMentionTag() + " Invalid command usage. Usage: ``e!help <category|command>``.");
            return;
        }
    }
}
