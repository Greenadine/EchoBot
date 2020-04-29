package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.handlers.shop.Inventories;
import me.greenadine.echobot.handlers.shop.Item;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.Map;
import java.util.StringJoiner;

public class InventoryCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "inventory";
    }

    public String getDescription() {
        return "View your item inventory.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!inventory";
    }

    public String getArguments() {
        return null;
    }

    public String getAliases() { return "inv"; }

    private Inventories inventories = EchoBot.inventories;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (!handler.getUser().isPresent()) {
            handler.reply("Failed to execute command. Reason: User empty.");
            return;
        }

        if (!handler.getUser().isPresent()) {
            handler.reply("Failed to execute command. Reason: User empty.");
            return;
        }

        User user = handler.getUser().get();

        if (handler.length() == 0) {
            if (!inventories.hasData(user)) {
                inventories.register(user);
            }

            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(Color.cyan)
                    .setThumbnail(user.getAvatar())
                    .setTitle("Your inventory");

            for (Item item : Item.values()) {
                if (!item.isInventory()) { continue; }

                inventories.getInventory(user).putIfAbsent(item, 0);

                if (item.getName().contains("Card")) {
                    embed.addField( item.getName() + ": " + inventories.getInventory(user).get(item), "Open it with ``e!open " + item.getName() + "``.");
                } else {
                    embed.addField(item.getName() + ": " + inventories.getInventory(user).get(item), item.getDescription());
                }
            }

            handler.reply(embed);

            return;
        }

        else if (handler.length() == 1) {

        }

        else {
            handler.reply("Invalid command usage. Type ``e!help inventory`` for command information.");
        }
    }

}
