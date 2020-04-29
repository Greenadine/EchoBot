package me.greenadine.echobot.commands.game;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.commands.EchobotCommand;
import me.greenadine.echobot.commands.CommandHandler;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.slots.SlotMachine;
import me.greenadine.echobot.handlers.slots.SlotCombo;
import me.greenadine.echobot.handlers.slots.SlotEntry;
import me.greenadine.echobot.handlers.slots.SlotsResult;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.StringJoiner;

public class SlotsCommand implements EchobotCommand {

    // Command info
    public String getName() {
        return "slots";
    }

    public String getDescription() {
        return "Play a game of slotmachine.";
    }

    public String getDetails() { return null; }

    public String getUsage() {
        return "e!slots <bet>";
    }

    public String getArguments() {
        return "``bet`` - The amount of Gold to bet. Has to be at least 500 Gold.";
    }

    public String getAliases() { return null; }

    private SlotMachine slots = EchoBot.slots;
    private Economy econ = EchoBot.econ;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(this, e);

        if (!handler.isCommand()) { return; }

        if (handler.getUser().isPresent()) {
            User user = handler.getUser().get();

            if (user.isBot()) {
                return;
            }

            if (handler.length() == 0) {
                handler.reply("Please enter a bet to play with.");
            }

            else if (handler.length() == 1) {
                int bet;

                try {
                    bet = Integer.valueOf(handler.getArg(0));
                } catch (NumberFormatException ex) {
                    handler.reply("The bet to play with has to be a number.");
                    return;
                }

                if (bet < 500) {
                    handler.reply("You have to bet at least 500 Gold to play.");
                    return;
                }

                if (!econ.hasBalance(user, bet)) {
                    handler.reply("You do not have enough balance for that.");
                    return;
                }

                econ.withdraw(user, bet);

                SlotsResult result = slots.roll(bet);

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Slotmachine")
                        .setDescription(result.formatSlots())
                        .setColor(Color.CYAN)
                        .setThumbnail(user.getAvatar());

                SlotEntry slot1 = result.getSlot(1);
                SlotEntry slot2 = result.getSlot(2);
                SlotEntry slot3 = result.getSlot(3);
                SlotEntry slot4 = result.getSlot(4);

                embed.addInlineField("Result",
                        slot1.getEmoji() + " - " + slot1.getPoints() + " Points\n" +
                                slot2.getEmoji() + " - " + slot2.getPoints() + " Points\n" +
                                slot3.getEmoji() + " - " + slot3.getPoints() + " Points\n" +
                                slot4.getEmoji() + " - " + slot4.getPoints() + " Points\n");

                embed.addInlineField("Total Points", String.valueOf(result.getPoints()));

                if (result.hasCombos()) {
                    StringJoiner joiner = new StringJoiner("\n");

                    for (SlotCombo combo : result.getCombos()) {
                        joiner.add(combo.getName() + " - x" + combo.getMultiplier() + "");
                    }

                    if (result.getCombos().size() < 2) {
                        embed.addInlineField("Combos", joiner.toString() + "\nMultiplier: x" + result.getMultiplier());
                    } else {
                        embed.addInlineField("Combos", joiner.toString() + "\nMultiplier: x" + result.getMultiplier() + "\nCombo Breaker Bonus!");
                    }
                } else {
                    embed.addInlineField("Combos", "None\nMultiplier: x" + result.getMultiplier());
                }

                if (bet > result.getReward()) { // Loss
                    embed.addField("Lol, you're stupid! You've lost", String.format("%,d", bet - result.getReward()) + " Gold");
                }
                else if (bet == result.getReward()) { // Draw
                    embed.addField("Lol, you got back the same amount", String.format("%,d", bet - result.getReward()) + " Gold");
                }
                else { // Win
                    embed.addField("Wow, you actually managed to make money? Won't happen again though. You've won", String.format("%,d", result.getReward() - bet) + " Gold");
                }

                handler.reply(embed);
                econ.add(user, result.getReward());
            }

            else {
                handler.reply("Invalid command usage. Type ``e!help slots`` for command information.");
            }
        } else {
            handler.reply("Failed to execute command. Reason: User empty.");
        }
    }
}
