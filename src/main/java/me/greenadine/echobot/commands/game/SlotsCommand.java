package me.greenadine.echobot.commands.game;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.CommandHandler;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.SlotMachine;
import me.greenadine.echobot.objects.SlotCombo;
import me.greenadine.echobot.objects.SlotEntry;
import me.greenadine.echobot.objects.SlotsResult;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.util.StringJoiner;

public class SlotsCommand implements MessageCreateListener {

    private SlotMachine slots = EchoBot.slots;
    private Economy econ = EchoBot.econ;

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        CommandHandler handler = new CommandHandler(e);

        if (handler.isCommand("slots")) {
            User user = handler.getUser();

            if (user == null || user.isBot()) {
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
                        slot4.getEmoji() + " - " + slot4.getPoints() + " Points\n" +
                        "Total: " + result.getSlotPoints() + " Points.");

                if (result.hasCombos()) {
                    StringJoiner joiner = new StringJoiner("\n");

                    for (SlotCombo combo : result.getCombos()) {
                        joiner.add(combo.getName() + " - x" + combo.getMultiplier() + " Multiplier");
                    }

                    embed.addInlineField("Combos", joiner.toString() + "\nMultiplier: x" + result.getMultiplier());
                } else {
                    embed.addInlineField("Combos", "None\nMultiplier: x" + result.getMultiplier());
                }

                embed.addInlineField("Total Points", String.valueOf(result.getPoints()));

                embed.addField("You have been rewarded", result.getReward() + " Gold");

                handler.reply(embed);
                econ.add(user, result.getReward());
            }

            else {
                handler.reply("Invalid command usage. Type ``e!help slots`` for command information.");
            }
        }
    }
}
