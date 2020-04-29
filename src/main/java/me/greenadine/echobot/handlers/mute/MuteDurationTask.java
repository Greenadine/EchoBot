package me.greenadine.echobot.handlers.mute;

import me.greenadine.echobot.EchoBot;

import java.util.TimerTask;

public class MuteDurationTask extends TimerTask {

    private MuteHandler mute = EchoBot.mute;
    @Override
    public void run() {
        if (mute.getMutedUsers().size() != 0) {
            for (Long id : mute.getMutedUsers()) {
                EchoBot.bot.getUserById(id).thenAcceptAsync(user -> {
                    if (mute.getMuteDuration(user) == -1) {
                        return;
                    }

                    int duration = mute.getMuteDuration(user);

                    if (duration >= 1000) {
                        boolean success = mute.reduceMuteDuration(user, 1000);

                        if (!success) {
                            System.out.println("Failed to reduce mute duration for user '" + user.getDiscriminatedName() + "' (ID: " + id + ")");
                        }
                    } else {
                        mute.unmute(user);
                    }
                });
            }

            mute.reload();
        }
    }
}
