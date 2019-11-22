package me.greenadine.echobot.objects;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.Muter;

import java.util.TimerTask;

public class MuteDurationTask extends TimerTask {

    private Muter mute = EchoBot.mute;
    @Override
    public void run() {
        if (mute.getMutedUsers().size() == 0) {
            return;
        } else {
            for (Long id : mute.getMutedUsers()) {
                EchoBot.bot.getUserById(id).thenAcceptAsync(user -> {
                    if (mute.getMuteDuration(user) == -1) {
                        return;
                    }

                    int duration = mute.getMuteDuration(user);

                    if (duration >= 1000) {
                        mute.reduceMuteDuration(user, 1000);
                    } else {
                        mute.unmute(user);
                    }
                });
            }

            mute.reload();
        }


    }
}
