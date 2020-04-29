package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.shop.Shop;
import me.greenadine.echobot.handlers.shop.ShopCategory;
import me.greenadine.echobot.handlers.shop.Item;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.io.File;

public class ShopCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "shop";
    }

    public String getDescription() {
        return "View the shop.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!shop <category>";
    }

    public String getArguments() {
        return "``category`` - The item category to show.";
    }

    public String getAliases() { return null; }

    private Economy econ = EchoBot.econ;
    private Shop shop = EchoBot.shop;

    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return;}

        if (!handler.getUser().isPresent()) {
            handler.reply("Failed to execute command. Reason: User empty.");
            return;
        }

        User user = handler.getUser().get();

        if (handler.length() == 0) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(Color.cyan)
                    .setAuthor("Balance: " + econ.getFormattedBalance(user) + " Gold", null, new File("assets/images/money_bag.png"))
                    .setTitle("")
                    .setDescription("See a specific page of the shop with ``e!shop <page>``.")
                    .setFooter("Earn Gold by being active on the server or by winning games!");

            for (ShopCategory category : ShopCategory.values()) {
                embed.addField("Page " + category.getId() + " |", category.getName());
            }

            handler.reply(embed);
        }

        else if (handler.length() == 1) {
            int page;

            try {
                page = Integer.valueOf(handler.getArg(0));
            } catch (NumberFormatException ex) {
                handler.reply("Please enter which page of the shop to view.");
                return;
            }

            ShopCategory category = ShopCategory.getFromId(page);

            if (category == null) {
                handler.reply("Page " + page + " does not exist.");
                return;
            }

            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(Color.cyan)
                    .setAuthor("Balance: " + econ.getFormattedBalance(user) + " Gold", null, new File("assets/images/money_bag.png"))
                    .setTitle(category.getName())
                    .setFooter("Earn Gold by being active on the server or by winning games!");

            for (Item item : shop.getItems(category)) {
                embed.addField(item.getName() + " | Cost: " + String.format("%,d", item.getPrice()) + " Gold", item.getDescription() + "\n``e!buy " + item.getName() + "``");
            }

            handler.reply(embed);
        }

        else {
            handler.reply("Invalid command usage. Type ``e!help shop`` for command information.");
        }
    }
}