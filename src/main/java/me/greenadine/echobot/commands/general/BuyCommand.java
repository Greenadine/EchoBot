package me.greenadine.echobot.commands.general;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.shop.Inventories;
import me.greenadine.echobot.handlers.shop.Shop;
import me.greenadine.echobot.handlers.shop.Item;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Optional;

public class BuyCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "buy";
    }

    public String getDescription() {
        return "Buy items from the shop. View ``e!shop`` for things to buy.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!buy <item>";
    }

    public String getArguments() {
        return "item - The item that you want to buy.";
    }

    public String getAliases() { return null; }

    private Economy econ = EchoBot.econ;
    private Shop shop = EchoBot.shop;
    private Inventories inventories = EchoBot.inventories;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (!handler.getUser().isPresent()) {
            handler.reply("Failed to execute command. Reason: User empty.");
            return;
        }

        if (handler.length() == 0) {
            handler.reply("Please specify which item to buy. View ``e!shop`` for a list of things to buy.");
            return;
        }

        Optional<Item> optItem = shop.getItem(handler.getArguments());

        if (!optItem.isPresent()) {
            handler.reply("Could not find item with name '" + handler.getArguments() + "'.");
            return;
        }

        User user = handler.getUser().get();

        Item item = optItem.get();

        if (!econ.hasBalance(user, item.getPrice())) {
            handler.reply("You do not have enough Gold to buy this item. Price: " + String.format("%,d", item.getPrice()) + " Gold.");
            return;
        }

        boolean withdraw = econ.withdraw(user, item.getPrice());

        if (!withdraw) {
            handler.reply("An internal error occurred while trying to complete transaction.");
            return;
        }

        boolean addItem = inventories.addItem(user, item);

        if (addItem) {
            if (item.isInventory()) {
                handler.reply("Transaction complete. Added item to your inventory.");
            } else {
                if (item == Item.KEVINS_LEWD_PICTURES) {
                    handler.reply("*Oh, someone has been saving up I see... Well, they are on the way, my guy.*");

                    EchoBot.bot.getUserById(173051548635627520L).join().openPrivateChannel().thenAcceptAsync(channel -> {
                        channel.sendMessage(user.getNicknameMentionTag() + " (" + user.getDiscriminatedName() + ") has bought your lewd pictures. *Better get to sending them ASAP...*");
                    });
                }

                else {
                    handler.reply("Transaction complete. Item bought.");
                }
            }

        } else {
            handler.reply("An internal error has occurred while trying to add item to inventory.");
        }
    }
}
