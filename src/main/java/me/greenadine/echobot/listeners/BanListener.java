package me.greenadine.echobot.listeners;

import me.greenadine.echobot.EchoBot;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberBanEvent;
import org.javacord.api.listener.server.member.ServerMemberBanListener;

public class BanListener implements ServerMemberBanListener {

    @Override
    public void onServerMemberBan(ServerMemberBanEvent e) {
        if (e.getServer().getId() == 595246997762605065L) {
            User user = e.getUser();
            EchoBot.econ.clear(user);
            EchoBot.lvl.clear(user);
            EchoBot.warnings.clear(user);
            EchoBot.notifier.removeFromAll(user);
            EchoBot.valentine.clear(user);
        }
    }
}