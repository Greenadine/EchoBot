package me.greenadine.echobot.listeners;

import me.greenadine.echobot.EchoBot;
import me.greenadine.echobot.handlers.Economy;
import me.greenadine.echobot.handlers.Levels;
import me.greenadine.echobot.handlers.WarningHandler;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberBanEvent;
import org.javacord.api.listener.server.member.ServerMemberBanListener;

public class BanListener implements ServerMemberBanListener {

    private Economy econ = EchoBot.econ;
    private Levels lvl = EchoBot.lvl;
    private WarningHandler warnings = EchoBot.warnings;

    @Override
    public void onServerMemberBan(ServerMemberBanEvent e) {
        if (e.getServer().getId() == 595246997762605065L) {
            User user = e.getUser();
            econ.clear(user);
            lvl.clear(user);
            warnings.clear(user);
        }
    }
}
