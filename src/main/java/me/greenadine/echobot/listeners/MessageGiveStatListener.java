package me.greenadine.echobot.listeners;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.Levels;
import me.greenadine.echobot.handlers.MessageHandler;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;
import java.util.List;

public class MessageGiveStatListener implements MessageCreateListener {

    public static List<Long> cooldown_econ;
    public static List<Long> cooldown_lvl;

    private Economy econ = EchoBot.econ;
    private Levels lvl = EchoBot.lvl;

    public MessageGiveStatListener() {
        cooldown_econ = new ArrayList<>();
        cooldown_lvl = new ArrayList<>();
    }

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        MessageHandler handler = new MessageHandler(e);
        User user = handler.getUser();

        if (user == null || user.isBot()) {
            return;
        }

        // If user is off cooldown for receiving Gold.
        if (!cooldown_econ.contains(handler.getUser().getId())) {
            if (!econ.hasData(user)){
                econ.register(user);
            }

            econ.add(user, 20); // Add 20 Gold.
            cooldown_econ.add(user.getId()); // Put user on cooldown.

            Thread thread = new Thread(new MessageGiveStatListener.schedule("econ", 300000, user.getId()));
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

            Thread thread = new Thread(new MessageGiveStatListener.schedule("lvl", 60000, user.getId()));
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

        public schedule(String t, int d, long i) {
            type = t;
            delayMillis = d;
            id = i;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(delayMillis);
            } catch(InterruptedException ex) {
                System.out.println("Error occurred while trying to sleep thread in MessageGiveStatListener.schedule. Reason: " + ex.getMessage());
                return;
            }

            if (type == "econ") {
                MessageGiveStatListener.cooldown_econ.remove(id);
            }

            if (type == "lvl") {
                MessageGiveStatListener.cooldown_lvl.remove(id);
            }
        }

    }

}