package me.greenadine.echobot.objects;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.MuteHandler;

import java.util.TimerTask;

public class MuteDurationTask extends TimerTask {

    private MuteHandler mute = EchoBot.mute;
    @Override
    public void run() {
        if (mute.getMutedUsers().size() == 0) {
            return;
        } else {
            for (Long id : mute.getMutedUsers()) {
                EchoBot.bot.getUserById(id).thenAcceptAsync(user -> {
                    if (mute.getMuteDuration(user) < 1000) {
                        mute.reduceMuteDuration(user, mute.getMuteDuration(user));
                    } else {
                        mute.reduceMuteDuration(user, 1000);
                    }
                });
            }
        }


    }
}
