package me.greenadine.echobot.commands.moderation;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.commands.EchobotCommand;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.StringJoiner;

public class ChangelogCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "changelog";
    }

    public String getDescription() {
        return "See the changelog of the latest update of the bot.";
    }

    public String getDetails() {
        return null;
    }

    public String getUsage() {
        return "e!changelog";
    }

    public String getArguments() {
        return null;
    }

    public String getAliases() { return null; }

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (handler.length() != 0) {
            handler.reply("Invalid command usage. Type ``e!help changelog`` for command information.");
            return;
        }

        EmbedBuilder embed = new EmbedBuilder().setTitle("Changelog of v" + EchoBot.versionID)
                .setThumbnail(EchoBot.bot.getYourself().getAvatar())
                .setColor(Color.cyan);

        StringJoiner additions = new StringJoiner("\n");
        StringJoiner changes = new StringJoiner("\n");
        StringJoiner other = new StringJoiner("\n");

        // Additions
        additions.add("- Commands can now have aliases.");
        additions.add("- ``e!help <command>`` now also works with aliases.");
        additions.add("- Added the shop. View and buy items with ``e!shop``.");
        additions.add("- Added ``e!buy`` command. Use this to buy items from the shop.");
        additions.add("- Added inventories. View your inventory with ``e!inventory``.");
        additions.add("- Added polls function. Create a poll with ``e!createpoll``.");
        additions.add("- Added ``e!god`` command. This is for Kevin's use only :p.");

        // Changes & Fixes
        changes.add("- Fixed the ``e!lock`` feature not working properly.");
        changes.add("- Fixed Echo never picking the last option of phrase entries (e.g. from ``e!roast``, ``e!future`` as well as reply actions).");
        changes.add("- Gold amounts are now shown with a thousands separator (e.g. 1.000.000 Gold).");
        changes.add("- Replaced ``e!top econ`` with ``e!top balance");
        changes.add("- Replaced ``e!trivia-repeat`` command with ``e!trivia``.");
        changes.add("- Added alias ``e!bal`` to command ``e!balance``.");
        changes.add("- Renamed ``e!stats`` command to ``e!statistics``.");
        changes.add("- Added alias ``e!stats`` to command ``e!statistics``");

        // Other
        // other.add("");

        // Send changelog
        if (additions.length() > 0) { embed.addField("Additions", additions.toString()); }
        if (changes.length() > 0) { embed.addField("Changes/Fixes", changes.toString()); }
        if (other.length() > 0) { embed.addField("Other", other.toString()); }
        if (additions.length() == 0 && changes.length() == 0 && other.length() == 0) { embed.setDescription("No changelog found."); }

        handler.reply(embed);
    }
}
