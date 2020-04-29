package me.greenadine.echobot.listeners;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.*;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.util.logging.ExceptionLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

public class MessageCreateListener implements org.javacord.api.listener.message.MessageCreateListener {

    private static List<Long> cooldown_econ;
    private static List<Long> cooldown_lvl;

    private Economy econ = EchoBot.econ;
    private Levels lvl = EchoBot.lvl;
    private Settings settings = EchoBot.settings;

    public MessageCreateListener() {
        cooldown_econ = new ArrayList<>();
        cooldown_lvl = new ArrayList<>();
    }

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        MessageHandler handler = new MessageHandler(e);
        User user = handler.getUser();

        if (user == null) {
            return;
        }

        if (!PermissionsHandler.isModerator(user)) {
            StringJoiner joiner = new StringJoiner(", ");

            for (String word : settings.getBannedWords()) {
                if (handler.containsIgnoreCase(word)) {
                    joiner.add("||" + word + "||");
                }
            }

            if (joiner.length() > 0) {
                e.deleteMessage();
                handler.reply(user.getNicknameMentionTag() + "Please refrain from using such language. You have received a warning. If you feel like this warning is unjustified, contact a Staff member.");

                EchoBot.warnings.addWarning(user, EchoBot.bot.getYourself(), "Usage of word(s) in the banned words list", "Word(s) found: " + joiner.toString());
            }
        }

        if (user.isBot()) {
            return;
        }

        // If user is off cooldown for receiving Gold.
        if (!cooldown_econ.contains(handler.getUser().getId())) {
            if (!econ.hasData(user)){
                econ.register(user);
            }

            econ.add(user, 20); // Add 20 Gold.
            cooldown_econ.add(user.getId()); // Put user on cooldown.

            Thread thread = new Thread(new MessageCreateListener.schedule("econ", 300000, user.getId()));
            thread.start(); // Schedule remove off cooldown after 300000 milliseconds (5 minutes).
        }


        // If user is off cooldown for receiving XP.
        if (!cooldown_lvl.contains(handler.getUser().getId())) {
            if (!lvl.hasData(user)){
                lvl.register(user);
            }

            int lvl_begin = lvl.calculateLevel(lvl.getXp(user));

            lvl.add(user, 5); // Add 5 XP.
            cooldown_lvl.add(user.getId()); // Put user on cooldown.

            Thread thread = new Thread(new MessageCreateListener.schedule("lvl", 60000, user.getId()));
            thread.start(); // Schedule remove off cooldown after 60000 milliseconds (1 minute).

            int lvl_end = lvl.calculateLevel(lvl.getXp(user));

            if (lvl_begin < lvl_end) {
                handler.reply("Congratulations " + user.getMentionTag() + "! You've leveled up to Level " + lvl_end + "!");
            }
        }
    }

    class schedule implements Runnable {

        private String type;
        private int delayMillis;
        private long id;

        private schedule(String t, int d, long i) {
            type = t;
            delayMillis = d;
            id = i;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(delayMillis);
            } catch(InterruptedException ex) {
                System.out.println("Error occurred while trying to sleep thread in MessageCreateListener.schedule. Reason: " + ex.getMessage());
                return;
            }

            if (type == "econ") {
                MessageCreateListener.cooldown_econ.remove(id);
            }

            if (type == "lvl") {
                MessageCreateListener.cooldown_lvl.remove(id);
            }
        }

    }

}